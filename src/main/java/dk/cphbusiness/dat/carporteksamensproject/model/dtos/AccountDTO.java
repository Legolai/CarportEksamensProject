package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Account;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Person;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

@JoinedEntity
@Join(main = Account.class, join = {Person.class, Address.class})
public record AccountDTO(Account account, PersonDTO personDTO) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) {
        if (entity instanceof PersonDTO foreignKey) {
            account.setPersonId(foreignKey.person().getId());
        }
    }
}
