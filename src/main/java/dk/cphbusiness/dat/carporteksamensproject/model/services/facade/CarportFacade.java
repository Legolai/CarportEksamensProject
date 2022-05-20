package dk.cphbusiness.dat.carporteksamensproject.model.services.facade;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Shack;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.CarportMapper;

public class CarportFacade {

    private CarportFacade(){}
    public static boolean updateCarport(CarportDTO carportDTO, ConnectionPool connectionPool) throws DatabaseException {
        CarportMapper carportMapper = new CarportMapper(new EntityManager(connectionPool));
        return carportMapper.update(carportDTO);
    }

    public static Shack createShack(Shack shack, ConnectionPool connectionPool) throws DatabaseException {
        CarportMapper carportMapper = new CarportMapper(new EntityManager(connectionPool));
        return carportMapper.insertShack(shack);
    }
}
