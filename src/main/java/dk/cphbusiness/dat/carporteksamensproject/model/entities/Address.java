package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

@Entity
@Table("Address")
public class Address {
    @Id
    @GeneratedValue(strategy = 1)
    @Column("address_ID")
    private int id;
    @Column("address_number")
    private String number;

    @Column("address_street")
    private String streetName;

    @Column("address_floor")
    private String floor;

    @Column("address_zipcode")
    private String zipcode;

    @Column("address_cityName")
    private String cityName;

    public Address(int id, String number, String streetName, String floor, String zipcode, String cityName) {
        this.id = id;
        this.number = number;
        this.streetName = streetName;
        this.floor = floor;
        this.zipcode = zipcode;
        this.cityName = cityName;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getFloor() {
        return floor;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
