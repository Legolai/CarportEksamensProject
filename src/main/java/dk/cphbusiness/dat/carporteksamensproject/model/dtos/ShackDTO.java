package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;

public record ShackDTO(Shack shack, ProductVariantDTO flooring, ProductVariantDTO facing) {
}
