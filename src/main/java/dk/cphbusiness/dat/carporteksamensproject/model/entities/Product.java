package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public class Product {
    private int id;
    @Column("product_description")
    private String description;
    @Column("product_unit_price")
    private int unitPrice;

    @Column("product_unit")
    private Unit unit;

    @Column("product_amount_unit")
    private AmountUnit amountUnit;

    @Column("product_type_ID")
    private int productTypeId;

    @Column("product_deleted")
    private boolean deleted;

    public Product(int id, String description, int unitPrice, Unit unit, AmountUnit amountUnit, int productTypeId, boolean deleted) {
        this.id = id;
        this.description = description;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.amountUnit = amountUnit;
        this.productTypeId = productTypeId;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public Unit getUnit() {
        return unit;
    }

    public AmountUnit getAmountUnit() {
        return amountUnit;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
