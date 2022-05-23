package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.util.Objects;


@Entity
@Table("Product")
public class Product {

    @Id
    @GeneratedValue(strategy = 1)
    @Column("product_ID")
    private int id;
    @Column("product_description")
    private String description;
    @Column("product_unit_price")
    private double unitPrice;

    @Column("product_unit")
    private Unit unit;

    @Column("product_amount_unit")
    private AmountUnit amountUnit;

    @Column("product_type_ID")
    private int productTypeId;

    @Column("product_deleted")
    private boolean isDeleted;

    public Product(int id, String description, double unitPrice, Unit unit, AmountUnit amountUnit, int productTypeId, boolean isDeleted) {
        this.id = id;
        this.description = description;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.amountUnit = amountUnit;
        this.productTypeId = productTypeId;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getUnitPrice() {
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
        return isDeleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnitPrice(double unitPrice) {
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
        this.isDeleted = deleted;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", unit=" + unit +
                ", amountUnit=" + amountUnit +
                ", productTypeId=" + productTypeId +
                ", deleted=" + isDeleted +
                '}';
    }
}
