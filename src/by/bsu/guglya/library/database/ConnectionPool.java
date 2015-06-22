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
    private BlockingQueue<Connection> connections;
    /**
     * This is a quantity of connections in blocking queue
     */
    private int countConnections;

    /**
     * This constructor registers driver and create linked blocking queue for connections
     * @throws SQLException a SQLException
     */
    private ConnectionPool() throws SQLException {
        try {
            //Registers the given driver with the DriverManager. A newly-loaded driver class should call the method
            // registerDriver to make itself known to the DriverManager.
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connections = new LinkedBlockingQueue<Connection>(MAX_POOL_SIZE);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new SQLException("Can't register database driver!");
        }
        /*try {
            String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
            //Attempts to establish a connection to the given database URL. The DriverManager attempts to select
            // an appropriate driver from the set of registered JDBC drivers.
            Connection connection = DriverManager.getConnection(url);
            connections = new LinkedBlockingQueue<Connection>(MAX_POOL_SIZE);
            //Inserts the specified element at the tail of this queue if it is possible to do so immediately without
            // exceeding the queue's capacity, returning true upon success and false if this queue is full.
            connections.offer(connection);
            countConnections++;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new SQLException("Can't connect to database!");
        }*/
    }

    /**
     * This method returns a connection pool instance or creates it if connection pool instance is null
     * @return a connection pool
     * @throws SQLException a SQLException
     */
    public static ConnectionPool getInstance() throws SQLException{
        try {
            lock.lock();
            if (instance == null) {
                instance = new ConnectionPool();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    /**
     * This method returns a connection or create it if capacity allows
     * @return a connection
     * @throws SQLException a exception if connection is null
     */
    public Connection getConnection() throws SQLException{
        Connection connection = null;
        try {
            //Retrieves and removes the head of this queue, or returns null if this queue is empty.
            connection = connections.poll();
            if (connection == null) {
                if (countConnections < MAX_POOL_SIZE) {
                    String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
                    connection = DriverManager.getConnection(url);
                    countConnections++;
                } else {
                    //Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
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
                //Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary
                // for an element to become available.
                connections.poll(WAITING_TIME, TimeUnit.MILLISECONDS).close();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            } catch (InterruptedException ex) {
                logger.error(ex.getMessage());
            }
        }
    }
}
