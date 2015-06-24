package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO {

    private static final Logger logger = Logger.getLogger(UserDAO.class);
    public static final String GET_USER = "select * from library.user join library.user_type on library.user.user_type = library.user_type.iduser_type where login=? and password=?;";
    public static final String GET_LOGIN = "select * from library.user where login=?;";
    public static final String GET_IDUSER_TYPE = "select iduser_type from library.user_type where type=?";
    public static final String INSERT_READER = "insert into library.user (login, password, user_type) values (?,?,?);";

    public boolean checkUserExist(String login, String password) throws DAOException {
        boolean result = false;
        getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(GET_USER);
            ps.setString(1, login);
            ps.setString(2, password);
            resultSet = ps.executeQuery();
            result = resultSet.first();
            //throw new SQLException();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
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
            closeConnection();
        }
        return result;
    }

    public User returnUser(String login, String password) throws DAOException {
        User user = null;
        getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(GET_USER);
            ps.setString(1, login);
            ps.setString(2, password);
            resultSet = ps.executeQuery();
            if (resultSet.first()) {
                int idUser = resultSet.getInt("user.iduser");
                String type = resultSet.getString("user_type.type");
                user = new User(idUser, login, password, type.toUpperCase());
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
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
            closeConnection();
        }
        return user;
    }

    public boolean checkLoginExist(String login) throws DAOException {
        boolean result = false;
        getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(GET_LOGIN);
            ps.setString(1, login);
            resultSet = ps.executeQuery();
            result = resultSet.first();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
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
            closeConnection();
        }
        return result;
    }

    public boolean registrateClient(String login, String password) throws DAOException {
        boolean result = false;
        getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(GET_IDUSER_TYPE);
            ps.setString(1, User.TypeOfUser.READER.toString().toLowerCase());
            resultSet = ps.executeQuery();
            resultSet.next();
            int idUserTypeReader = resultSet.getInt("iduser_type");
            ps = conn.prepareStatement(INSERT_READER);
            ps.setString(1, login);
            ps.setString(2, password);
            ps.setInt(3, idUserTypeReader);
            throw new SQLException();
            //ps.executeUpdate();
            //result = true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
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
            closeConnection();
        }
        //return result;
    }

}
