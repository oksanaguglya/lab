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
        //сделать конструктор пустым!
        try{
            conn = ConnectionPool.getInstance().getConnection();
        }catch(SQLException ex){
            //throw new DAOException(ex);
        }
    }

    public void getConnection() throws DAOException{
        try{
            conn = ConnectionPool.getInstance().getConnection();
        }catch(SQLException ex){
            throw new DAOException(ex.getMessage());
        }
    }

    public void closeConnection() throws DAOException{
        try{
            ConnectionPool.getInstance().returnConnection(conn);
        }catch(SQLException ex){
            throw new DAOException(ex.getMessage());
        }
    }

}
