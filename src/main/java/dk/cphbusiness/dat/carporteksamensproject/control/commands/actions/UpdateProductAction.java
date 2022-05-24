package dk.cphbusiness.dat.carporteksamensproject.control.commands.actions;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.ProtectedPage;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.*;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.ProductFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateProductAction extends ProtectedPage {

    public UpdateProductAction(Role... roles) {
        super("", roles);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        String productIdString = request.getParameter("material-ID");
        String description = request.getParameter("material-description");
        String productTypeIdString = request.getParameter("material-type-ID");
        String productType = request.getParameter("material-type");
        String sizeUnit = request.getParameter("material-size-unit");
        String amountUnit = request.getParameter("material-amount-size");
        String productPriceString = request.getParameter("material-price");

        try {
            int productId = Integer.parseInt(productIdString);
            int productTypeId = Integer.parseInt(productTypeIdString);
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            Number number = format.parse(productPriceString);
            double unitPrice = number.doubleValue();

            Product product = new Product(
                    productId,
                    description,
                    unitPrice,
                    Unit.valueOf(sizeUnit),
                    AmountUnit.valueOf(amountUnit),
                    productTypeId,
                    false);

            ProductType type = new ProductType(productTypeId, productType, false);
            ProductDTO productDTO = new ProductDTO(product, type);

            boolean hasUpdate = ProductFacade.update(productDTO, connectionPool);
            if (!hasUpdate) {
                request.setAttribute("errormessage", "Failed to update values for product");
                return new PageDirect(RedirectType.ERROR, "error");
            }
        } catch (NumberFormatException | ParseException ex) {
            Logger.getLogger("web").log(Level.SEVERE, ex.getMessage());
        } catch (DatabaseException ex) {
            request.setAttribute("errormessage", ex.getMessage());
            return new PageDirect(RedirectType.ERROR, "error");
        }

        return new PageDirect(RedirectType.COMMAND, "edit-material-page");
    }
}
