package dk.cphbusiness.dat.carporteksamensproject.model.persistence;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountMapper {
    private final EntityManager entityManager;

    public AccountMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<AccountDTO> login(String email, String password) throws DatabaseException {
        return entityManager.find(AccountDTO.class, Map.of("account_email", email, "account_password", password));
    }

    public List<AccountDTO> getAll() throws DatabaseException {
        return entityManager.getAll(AccountDTO.class);
    }
}
