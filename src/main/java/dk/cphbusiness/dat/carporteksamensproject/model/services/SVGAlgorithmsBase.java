package dk.cphbusiness.dat.carporteksamensproject.model.services;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;

public class SVGAlgorithmsBase {
    public final int overhang = 30;
    public int stolpeDim = 14;
    public int width;
    public int length;
    public CarportDTO carportDTO;
    public int shackwidth = 0;
    public int shacklength = 0;
    public int spaerAmounts;
    public int lengthBetween;
    public int dottedX = 0;

    public SVGAlgorithmsBase(CarportDTO carportDTO) {
        this.width = carportDTO.carport().getWidth();
        this.length = carportDTO.carport().getLength();
        this.carportDTO = carportDTO;
        if (carportDTO.shack().isPresent()) {
            this.shackwidth = carportDTO.shack().get().getWidth();
            this.shacklength = carportDTO.shack().get().getLength();
        }
        this.spaerAmounts = (int) Math.ceil(length / 56.5 - 0.35) + 1;
        this.lengthBetween = (int) Math.ceil((length - 10) / (spaerAmounts - 1));
    }
}
