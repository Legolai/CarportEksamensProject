package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public class Address {
    private int id;
    private String number;
    private String StreetName;
    private String floor;
    private String zipcode;

    public Address(int id, String number, String streetName, String floor, String zipcode) {
        this.id = id;
        this.number = number;
        StreetName = streetName;
        this.floor = floor;
        this.zipcode = zipcode;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getStreetName() {
        return StreetName;
    }

    public String getFloor() {
        return floor;
    }

    public String getZipcode() {
        return zipcode;
    }
}
