package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.BillOfMaterial;

import java.util.List;

public record BillOfMaterialDTO(BillOfMaterial billOfMaterial, List<BillOfMaterialDTO> lineItems) {
}
