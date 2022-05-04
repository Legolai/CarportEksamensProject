package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public class City {
    private String zipcode;
    private String name;

    public City(String zipcode, String name) {
        this.zipcode = zipcode;
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getName() {
        return name;
    }
}
