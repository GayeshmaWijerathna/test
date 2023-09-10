package lk.ijse.api.bo;

import lk.ijse.api.bo.custom.impl.CustomerBOImpl;
import lk.ijse.api.bo.custom.impl.ItemBOImpl;
import lk.ijse.api.bo.custom.impl.PurchaseOrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    public BOFactory() {
    }

    public static BOFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BoType{
        Item,  Customer, PurchaseOrder
    }
    public SuperBO getBoType(BOFactory.BoType boType ){
        switch (boType){
            case Customer:
                return (SuperBO) new CustomerBOImpl();
            case Item:
                return (SuperBO) new ItemBOImpl();
//            case PurchaseOrder:
//                return (SuperBO) new PurchaseOrderBOImpl();

        }
        return null;
    }
}
