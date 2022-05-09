package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.util.Objects;

@Entity
@Table("Account")
public class Product {
    @Id
    @GeneratedValue(strategy = 1)
    @Column("product_ID")
    private int id;
    private String description;
    private int unitPrice;
    private Unit unit;
    private AmountUnit amountUnit;
    private int productTypeId;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setAmountUnit(AmountUnit amountUnit) {
        this.amountUnit = amountUnit;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getId() == product.getId() && getUnitPrice() == product.getUnitPrice() && getProductTypeId() == product.getProductTypeId() && isDeleted() == product.isDeleted() && Objects.equals(getDescription(), product.getDescription()) && getUnit() == product.getUnit() && getAmountUnit() == product.getAmountUnit();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getUnitPrice(), getUnit(), getAmountUnit(), getProductTypeId(), isDeleted());
    }
}
