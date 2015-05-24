package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.database.ConnectionPool;

import java.sql.Connection;

public class AbstractDao {

    protected Connection conn;

    public AbstractDao() {
        conn = ConnectionPool.getInstance().getConnection();
    }

    public void closeConnection()
    {
        ConnectionPool.getInstance().returnConnection(conn);
    }


}
