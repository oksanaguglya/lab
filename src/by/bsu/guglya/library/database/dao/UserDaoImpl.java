package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends UserDao {

    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);
    public static final String REQUEST_GET_USER = "select * from library.user join library.user_type on library.user.user_type = library.user_type.iduser_type where login=? and password=?;";
    //public static final String INSERT_CLIENT = "insert into user (login,password,user_type) values (?,?,?)";
    //public static final String GET_IDTYPE = "select iduser_type from user_type where type=?";

    @Override
    public boolean checkLogin(String login, String password) {
        boolean result = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(REQUEST_GET_USER);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            result = resultSet.first();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    LOG.error(ex.getMessage());
                }
            }
            closeConnection();
        }
        return result;
    }

    @Override
    public User returnUser(String login, String password) {
        User user = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(REQUEST_GET_USER);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.first()) {
                int idUser = resultSet.getInt("user.user_id");
                String type = resultSet.getString("user_type.type");
                user = new User(idUser, login, password, type.toUpperCase());
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    LOG.error(ex.getMessage());
                }
            }
            closeConnection();
        }
        return user;
    }

/*    @Override
    public boolean registerClient(String login, String password, int account) {
        boolean result = false;
        PreparedStatement idTypePS = null;
        PreparedStatement insertClientPS = null;
        PreparedStatement selectClientPS = null;
        try {
            conn.setAutoCommit(false);
            idTypePS = conn.prepareStatement(GET_IDTYPE);
            idTypePS.setString(1, User.TypeOfUser.READER.toString());
            insertClientPS = conn.prepareStatement(INSERT_CLIENT);
            selectClientPS = conn.prepareStatement(REQUEST_GET_USER);
            try {
                ResultSet resultSet = idTypePS.executeQuery();
                resultSet.next();
                int idTypeClient = resultSet.getInt("user_type_id");
                insertClientPS.setString(1, login);
                insertClientPS.setString(2, password);
                insertClientPS.setInt(3, idTypeClient);
                insertClientPS.executeUpdate();
                selectClientPS.setString(1, login);
                selectClientPS.setString(2, password);
                resultSet = selectClientPS.executeQuery();
                resultSet.next();
                int idUser = resultSet.getInt("user_id");
                conn.commit();
                result = true;
            } catch (SQLException ex) {
                conn.rollback();
                LOG.error(ex.getMessage());
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
            LOG.error(ex.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }*/
}
