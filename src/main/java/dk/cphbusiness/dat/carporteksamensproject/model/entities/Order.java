package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private LocalDateTime created;
    private LocalDateTime shipped;
    private boolean deleted;
    private int personId;
    private int inquiryId;
    private int addressId;

    public Order(int id, LocalDateTime created, LocalDateTime shipped, boolean deleted, int personId, int inquiryId, int addressId) {
        this.id = id;
        this.created = created;
        this.shipped = shipped;
        this.deleted = deleted;
        this.personId = personId;
        this.inquiryId = inquiryId;
        this.addressId = addressId;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getShipped() {
        return shipped;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public int getPersonId() {
        return personId;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public int getAddressId() {
        return addressId;
    }
}
