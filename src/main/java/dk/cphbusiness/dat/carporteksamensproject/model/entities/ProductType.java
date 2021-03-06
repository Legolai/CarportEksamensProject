package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.util.Objects;

@Entity
@Table("Product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = 1)
    @Column("product_type_ID")
    private int id;

    @Column("product_type_name")
    private String type;

    @Column("product_type_deleted")
    private boolean isDeleted;

    public ProductType(int id, String type, boolean isDeleted) {
        this.id = id;
        this.type = type;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", deleted=" + isDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductType that)) return false;
        return getId() == that.getId() && isDeleted() == that.isDeleted() && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), isDeleted());
    }
}
