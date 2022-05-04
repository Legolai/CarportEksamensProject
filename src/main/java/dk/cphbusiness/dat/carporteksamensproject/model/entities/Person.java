package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public class Person {
    private int id;
    private String forename;
    private String surname;
    private String email;
    private String phoneNumber;
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
