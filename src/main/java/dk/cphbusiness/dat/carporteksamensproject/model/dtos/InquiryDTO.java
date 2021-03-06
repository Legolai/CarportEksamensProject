package dk.cphbusiness.dat.carporteksamensproject.model.dtos;


import dk.cphbusiness.dat.carporteksamensproject.model.entities.Inquiry;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

import java.util.Optional;


public record InquiryDTO(Inquiry inquiry, PersonDTO person, Optional<BillOfMaterialDTO> billOfMaterial,
                         CarportDTO carport) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) {
        if (entity instanceof CarportDTO foreignKey) {
            inquiry.setCarportId(foreignKey.carport().getId());
        } else if (entity instanceof PersonDTO foreignKey) {
            inquiry.setPersonId(foreignKey.person().getId());
        }
    }
}
