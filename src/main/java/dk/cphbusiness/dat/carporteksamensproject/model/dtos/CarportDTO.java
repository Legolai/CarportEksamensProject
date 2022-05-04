package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;

import java.util.Optional;

public record CarportDTO(Carport carport, Optional<ShackDTO> shack) {
}
