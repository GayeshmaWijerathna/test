package lk.ijse.api.bo.custom;

import lk.ijse.api.bo.SuperBO;
import lk.ijse.api.dao.CrudDAO;
import lk.ijse.api.dto.ItemDTO;
import lk.ijse.api.entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    public boolean saveItem(ItemDTO itmDTO) throws SQLException;
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
    public boolean updateItem(ItemDTO itmDTO) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String code)throws SQLException, ClassNotFoundException;

}
