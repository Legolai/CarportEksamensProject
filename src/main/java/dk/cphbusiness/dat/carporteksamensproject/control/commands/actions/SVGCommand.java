package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.UnprotectedPageCommand;
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

public class SVGCommand extends UnprotectedPageCommand {
    public SVGCommand(String pageName) {
        super(pageName);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {

        CarportDTO carportDTO = (CarportDTO) request.getAttribute("carportDTO");
        Carport carport = new Carport(0, 600, 780, 210, RoofType.FLAT, 0, LocalDateTime.now(), 0);
        Shack shack = new Shack(0, 540, 210, true);
        carportDTO = new CarportDTO(carport,Optional.of(shack));
//        Carport carport = new Carport(0, 360, 360, 210, RoofType.FLAT, 0, LocalDateTime.now());
//        carportDTO = new CarportDTO(Optional.empty(), carport);

        SVGTopView svgAlgo = new SVGTopView(carportDTO);
        SVG topSVG = svgAlgo.calcSVG();

        SVGSideView svgSide = new SVGSideView(carportDTO);
        SVG sideSVG = svgSide.calcSVG();


        request.setAttribute("SVG", topSVG);
        //request.setAttribute("SVG", sideSVG);

        return new PageDirect(RedirectType.DEFAULT, "svgExperiments");
    }
}
