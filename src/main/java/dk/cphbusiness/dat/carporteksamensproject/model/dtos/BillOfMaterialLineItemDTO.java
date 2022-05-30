package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinView;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.BillOfMaterialLineItem;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

@JoinedEntity
@JoinView("lineitemdto")
public record BillOfMaterialLineItemDTO(BillOfMaterialLineItem lineItem,
                                        ProductVariantDTO product) implements IForeignKey {

    @Override
    public void updateForeignKey(Object entity) {
        if (entity instanceof ProductVariantDTO foreignKey) {
            lineItem.setProductId(foreignKey.variant().getId());
        }
    }

    public double getPrice() {
        return lineItem.getAmount() * product.getPrice();
    }
}
