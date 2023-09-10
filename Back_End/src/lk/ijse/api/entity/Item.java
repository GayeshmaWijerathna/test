package lk.ijse.api.entity;

public class Item {
    String code;
    String description;
    String qtyOnHand;
    String unitPrice;

    public Item(String code, String description, String qtyOnHand, String unitPrice) {
        this.code = code;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }

    public Item() {
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getQtyOnHand() {
        return qtyOnHand;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQtyOnHand(String qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}