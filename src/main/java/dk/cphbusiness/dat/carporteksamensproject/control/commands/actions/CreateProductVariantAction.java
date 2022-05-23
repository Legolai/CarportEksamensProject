package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.ProtectedPage;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductVariant;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Size;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.SizeType;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.ProductVariantFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateProductVariantAction extends ProtectedPage {
    public CreateProductVariantAction(Role role) {
        super("", role);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {

        String productIdString = request.getParameter("material-ID");
        String sizeDetailString = request.getParameter("material-new-size");
        String sizeTypeString = request.getParameter("size-type");

        try {
            int productId = Integer.parseInt(productIdString);
            int sizeDetail = Integer.parseInt(sizeDetailString);
            SizeType sizeType = SizeType.valueOf(sizeTypeString);

            Size size = new Size(0, sizeDetail, sizeType, false);
            ProductVariant productVariant = new ProductVariant(0, productId,0,false);

            ProductVariantFacade.createVariant(productVariant, size, connectionPool);
        } catch (DatabaseException | NumberFormatException ex) {
            Logger.getLogger("web").log(Level.SEVERE, ex.getMessage());
        }
        return new PageDirect(RedirectType.COMMAND, "edit-material-page");
    }
}
