package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Carport;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CarportMapper implements DataMapper<CarportDTO> {

    private final EntityManager entityManager;

    public CarportMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<CarportDTO> find(Map<String, Object> properties) throws DatabaseException {
        return Optional.empty();
    }

    @Override
    public List<CarportDTO> getAll() throws DatabaseException {
        return null;
    }

    @Override
    public Optional<List<CarportDTO>> findAll(Map<String, Object> properties) throws DatabaseException {
        return Optional.empty();
    }

    @Override
    public CarportDTO insert(CarportDTO carportDTO) throws DatabaseException {
        return null;
    }

    @Override
    public boolean delete(CarportDTO carportDTO) throws DatabaseException {
        return false;
    }

    @Override
    public boolean update(CarportDTO carportDTO) throws DatabaseException {
        carportDTO.carport().setUpdated(LocalDateTime.now().withNano(0));
        boolean carportUpdated = entityManager.updateEntity(Carport.class, carportDTO.carport());
        if (carportDTO.shack().isEmpty()){
            return carportUpdated;
        }
        Shack shack = carportDTO.shack().get();
        return carportUpdated && entityManager.updateEntity(Shack.class, shack);
    }

    public Shack insertShack(Shack shack) throws DatabaseException {
        return entityManager.insert(Shack.class, shack);
    }
}
