package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO {

    private static final Logger logger = Logger.getLogger(UserDAO.class);
    public static final String GET_USER = "select * from library.user join library.user_type on library.user.user_type = library.user_type.iduser_type where login=? and password=?;";
    public static final String INSERT_READER = "insert into library.user (login, password, user_type) values (?,?,?);";
    public static final String GET_IDUSER_TYPE = "select iduser_type from library.user_type where type=?";
    public static final String GET_LOGIN = "select * from library.user where login=?;";

    public boolean checkUserExist(String login, String password) {
        boolean result = false;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(GET_USER);
            ps.setString(1, login);
            ps.setString(2, password);
            resultSet = ps.executeQuery();
            result = resultSet.first();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
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


    public User returnUser(String login, String password) {
        User user = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(GET_USER);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.first()) {
                int idUser = resultSet.getInt("user.iduser");
                String type = resultSet.getString("user_type.type");
                user = new User(idUser, login, password, type.toUpperCase());
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            closeConnection();
        }
        return user;
    }


    public boolean registrateClient(String login, String password) {
        boolean result = false;
        PreparedStatement idTypePS = null;
        PreparedStatement insertClientPS = null;
        PreparedStatement selectClientPS = null;
        try {
            conn.setAutoCommit(false);
            idTypePS = conn.prepareStatement(GET_IDUSER_TYPE);
            idTypePS.setString(1, User.TypeOfUser.READER.toString());
            insertClientPS = conn.prepareStatement(INSERT_READER);
            selectClientPS = conn.prepareStatement(GET_USER);
            try {
                ResultSet resultSet = idTypePS.executeQuery();
                resultSet.next();
                int idUserTypeReader = resultSet.getInt("iduser_type");
                insertClientPS.setString(1, login);
                insertClientPS.setString(2, password);
                insertClientPS.setInt(3, idUserTypeReader);
                insertClientPS.executeUpdate();
                selectClientPS.setString(1, login);
                selectClientPS.setString(2, password);
                resultSet = selectClientPS.executeQuery();
                resultSet.next();
                int idUser = resultSet.getInt("iduser");
                conn.commit();
                result = true;
            } catch (SQLException ex) {
                conn.rollback();
                logger.error(ex.getMessage());
            } finally {
                if (selectClientPS != null) {
                    selectClientPS.close();
                }
                if (insertClientPS != null) {
                    insertClientPS.close();
                }
                if (idTypePS != null) {
                    idTypePS.close();
                }
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
            closeConnection();
        }
        return result;
    }

    public boolean checkLoginExist(String login) {
        boolean result = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(GET_LOGIN);
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            result = resultSet.first();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            closeConnection();
        }
        return result;
    }
}
