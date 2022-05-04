package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public class ProductVariant {
    private int id;
    private int productId;
    private int sizeId;
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
}
