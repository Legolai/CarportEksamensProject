package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.time.LocalDateTime;


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
}
