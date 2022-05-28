package dk.cphbusiness.dat.carporteksamensproject.control.commands.pages;

import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductVariantDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.ProductFacade;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.ProductVariantFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditMaterialPage extends ProtectedPage {
    public EditMaterialPage(String pageName, Role... roles) {
        super(pageName, roles);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {

        String materialIdString = request.getParameter("material-ID");

        try {
            int materialId = Integer.parseInt(materialIdString);
            Optional<List<ProductVariantDTO>> productVariants = ProductVariantFacade.findAllByProductId(materialId, connectionPool);
            Optional<ProductDTO> productDTO = ProductFacade.findById(materialId, connectionPool);
            productDTO.ifPresent(material -> request.setAttribute("material", material));
            productVariants.ifPresent(list -> request.setAttribute("materialVariants", list));
        } catch (NumberFormatException ex) {
            request.setAttribute("errormessage", "Material code is not available");
            Logger.getLogger("web").log(Level.SEVERE, ex.getMessage());
            return new PageDirect(RedirectType.ERROR, "error");
        } catch (DatabaseException ex) {
            request.setAttribute("errormessage", ex.getMessage());
            Logger.getLogger("web").log(Level.SEVERE, ex.getMessage());
            return new PageDirect(RedirectType.ERROR, "error");
        }

        return super.execute(request, response, connectionPool);
    }
}
