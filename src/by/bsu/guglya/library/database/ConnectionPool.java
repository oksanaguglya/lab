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

public class ConnectionPool {

    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
    private static ReentrantLock lock = new ReentrantLock();
    private final int MAX_POOL_SIZE = 10;
    private final int WAITING_TIME = 1000;
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connections;
    private int countConnections;

    private ConnectionPool() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
            Connection connection = DriverManager.getConnection(url);
            connections = new LinkedBlockingQueue<Connection>(MAX_POOL_SIZE);
            connections.offer(connection);
            countConnections++;
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }
    }

    public static ConnectionPool getInstance() {
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

    public Connection getConnection() {
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
            LOG.error(ex.getMessage());
        } catch (InterruptedException ex) {
            LOG.error(ex.getMessage());
        }
        return connection;
    }

    public void returnConnection(Connection connection) {
        connections.offer(connection);
    }

    public void closeConnections() {
        int remainingConnections = countConnections;
        while (remainingConnections > 0) {
            try {
                connections.poll(WAITING_TIME, TimeUnit.MILLISECONDS).close();
            } catch (SQLException ex) {
                LOG.error(ex.getMessage());
            } catch (InterruptedException ex) {
                LOG.error(ex.getMessage());
            }
        }
    }
}
