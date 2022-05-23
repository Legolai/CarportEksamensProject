package dk.cphbusiness.dat.carporteksamensproject.control.commands.pages;

import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.ProductFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlatRoofPage extends UnprotectedPage {
    public FlatRoofPage(String pageName) {
        super(pageName);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        try {
            Optional<List<ProductDTO>> productDTOS = ProductFacade.findAll(Map.of("Product_type_name", "Tagplade"), connectionPool);
            productDTOS.ifPresentOrElse(list -> request.setAttribute("roofs", list), () -> Logger.getLogger("web").log(Level.SEVERE, "Could not find flat roofs"));
        }
        catch (DatabaseException ex) {
            request.setAttribute("errormessage", ex.getMessage());
            return new PageDirect(RedirectType.ERROR, "error");
        }

        return super.execute(request, response, connectionPool);
    }
}
