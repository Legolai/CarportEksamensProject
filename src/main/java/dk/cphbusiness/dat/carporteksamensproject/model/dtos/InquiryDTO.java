package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Table;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Inquiry;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

import java.util.Optional;

@JoinedEntity
@Table("Inquiry")
@Join(tables = {"Person","Address","Carport","Shack","Bill_of_material","Bill_of_material_line_item","Product_variant","Product","Size","Product_type"},
        joins = {"person_ID","address_ID","carport_ID","carport_ID","bill_of_material_ID","bill_of_material_ID","product_variant_ID","product_ID","size_ID","product_type_ID"})
public record InquiryDTO(Inquiry inquiry, PersonDTO person, Optional<BillOfMaterialDTO> billOfMaterial, CarportDTO carport) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) throws DatabaseException {
        if (entity instanceof BillOfMaterialDTO foreignKey) {
            inquiry.setBillOfMaterialId(foreignKey.billOfMaterial().getId());
        } else if (entity instanceof CarportDTO foreignKey) {
            inquiry.setCarportId(foreignKey.carport().getId());
        } else if (entity instanceof PersonDTO foreignKey) {
            inquiry.setPersonId(foreignKey.person().getId());
        }
    }
}
