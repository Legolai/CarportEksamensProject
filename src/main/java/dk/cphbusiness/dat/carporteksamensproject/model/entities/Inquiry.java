package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.time.LocalDateTime;

public class Inquiry {
    private int id;
    private InquiryStatus inquiryStatus;
    private String comment;
    private int personId;
    private int billOfMaterialId;
    private LocalDateTime created;
    private LocalDateTime updated;

    public Inquiry(int id, InquiryStatus inquiryStatus, String comment, int personId, int billOfMaterialId, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.inquiryStatus = inquiryStatus;
        this.comment = comment;
        this.personId = personId;
        this.billOfMaterialId = billOfMaterialId;
        this.created = created;
        this.updated = updated;
    }

    public int getId() {
        return id;
    }

    public InquiryStatus getInquiryStatus() {
        return inquiryStatus;
    }

    public String getComment() {
        return comment;
    }

    public int getPersonId() {
        return personId;
    }

    public int getBillOfMaterialId() {
        return billOfMaterialId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
}
