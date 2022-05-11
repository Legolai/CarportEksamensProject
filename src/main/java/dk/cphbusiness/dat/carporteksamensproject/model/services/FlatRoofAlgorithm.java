package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;

import java.util.ArrayList;
import java.util.List;

public class FlatRoofAlgorithm implements ICarportAlgorithm{
    @Override
    public List<BillOfMaterialLineItemDTO> calcCarport(CarportDTO carportDTO) {
        List<BillOfMaterialLineItemDTO> list = new ArrayList<>();
        list.addAll(calcBase(carportDTO));
        list.addAll(calcRoof(carportDTO));
        list.addAll(calcSterns(carportDTO));
        if (carportDTO.shack().isPresent()){
            list.addAll(calcShack(carportDTO));
        }
        list.addAll(calcFittings(carportDTO));
        list.addAll(calcScrews(carportDTO));
        return list;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcRoof(CarportDTO carportDTO) {
        return null;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcBase(CarportDTO carportDTO) {
        return null;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcSterns(CarportDTO carportDTO) {
        return null;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcShack(CarportDTO carportDTO) {
        return null;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcFittings(CarportDTO carportDTO) {
        return null;
    }

    @Override
    public List<BillOfMaterialLineItemDTO> calcScrews(CarportDTO carportDTO) {
        return null;
    }
}
