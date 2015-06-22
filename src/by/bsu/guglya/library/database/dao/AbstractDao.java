package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.database.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This abstract class holds connection with database
 * Also contains a method of closing the connection
 * @author Oksana Guglya
 */
public abstract class AbstractDAO {

    protected Connection conn;

    public AbstractDAO(){
        try{
            conn = ConnectionPool.getInstance().getConnection();
        }catch(SQLException ex){

        }

    }

    public void closeConnection()
    {
        try{
            ConnectionPool.getInstance().returnConnection(conn);
        }catch(SQLException ex){

        }

    }

}
