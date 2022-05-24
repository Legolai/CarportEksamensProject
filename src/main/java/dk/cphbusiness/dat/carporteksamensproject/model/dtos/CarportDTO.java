package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinView;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

import java.util.Optional;


@JoinedEntity
@Join(main = Carport.class, join = {Shack.class})
@JoinView("carportdto")
public record CarportDTO(Carport carport, Optional<Shack> shack) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) {
        if (entity instanceof Optional<?> foreignKey) {
            foreignKey.ifPresent(key -> carport.setShackId(((Shack) key).getId()));
        }
    }
}
