package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Column;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Entity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Id;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Table;

@Entity
@Table("City")
public class City {

    @Id
    @Column("city_zipcode")
    private String zipcode;

    @Column("city_name")
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

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setName(String name) {
        this.name = name;
    }
}
