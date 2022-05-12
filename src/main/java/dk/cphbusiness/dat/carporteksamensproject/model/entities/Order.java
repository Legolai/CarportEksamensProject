package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public Order(int id, LocalDateTime created, LocalDateTime shipped, boolean deleted, int personId, int inquiryId) {
        this.id = id;
        this.created = created;
        this.shipped = shipped;
        this.deleted = deleted;
        this.personId = personId;
        this.inquiryId = inquiryId;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setShipped(LocalDateTime shipped) {
        this.shipped = shipped;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", created=" + created +
                ", shipped=" + shipped +
                ", deleted=" + deleted +
                ", personId=" + personId +
                ", inquiryId=" + inquiryId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getId() == order.getId() && isDeleted() == order.isDeleted() && getPersonId() == order.getPersonId() && getInquiryId() == order.getInquiryId() && Objects.equals(getCreated(), order.getCreated()) && Objects.equals(getShipped(), order.getShipped());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreated(), getShipped(), isDeleted(), getPersonId(), getInquiryId());
    }
}
