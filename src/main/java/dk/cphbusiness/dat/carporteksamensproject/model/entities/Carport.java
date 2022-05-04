package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.time.LocalDateTime;

public class Carport {
    private int id;
    private int width;
    private int length;
    private int height;
    private RoofType roofType;
    private int roofMaterialId;
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
