package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

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
}
