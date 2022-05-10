package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.*;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

@JoinedEntity
@Join(main = BillOfMaterialLineItem.class, join = {ProductVariant.class, Product.class, Size.class, ProductType.class})
public record BillOfMaterialLineItemDTO(BillOfMaterialLineItem lineItem, ProductVariantDTO product) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) throws DatabaseException {
        if (entity instanceof ProductVariantDTO foreignKey) {
            lineItem.setProductId(foreignKey.variant().getId());
        }
    }
}
