package lk.ijse.api.dao.custom.impl;

import lk.ijse.api.dao.custom.ItemDAO;
import lk.ijse.api.db.DBConnection;
import lk.ijse.api.entity.Customer;
import lk.ijse.api.entity.Item;
import lk.ijse.api.util.CrudUtil;

import java.sql.*;

public class ItemDAOImpl implements ItemDAO {

    public boolean save(Item itm) {
        try( Connection connection = DBConnection.getDbConnection().getConnection()) {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES(?,?,?,?)");
            pstm.setString(1,itm.getCode());
            pstm.setString(2,itm.getDescription());
            pstm.setString(3, String.valueOf(itm.getQtyOnHand()));
            pstm.setString(4, String.valueOf(itm.getUnitPrice()));

            int added = pstm.executeUpdate();
            return added > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public ResultSet getAll() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM item");
        return resultSet;
    }

    @Override
    public boolean update(Item itm) throws SQLException, ClassNotFoundException {
        String sql ="UPDATE item SET description=?, qtyOnHand=?, unitPrice=? WHERE code=?";
        return CrudUtil.execute(sql,itm.getDescription(),itm.getQtyOnHand(),itm.getUnitPrice(),itm.getCode());

    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM item WHERE code=?";
        return CrudUtil.execute(sql,code);
    }

}
