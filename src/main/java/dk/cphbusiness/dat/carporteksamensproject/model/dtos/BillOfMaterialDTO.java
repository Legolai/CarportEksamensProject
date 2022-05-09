package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Table;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.BillOfMaterial;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

import java.util.List;



@JoinedEntity
@Table("Bill_of_material")
@Join(tables = {"Bill_of_material_line_item","Product_variant","Product","Size","Product_type"},
        joins = {"bill_of_material_ID","product_variant_ID","product_ID","size_ID","product_type_ID"})
public record BillOfMaterialDTO(List<BillOfMaterialLineItemDTO> lineItems, BillOfMaterial billOfMaterial) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) throws DatabaseException {
        if (entity instanceof BillOfMaterial foreignKey) {
            lineItems.forEach(lineItem -> lineItem.lineItem().setBillOfMaterialId(foreignKey.getId()));
        }
    }
}
