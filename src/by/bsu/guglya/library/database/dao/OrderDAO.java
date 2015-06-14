package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.Order;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO extends AbstractDAO{
    private static final Logger LOG = Logger.getLogger(OrderDAO.class);
    public static final String GET_IDORDER_TYPE = "select idorder_type from library.order_type where type=?;";
    public static final String INSERT_ORDER = "insert into library.order (user, book, quantity, state) values (?,?,?,?);";

    public boolean addOrder(String idBook, int idUser){
        boolean result = false;
        PreparedStatement idTypePS = null;
        PreparedStatement insertOrderPS = null;
        try{
            conn.setAutoCommit(false);
            idTypePS = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePS.setString(1, Order.TypeOfOrder.NEW.toString());
            insertOrderPS = conn.prepareStatement(INSERT_ORDER);
            try{
                ResultSet resultSet = idTypePS.executeQuery();
                resultSet.next();
                int idOrderTypeProc = resultSet.getInt("idorder_type");
                insertOrderPS.setInt(1, idUser);
                insertOrderPS.setString(2, idBook);
                int qty = 1;
                insertOrderPS.setInt(3, qty);
                insertOrderPS.setInt(4, idOrderTypeProc);
                insertOrderPS.executeUpdate();
                conn.commit();
                result = true;
            } catch (SQLException ex) {
                conn.rollback();
                LOG.error(ex.getMessage());
            } finally {
                if (insertOrderPS != null) {
                    insertOrderPS.close();
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
    }
}
