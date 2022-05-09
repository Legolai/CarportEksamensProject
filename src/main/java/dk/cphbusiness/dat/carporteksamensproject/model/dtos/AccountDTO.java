package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Table;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Account;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Person;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

@JoinedEntity
@Table("Account")
@Join(tables = {"Person","Address"}, joins = {"person_ID","address_ID"})
public record AccountDTO(Account account, PersonDTO personDTO) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) throws DatabaseException {
        if (entity instanceof Person forgeinKey) {
            account.setPersonId(forgeinKey.getId());
        }
    }
}
