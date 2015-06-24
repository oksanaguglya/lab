package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.database.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This abstract class holds connection with database
 * Also contains a method of closing the connection
 * @author Oksana Guglya
 */
public abstract class AbstractDAO {

    protected Connection conn;

    protected PreparedStatement ps;

    protected ResultSet resultSet;

    private static final Logger logger = Logger.getLogger(AbstractDAO.class);

    public AbstractDAO(){
    }

    public void getConnection() throws DAOException{
        try{
            conn = ConnectionPool.getInstance().getConnection();
        }catch(SQLException ex){
            throw new DAOException(ex.getMessage());
        }
    }

    public void closeConnection() throws DAOException{
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        try{
            ConnectionPool.getInstance().returnConnection(conn);
        }catch(SQLException ex){
            throw new DAOException(ex.getMessage());
        }
    }

}
