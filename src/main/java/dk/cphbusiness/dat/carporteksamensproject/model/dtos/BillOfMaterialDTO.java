package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.BillOfMaterialLineItem;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Inquiry;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Product;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductType;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductVariant;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Size;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

import java.util.List;


@JoinedEntity
@Join(main = BillOfMaterialLineItem.class, join = {ProductVariant.class, Product.class, Size.class, ProductType.class})
public record BillOfMaterialDTO(List<BillOfMaterialLineItemDTO> lineItems) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) {
        if (entity instanceof Inquiry foreignKey) {
            lineItems.forEach(lineItem -> lineItem.lineItem().setInquiryId(foreignKey.getId()));
        }
    }
}
