package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.Inquiry;

public record InquiryDTO(Inquiry inquiry, BillOfMaterialDTO billOfMaterial, CarportDTO carport) {
}
