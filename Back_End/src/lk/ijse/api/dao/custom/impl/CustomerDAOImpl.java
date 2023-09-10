package lk.ijse.api.dao.custom.impl;

import lk.ijse.api.dao.custom.CustomerDAO;
import lk.ijse.api.db.DBConnection;
import lk.ijse.api.entity.Customer;
import lk.ijse.api.util.CrudUtil;

import java.sql.*;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer cus) {
        try( Connection connection = DBConnection.getDbConnection().getConnection()) {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?)");
            pstm.setString(1,cus.getId());
            pstm.setString(2,cus.getName());
            pstm.setString(3,cus.getAddress());

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
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
        return resultSet;
    }

    @Override
    public boolean update(Customer obj) throws SQLException, ClassNotFoundException {
    String sql ="UPDATE customer SET name=?, address=? WHERE id=?";
    return CrudUtil.execute(sql,obj.getName(),obj.getAddress(),obj.getId());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
       String sql = "DELETE FROM customer WHERE id=?";
       return CrudUtil.execute(sql,id);
    }
}
