package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.Order;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

public record OrderDTO(Order order, InquiryDTO inquiry) implements IForeignKey {

    @Override
    public void updateForeignKey(Object entity) {
        if (entity instanceof InquiryDTO foreignKey) {
            order.setInquiryId(foreignKey.inquiry().getId());
        }
    }
}
