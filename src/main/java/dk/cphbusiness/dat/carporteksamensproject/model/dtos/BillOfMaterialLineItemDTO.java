package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Table;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.BillOfMaterialLineItem;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

@JoinedEntity
@Table("Bill_of_material_line_item")
@Join(tables = {"Product_variant","Product","Size","Product_type"} , joins = {"product_variant_ID","product_ID","size_ID","product_type_ID"})
public record BillOfMaterialLineItemDTO(BillOfMaterialLineItem lineItem, ProductVariantDTO product) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) throws DatabaseException {
        if (entity instanceof ProductVariantDTO foreignKey) {
            lineItem.setProductId(foreignKey.variant().getId());
        }
    }
}
