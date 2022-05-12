package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person;


import dk.cphbusiness.dat.carporteksamensproject.model.entities.Address;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.DataMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AddressMapper implements DataMapper<Address> {
    private final EntityManager entityManager;

    public AddressMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Address> getAll() throws DatabaseException {
        return entityManager.getAll(Address.class);
    }

    @Override
    public Optional<List<Address>> findAll(Map<String, Object> properties) throws DatabaseException {
        return entityManager.findAll(Address.class, properties);
    }

    @Override
    public Address insert(Address address) throws DatabaseException {
        return entityManager.insert(Address.class, address);
    }

    @Override
    public boolean delete(Address address) throws DatabaseException {
        return false;
    }

    @Override
    public boolean update(Address address) throws DatabaseException {
        return false;
    }

    public Optional<Address> find(Map<String, Object> properties) throws DatabaseException {
        return entityManager.find(Address.class, properties);
    }
}
