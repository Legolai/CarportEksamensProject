package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table("Order")
public class Order {

    @Id
    @GeneratedValue(strategy = 1)
    @Column("order_ID")
    private int id;

    @Column("order_created")
    private LocalDateTime created;

    @Column("order_shipped")
    private LocalDateTime shipped;

    @Column("order_deleted")
    private boolean deleted;

    @Column("person_ID")
    private int personId;

    @Column("inquiry_ID")
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
