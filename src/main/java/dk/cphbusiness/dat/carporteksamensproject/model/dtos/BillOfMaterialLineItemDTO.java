package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.BillOfMaterialLineItem;

public record BillOfMaterialLineItemDTO(BillOfMaterialLineItem lineItem, ProductVariantDTO product) {
}
