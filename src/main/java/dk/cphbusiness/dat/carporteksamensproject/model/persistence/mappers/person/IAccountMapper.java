package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.DataMapper;

import java.util.Optional;

public interface IAccountMapper extends DataMapper<AccountDTO> {
    Optional<AccountDTO> login(String email, String password) throws DatabaseException;
}
