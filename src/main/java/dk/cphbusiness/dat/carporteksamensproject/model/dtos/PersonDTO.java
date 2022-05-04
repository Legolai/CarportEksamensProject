package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.Person;

public record PersonDTO(Person person, AddressDTO address) {
}
