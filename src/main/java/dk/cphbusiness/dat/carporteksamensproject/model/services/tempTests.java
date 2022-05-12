package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.RoofType;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;

import java.time.LocalDateTime;
import java.util.Optional;

public class tempTests {

    public static void main(String[] args) {


        CarportAlgorithm algo = new CarportAlgorithm();

        //algo.flatRoofAlgo(600, 600, true, 530, 210);

        FlatRoofAlgorithm algo2 = new FlatRoofAlgorithm();

        Carport carport = new Carport(0, 600, 780, 210, RoofType.FLAT, 0, LocalDateTime.now());
        Shack shack = new Shack(0, 540, 210, true, 0);
        CarportDTO carportDTO = new CarportDTO(Optional.of(shack), carport);
        algo2.calcRoof(carportDTO);

    }

}
