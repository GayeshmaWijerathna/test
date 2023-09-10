package lk.ijse.api.bo.custom.impl;

import lk.ijse.api.bo.custom.CustomerBO;
import lk.ijse.api.dao.DAOFactory;
import lk.ijse.api.dao.custom.CustomerDAO;
import lk.ijse.api.dto.CustomerDTO;
import lk.ijse.api.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDAOFactory().getDo(DAOFactory.DoType.Customer);

    @Override
    public boolean saveCustomer(CustomerDTO cusDTO) throws SQLException {
        return customerDAO.save(new Customer(cusDTO.getId(), cusDTO.getName(), cusDTO.getAddress()));

    }

    @Override
    public List<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
      ResultSet rst = customerDAO.getAll();
        List<CustomerDTO> allCustomer = new ArrayList<>();
        while (rst.next()){
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(rst.getString("id"));
            customerDTO.setName(rst.getString("name"));
            customerDTO.setAddress(rst.getString("address"));
            allCustomer.add(customerDTO);
        }

        return allCustomer;
    }

    @Override
    public boolean updateCustomer(CustomerDTO cusDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(cusDTO.getId(), cusDTO.getName(), cusDTO.getAddress()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }
}
