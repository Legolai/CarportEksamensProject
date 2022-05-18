package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.UnprotectedPageCommand;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.RoofType;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person.AccountMapper;
import dk.cphbusiness.dat.carporteksamensproject.model.services.SVG;
import dk.cphbusiness.dat.carporteksamensproject.model.services.SVGAlgorithm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

public class SVGCommand extends UnprotectedPageCommand {
    public SVGCommand(String pageName) {
        super(pageName);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {

        CarportDTO carportDTO = (CarportDTO) request.getAttribute("carportDTO");
        Carport carport = new Carport(0, 600, 780, 210, RoofType.FLAT, 0, LocalDateTime.now());
        Shack shack = new Shack(0, 540, 210, true, 0);
        carportDTO = new CarportDTO(Optional.of(shack), carport);
//        Carport carport = new Carport(0, 360, 360, 210, RoofType.FLAT, 0, LocalDateTime.now());
//        carportDTO = new CarportDTO(Optional.empty(), carport);

        SVGAlgorithm svgAlgo = new SVGAlgorithm();
        svgAlgo.setStats(carportDTO);
        request.setAttribute("SVG", svgAlgo.calcSVG(carportDTO));

        return new PageDirect(RedirectType.DEFAULT, "svgExperiments");
    }
}
