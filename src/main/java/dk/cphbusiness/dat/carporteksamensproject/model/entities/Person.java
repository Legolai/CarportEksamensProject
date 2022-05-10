package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.util.Objects;

@Entity
@Table("Person")
public class Person {

    @Id
    @GeneratedValue(strategy = 1)
    @Column("person_id")
    private int id;

    @Column("person_forename")
    private String forename;

    @Column("person_surname")
    private String surname;

    @Column("person_email")
    private String email;

    @Column("person_phone_number")
    private String phoneNumber;

    @Column("address_ID")
    private int addressId;

    public Person(int id, String forename, String surname, String email, String phoneNumber, int addressId) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addressId = addressId;
    }

    public int getId() {
        return id;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return getId() == person.getId() && getAddressId() == person.getAddressId() && Objects.equals(getForename(), person.getForename()) && Objects.equals(getSurname(), person.getSurname()) && Objects.equals(getEmail(), person.getEmail()) && Objects.equals(getPhoneNumber(), person.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getForename(), getSurname(), getEmail(), getPhoneNumber(), getAddressId());
    }
}
