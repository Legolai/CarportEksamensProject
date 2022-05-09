package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Carport {
    private int id;
    private int width;
    private int length;
    private int height;
    private RoofType roofType;
    private int roofMaterialId;
    private LocalDateTime updated;

    public Carport(int id, int width, int length, int height, RoofType roofType, int roofMaterialId, LocalDateTime updated) {
        this.id = id;
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.roofMaterialId = roofMaterialId;
        this.updated = updated;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carport carport)) return false;
        return getId() == carport.getId() && getWidth() == carport.getWidth() && getLength() == carport.getLength() && getHeight() == carport.getHeight() && getRoofMaterialId() == carport.getRoofMaterialId() && getRoofType() == carport.getRoofType() && Objects.equals(getUpdated(), carport.getUpdated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWidth(), getLength(), getHeight(), getRoofType(), getRoofMaterialId(), getUpdated());
    }
}
