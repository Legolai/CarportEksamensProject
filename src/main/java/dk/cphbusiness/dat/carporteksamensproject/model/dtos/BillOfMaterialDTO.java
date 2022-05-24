package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.Inquiry;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

import java.util.List;
import java.util.Objects;


public record BillOfMaterialDTO(List<BillOfMaterialLineItemDTO> lineItems) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) {
        if (entity instanceof Inquiry foreignKey) {
            lineItems.forEach(lineItem -> lineItem.lineItem().setInquiryId(foreignKey.getId()));
        }
    }

    public double calcTotalPrice() {
        return lineItems.stream().filter(Objects::nonNull).mapToDouble(BillOfMaterialLineItemDTO::getPrice).sum();
    }
}
