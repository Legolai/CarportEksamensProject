package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.util.Objects;

@Entity
@Table("Product_variant")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = 1)
    @Column("product_variant_ID")
    private int id;

    @Column("product_ID")
    private int productId;

    @Column("size_ID")
    private int sizeId;

    @Column("product_variant_deleted")
    private boolean deleted;

    public ProductVariant(int id, int productId, int sizeId, boolean deleted) {
        this.id = id;
        this.productId = productId;
        this.sizeId = sizeId;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductVariant that)) return false;
        return getId() == that.getId() && getProductId() == that.getProductId() && getSizeId() == that.getSizeId() && isDeleted() == that.isDeleted();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductId(), getSizeId(), isDeleted());
    }
}
