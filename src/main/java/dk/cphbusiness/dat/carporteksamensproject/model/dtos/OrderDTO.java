package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.Order;

public record OrderDTO(Order order, InquiryDTO inquiry, PersonDTO person, AddressDTO address) {

}
