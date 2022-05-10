package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Person;


@JoinedEntity
@Join(main = Person.class, join = {Address.class})
public record PersonDTO(Person person, Address address) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity)  {
        if(entity instanceof Address foreignKey){
            person.setAddressId(foreignKey.getId());
        }
    }
}
