package dk.cphbusiness.dat.carporteksamensproject.control.commands.pages;

import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.ProductDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.services.SVG;
import dk.cphbusiness.dat.carporteksamensproject.model.services.SVGSideView;
import dk.cphbusiness.dat.carporteksamensproject.model.services.SVGTopView;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.InquiryFacade;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.ProductFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EditInquiryPage extends ProtectedPage{

    public EditInquiryPage(String pageName, Role role) {
        super(pageName, role);
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) throws DatabaseException {
        String inquiryIdString = request.getParameter("inquiry-ID");
        int inquiryId = Integer.parseInt(inquiryIdString);

        Optional<InquiryDTO> optional = InquiryFacade.find(Map.of("inquiry_ID", inquiryId), connectionPool);
        Optional<List<ProductDTO>> optionalFlatRoofs = ProductFacade.findAll(Map.of("product_type_name", "Tagplade"), connectionPool);

        if (optional.isEmpty() || optionalFlatRoofs.isEmpty()) {
            return new PageDirect(RedirectType.ERROR, "");
        }

        InquiryDTO inquiryDTO = optional.get();

        request.setAttribute("roofs", optionalFlatRoofs.get());
        request.setAttribute("inquiry", inquiryDTO);
        SVGSideView sideView = new SVGSideView(inquiryDTO.carport());
        SVGTopView topView = new SVGTopView(inquiryDTO.carport());
        SVG svgTop = topView.calcSVG();
        SVG svgSide = sideView.calcSVG();
        request.setAttribute("svgTop", svgTop.toString());
        request.setAttribute("svgSide", svgSide.toString());
        return super.execute(request, response, connectionPool);
    }
}
