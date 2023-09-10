package lk.ijse.api.bo.custom.impl;

import lk.ijse.api.bo.custom.ItemBO;
import lk.ijse.api.dao.DAOFactory;
import lk.ijse.api.dao.custom.ItemDAO;
import lk.ijse.api.dto.ItemDTO;
import lk.ijse.api.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDo(DAOFactory.DoType.Item);

    @Override
    public boolean saveItem(ItemDTO itmDTO) throws SQLException {
        return itemDAO.save(new Item(itmDTO.getCode(), itmDTO.getDescription(), itmDTO.getQtyOnHand(), itmDTO.getUnitPrice()));
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ResultSet rst = itemDAO.getAll();
        List<ItemDTO> allItems = new ArrayList<>();

        while (rst.next()){
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCode(rst.getString("code"));
            itemDTO.setDescription(rst.getString("description"));
            itemDTO.setQtyOnHand(rst.getString("qtyOnHand"));
            itemDTO.setUnitPrice(rst.getString("unitPrice"));
            allItems.add(itemDTO);
        }
        return allItems;
    }

    @Override
    public boolean updateItem(ItemDTO itmDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(itmDTO.getCode(),itmDTO.getDescription(),itmDTO.getQtyOnHand(),itmDTO.getUnitPrice()));
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }
}
