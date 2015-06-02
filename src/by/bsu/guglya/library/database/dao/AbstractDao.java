package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.database.ConnectionPool;

import java.sql.Connection;

public class AbstractDAO {

    protected Connection conn;

    public AbstractDAO() {
        conn = ConnectionPool.getInstance().getConnection();
    }

    public void closeConnection()
    {
        ConnectionPool.getInstance().returnConnection(conn);
    }


}
