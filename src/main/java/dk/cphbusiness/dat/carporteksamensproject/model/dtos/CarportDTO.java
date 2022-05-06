package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;

import java.util.Optional;

public record CarportDTO(Carport carport, Optional<Shack> shack) {
}
