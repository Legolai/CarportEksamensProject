package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.City;

@JoinedEntities
@Table("Address")
@Join(tables = {"City"}, joins = {"city_zipcode"})
public record AddressDTO(Address address, City city) {}
