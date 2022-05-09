package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Table;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Order;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

@JoinedEntity
@Table("Order")
@Join(tables = {"Person","Address","Carport","Shack","Bill_of_material","Bill_of_material_line_item","Product_variant","Product","Size","Product_type"},
        joins = {"person_ID","address_ID","carport_ID","carport_ID","bill_of_material_ID","bill_of_material_ID","product_variant_ID","product_ID","size_ID","product_type_ID"})
public record OrderDTO(Order order, InquiryDTO inquiry, PersonDTO person) implements IForeignKey {

    @Override
    public void updateForeignKey(Object entity) throws DatabaseException {
        if (entity instanceof InquiryDTO foreignKey) {
            order.setInquiryId(foreignKey.inquiry().getId());
        } else if (entity instanceof PersonDTO foreignKey) {
            order.setPersonId(foreignKey.person().getId());
        }
    }
}
