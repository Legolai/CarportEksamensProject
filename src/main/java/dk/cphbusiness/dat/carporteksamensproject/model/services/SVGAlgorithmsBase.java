package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;

public class SVGAlgorithmsBase {
    public final int overhang = 30;
    public int stolpeDim = 14;

    public int width = 0;
    public int length = 0;
    public CarportDTO carportDTO;
    public int shackwidth = 0;
    public int shacklength = 0;
    public int spaerAmounts = 5;
    public int lengthBetween = 55;
    public int dottedX = 0;

    public SVGAlgorithmsBase(CarportDTO carportDTO) {
        setStats(carportDTO);
    }

    public void setStats(CarportDTO carportDTO) {
        width = carportDTO.carport().getWidth();
        length = carportDTO.carport().getLength();
        this.carportDTO = carportDTO;
        if (carportDTO.shack().isPresent()){
            shackwidth = carportDTO.shack().get().getWidth();
            shacklength = carportDTO.shack().get().getLength();
        }
        spaerAmounts = (int) Math.ceil(length/56.5-0.35) + 1;
        lengthBetween = (int) Math.ceil((length - 10)/(spaerAmounts-1));
    }


}
