package lk.ijse.api.dao;

import lk.ijse.api.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.api.dao.custom.impl.ItemDAOImpl;
import lk.ijse.api.dao.custom.impl.PurchaseOrderDAOImpl;
import lk.ijse.api.entity.Customer;
import org.omg.PortableServer.POA;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public DAOFactory() {

    }

    public static DAOFactory getDAOFactory(){
        if (daoFactory == null){
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DoType {
        Customer,Item,PurchaseOrder
    }
    public SuperDAO getDo(DAOFactory.DoType doType ){
        switch (doType){
            case Customer:
                return (SuperDAO) new CustomerDAOImpl();
            case Item:
                return (SuperDAO) new ItemDAOImpl();
            case PurchaseOrder:
                return (SuperDAO) new PurchaseOrderDAOImpl();

        }
        return null;
    }

}
