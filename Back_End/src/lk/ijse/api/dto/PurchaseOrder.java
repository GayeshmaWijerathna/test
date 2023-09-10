package lk.ijse.api.dto;

import java.math.BigDecimal;

public class PurchaseOrder {
    private String oid;
    private String code;
    private int qty;
    private BigDecimal unitPrice;

    public PurchaseOrder() {
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public PurchaseOrder(String oid, String code, int qty, BigDecimal unitPrice) {
        this.setOid(oid);
        this.setCode(code);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
    }
}
