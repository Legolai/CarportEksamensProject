package dk.cphbusiness.dat.carporteksamensproject.model.services.facade;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person.AccountMapper;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person.IAccountMapper;

import java.util.Optional;

public class AccountFacade {

    public static Optional<AccountDTO> login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        IAccountMapper accountMapper = new AccountMapper(new EntityManager(connectionPool));
        return accountMapper.login(email, password);
    }

    public static AccountDTO createAccount(AccountDTO accountDTO, ConnectionPool connectionPool) throws DatabaseException {
        IAccountMapper accountMapper = new AccountMapper(new EntityManager(connectionPool));
        return accountMapper.insert(accountDTO);
    }
}
