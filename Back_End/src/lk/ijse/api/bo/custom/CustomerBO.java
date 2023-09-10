package lk.ijse.api.bo.custom;

import lk.ijse.api.bo.SuperBO;
import lk.ijse.api.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public boolean saveCustomer(CustomerDTO cusDTO) throws SQLException;
    public List<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;
    public boolean updateCustomer(CustomerDTO cusDTO) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id)throws SQLException, ClassNotFoundException;
}

