package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;

public class CarportAlgorithmFactory {

    public ICarportAlgorithm createCarportAlgorithm(CarportDTO carportDTO) {
        if (carportDTO == null)
            return null;
        return switch (carportDTO.carport().getRoofType()) {
            case FLAT -> new FlatRoofAlgorithm();
            case SLOPE -> null;
        };
    }
}
