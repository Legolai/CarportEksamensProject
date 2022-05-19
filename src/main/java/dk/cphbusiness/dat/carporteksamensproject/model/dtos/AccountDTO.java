package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinView;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Account;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;
@JoinedEntity
@JoinView("accountdto")
public record AccountDTO(Account account, PersonDTO personDTO) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) {
        if (entity instanceof PersonDTO foreignKey) {
            account.setPersonId(foreignKey.person().getId());
        }
    }
}
