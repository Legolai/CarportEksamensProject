package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table("Carport")
public class Carport {

    @Id
    @GeneratedValue(strategy = 1)
    @Column("carport_ID")
    private int id;
    @Column("carport_width")
    private int width;
    @Column("carport_length")
    private int length;
    @Column("carport_height")
    private int height;
    @Column("carport_roof_type")
    private RoofType roofType;
    @Column("carport_roof_material")
    private int roofMaterialId;
    @Column("carport_updated")
    private LocalDateTime updated;
    private int inquiryId;

    public Carport(int id, int width, int length, int height, RoofType roofType, int roofMaterialId, LocalDateTime updated, int inquiryId) {
        this.id = id;
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.roofMaterialId = roofMaterialId;
        this.updated = updated;
        this.inquiryId = inquiryId;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public RoofType getRoofType() {
        return roofType;
    }

    public int getRoofMaterialId() {
        return roofMaterialId;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRoofType(RoofType roofType) {
        this.roofType = roofType;
    }

    public void setRoofMaterialId(int roofMaterialId) {
        this.roofMaterialId = roofMaterialId;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carport carport)) return false;
        return getId() == carport.getId() && getWidth() == carport.getWidth() && getLength() == carport.getLength() && getHeight() == carport.getHeight() && getRoofMaterialId() == carport.getRoofMaterialId() && getInquiryId() == carport.getInquiryId() && getRoofType() == carport.getRoofType() && Objects.equals(getUpdated(), carport.getUpdated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWidth(), getLength(), getHeight(), getRoofType(), getRoofMaterialId(), getUpdated(), getInquiryId());
    }
}
