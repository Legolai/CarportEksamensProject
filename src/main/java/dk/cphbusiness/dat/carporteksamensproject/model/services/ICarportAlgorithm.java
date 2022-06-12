package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;

import java.util.List;

public interface ICarportAlgorithm {
    List<BillOfMaterialLineItemDTO> calcCarport(CarportDTO carportDTO);

    List<BillOfMaterialLineItemDTO> getCarportBaseMaterials(CarportDTO carportDTO);

    List<BillOfMaterialLineItemDTO> getRoofMaterials(CarportDTO carportDTO);

    List<BillOfMaterialLineItemDTO> getSterns(CarportDTO carportDTO);

    List<BillOfMaterialLineItemDTO> getShackMaterials(Carport carport, Shack shack);

    List<BillOfMaterialLineItemDTO> getFittingsAndScrews(CarportDTO carportDTO);
}
