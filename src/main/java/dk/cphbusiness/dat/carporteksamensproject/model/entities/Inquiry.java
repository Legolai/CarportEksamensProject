package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Inquiry {
    private int id;
    private InquiryStatus inquiryStatus;
    private String comment;
    private int personId;
    private int carportId;
    private int billOfMaterialId;
    private LocalDateTime created;
    private LocalDateTime updated;

    public Inquiry(int id, InquiryStatus inquiryStatus, String comment, int personId, int carportId, int billOfMaterialId, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.inquiryStatus = inquiryStatus;
        this.comment = comment;
        this.personId = personId;
        this.carportId = carportId;
        this.billOfMaterialId = billOfMaterialId;
        this.created = created;
        this.updated = updated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InquiryStatus getInquiryStatus() {
        return inquiryStatus;
    }

    public void setInquiryStatus(InquiryStatus inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getCarportId() {
        return carportId;
    }

    public void setCarportId(int carportId) {
        this.carportId = carportId;
    }

    public int getBillOfMaterialId() {
        return billOfMaterialId;
    }

    public void setBillOfMaterialId(int billOfMaterialId) {
        this.billOfMaterialId = billOfMaterialId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inquiry inquiry)) return false;
        return getId() == inquiry.getId() && getPersonId() == inquiry.getPersonId() && getCarportId() == inquiry.getCarportId() && getBillOfMaterialId() == inquiry.getBillOfMaterialId() && getInquiryStatus() == inquiry.getInquiryStatus() && Objects.equals(getComment(), inquiry.getComment()) && Objects.equals(getCreated(), inquiry.getCreated()) && Objects.equals(getUpdated(), inquiry.getUpdated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInquiryStatus(), getComment(), getPersonId(), getCarportId(), getBillOfMaterialId(), getCreated(), getUpdated());
    }
}
