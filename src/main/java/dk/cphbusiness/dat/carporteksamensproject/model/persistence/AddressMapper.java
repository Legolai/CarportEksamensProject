package dk.cphbusiness.dat.carporteksamensproject.model.persistence;


import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AddressMapper {
    private final EntityManager manager;

    public AddressMapper(EntityManager manager) {
        this.manager = manager;
    }

    public List<Address> getAll() throws DatabaseException {
        return manager.getAll(Address.class);
    }

    public Optional<Address> find(Map<String, Object> properties) throws DatabaseException {
        return manager.find(Address.class, properties);
    }
}
