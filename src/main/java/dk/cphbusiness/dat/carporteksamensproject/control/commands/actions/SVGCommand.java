package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.UnprotectedPage;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.RoofType;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.SVG;
import dk.cphbusiness.dat.carporteksamensproject.model.services.SVGTopView;
import dk.cphbusiness.dat.carporteksamensproject.model.services.SVGSideView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

public class SVGCommand extends UnprotectedPage {
    public SVGCommand(String pageName) {
        super(pageName);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {

        CarportDTO carportDTO = (CarportDTO) request.getAttribute("carportDTO");
        Carport carport = new Carport(0, 600, 780, 210, RoofType.FLAT, 0, LocalDateTime.now(), 0);
        Shack shack = new Shack(0, 540, 210, true);
//        Carport carport = new Carport(0, 360, 360, 210, RoofType.FLAT, 0, LocalDateTime.now());
//        carportDTO = new CarportDTO(Optional.empty(), carport);

            // copy of the one in stykliste
        Carport carport1 = new Carport(0, 600, 780, 210, RoofType.FLAT, 0, LocalDateTime.now(), 0);
        Shack shack1 = new Shack(0, 540, 210, true);
        carportDTO = new CarportDTO(carport1, Optional.of(shack1));
        SVGTopView svgAlgo = new SVGTopView(carportDTO);
        SVG topSVG = svgAlgo.calcSVG();
        request.setAttribute("SVG1", topSVG);
        SVGSideView svgSide = new SVGSideView(carportDTO);
        SVG sideSVG = svgSide.calcSVG();
        request.setAttribute("SVG1side", sideSVG);

            // another one without shack, 600 wide and 360 deep
        Carport carport2 = new Carport(0, 600, 360, 210, RoofType.FLAT, 0, LocalDateTime.now(), 0);
        carportDTO = new CarportDTO(carport2, Optional.empty());
        svgAlgo = new SVGTopView(carportDTO);
        topSVG = svgAlgo.calcSVG();
        request.setAttribute("SVG2", topSVG);
        svgSide = new SVGSideView(carportDTO);
        sideSVG = svgSide.calcSVG();
        request.setAttribute("SVG2side", sideSVG);

            // another one without shack, 600 wide and 360 deep
        Carport carport3 = new Carport(0, 360, 780, 210, RoofType.FLAT, 0, LocalDateTime.now(), 0);
        carportDTO = new CarportDTO(carport3, Optional.empty());
        svgAlgo = new SVGTopView(carportDTO);
        topSVG = svgAlgo.calcSVG();
        request.setAttribute("SVG3", topSVG);
        svgSide = new SVGSideView(carportDTO);
        sideSVG = svgSide.calcSVG();
        request.setAttribute("SVG3side", sideSVG);

            // another one without shack, 600 wide and 360 deep
        Carport carport4 = new Carport(0, 330, 330, 210, RoofType.FLAT, 0, LocalDateTime.now(), 0);
        carportDTO = new CarportDTO(carport4, Optional.empty());
        svgAlgo = new SVGTopView(carportDTO);
        topSVG = svgAlgo.calcSVG();
        request.setAttribute("SVG4", topSVG);
        svgSide = new SVGSideView(carportDTO);
        sideSVG = svgSide.calcSVG();
        request.setAttribute("SVG4side", sideSVG);



        return new PageDirect(RedirectType.DEFAULT, "svgExperiments");
    }
}
