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

    public ProductType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductType that)) return false;
        return getId() == that.getId() && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType());
    }
}
