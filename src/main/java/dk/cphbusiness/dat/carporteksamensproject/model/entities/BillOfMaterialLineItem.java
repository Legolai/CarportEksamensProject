package dk.cphbusiness.dat.carporteksamensproject.model.entities;

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
}
