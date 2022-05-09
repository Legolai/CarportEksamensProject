package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.util.Objects;

public class BillOfMaterialLineItem {
    private int id;
    private int billOfMaterialId;
    private int amount;
    private String comment;
    private int productId;

    public BillOfMaterialLineItem(int id, int billOfMaterialId, int amount, String comment, int productId) {
        this.id = id;
        this.billOfMaterialId = billOfMaterialId;
        this.amount = amount;
        this.comment = comment;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public int getBillOfMaterialId() {
        return billOfMaterialId;
    }

    public int getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public int getProductId() {
        return productId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBillOfMaterialId(int billOfMaterialId) {
        this.billOfMaterialId = billOfMaterialId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillOfMaterialLineItem that)) return false;
        return getId() == that.getId() && getBillOfMaterialId() == that.getBillOfMaterialId() && getAmount() == that.getAmount() && getProductId() == that.getProductId() && Objects.equals(getComment(), that.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBillOfMaterialId(), getAmount(), getComment(), getProductId());
    }
}
