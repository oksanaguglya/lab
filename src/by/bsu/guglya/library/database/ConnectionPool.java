package by.bsu.guglya.library.database;

import by.bsu.guglya.library.managers.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
/**
 * This class creates and closes connections with database
 *
 * @author Oksana Guglya
 */
public class ConnectionPool {

    private static final int MAX_POOL_SIZE = 10;
    private static final int WAITING_TIME = 1000;
    /**
     * This is a logger which print some messages to log file
     */
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);
    /**
     * This is a lock
     */
    private static ReentrantLock lock = new ReentrantLock();
    /**
     * This is a connection pool instance
     */
    private static ConnectionPool instance;
    /**
     * This is a blocking queue of connections
     */
    private static BlockingQueue<Connection> connections;
    /**
     * This is a quantity of connections in blocking queue
     */
    private static int countConnections;
    /**
     * This is a constructor
     */
    private ConnectionPool(){
    }
    /**
     * This method returns a connection pool instance or call init method to create it
     * @return a connection pool
     * @throws SQLException a SQLException
     */
    public static ConnectionPool getInstance() throws SQLException {
        try {
            lock.lock();
            if (instance == null) {
                instance = init();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }
    /**
     * This method initialize a connection pool instance
     * @return a connection pool
     * @throws SQLException a SQLException
     */
    private static ConnectionPool init() throws SQLException {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //throw new SQLException();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new SQLException("Can't register database driver!");
        }
        try {
            String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
            Connection connection = DriverManager.getConnection(url);
            connections = new LinkedBlockingQueue<>(MAX_POOL_SIZE);
            connections.offer(connection);
            countConnections++;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new SQLException("Can't connect to database!");
        }
        return new ConnectionPool();
    }
    /**
     * This method returns a connection or create it if capacity allows
     *
     * @return a connection
     * @throws SQLException a exception if connection is null
     */
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = connections.poll();
            if (connection == null) {
                if (countConnections < MAX_POOL_SIZE) {
                    String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
                    connection = DriverManager.getConnection(url);
                    countConnections++;
                } else {
                    connection = connections.take();
                }
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new SQLException("Can't connect to database!");
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
        }
        return connection;
    }
    /**
     * This method returns a connection to connection pool
     */
    public void returnConnection(Connection connection) {
        connections.offer(connection);
    }
    /**
     * This method tries to close remaining connections of connection pool
     */
    public void closeConnections() {
        int remainingConnections = countConnections;
        while (remainingConnections > 0) {
            try {
                connections.poll(WAITING_TIME, TimeUnit.MILLISECONDS).close();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            } catch (InterruptedException ex) {
                logger.error(ex.getMessage());
            }
        }
    }
}
