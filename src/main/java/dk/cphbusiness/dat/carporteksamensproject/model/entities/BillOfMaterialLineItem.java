package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.util.Objects;

@Entity
@Table("Bill_of_material_line_item")
public class BillOfMaterialLineItem {
    @Id
    @GeneratedValue(strategy = 1)
    @Column("bill_of_material_line_item_ID")
    private int id;
    @Column("inquiry_ID")
    private int inquiryId;
    @Column("bill_of_material_line_item_amount")
    private int amount;
    @Column("bill_of_material_line_item_comment")
    private String comment;
    @Column("product_variant_ID")
    private int productId;

    public BillOfMaterialLineItem(int id, int inquiryId, int amount, String comment, int productId) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.amount = amount;
        this.comment = comment;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "BillOfMaterialLineItem{" +
                "id=" + id +
                ", inquiryId=" + inquiryId +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", productId=" + productId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillOfMaterialLineItem that)) return false;
        return getId() == that.getId() && getInquiryId() == that.getInquiryId() && getAmount() == that.getAmount() && getProductId() == that.getProductId() && Objects.equals(getComment(), that.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInquiryId(), getAmount(), getComment(), getProductId());
    }
}
