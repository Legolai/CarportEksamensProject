package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;

import java.util.List;

public interface ICarportAlgorithm {
    List<BillOfMaterialLineItemDTO> calcCarport(CarportDTO carportDTO);

    List<BillOfMaterialLineItemDTO> calcRoof(CarportDTO carportDTO);

    List<BillOfMaterialLineItemDTO> calcBase(CarportDTO carportDTO);

    List<BillOfMaterialLineItemDTO> calcSterns(CarportDTO carportDTO);

    List<BillOfMaterialLineItemDTO> calcShack(CarportDTO carportDTO);

    List<BillOfMaterialLineItemDTO> calcFittingsAndScrews(CarportDTO carportDTO);
}
