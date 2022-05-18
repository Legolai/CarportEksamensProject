package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.RoofType;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class tempTests {

    public static void main(String[] args) {

//        int carportlength = 360;
//        int frontStolpe = (carportlength/5);
//        if (frontStolpe <= 90) {
//            frontStolpe = 60;
//        }
//        System.out.println("Frontstolpe is: "+frontStolpe);

        CarportAlgorithm algo = new CarportAlgorithm();

        //algo.flatRoofAlgo(600, 600, true, 530, 210);

        FlatRoofAlgorithm algo2 = new FlatRoofAlgorithm();

        Carport carport = new Carport(0, 600, 750, 210, RoofType.FLAT, 0, LocalDateTime.now());
        Shack shack = new Shack(0, 540, 210, true, 0);
        CarportDTO carportDTO = new CarportDTO(Optional.of(shack), carport);

        List<BillOfMaterialLineItemDTO> roof =  algo2.calcCarport(carportDTO);

        for (BillOfMaterialLineItemDTO roofDTO : roof) {
            System.out.println("----------");
            System.out.print(roofDTO.product().product().product().getDescription());
            System.out.print(" | længde: "+roofDTO.product().size().getDetail());
            System.out.print(" | mængde: "+roofDTO.lineItem().getAmount());
            System.out.println(" | kommentar: "+roofDTO.lineItem().getComment());
        }


    }

}
