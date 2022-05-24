package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.person;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.AccountDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountMapper implements IAccountMapper {
    private final EntityManager entityManager;

    public AccountMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<AccountDTO> login(String email, String password) throws DatabaseException {
        return entityManager.find(AccountDTO.class, Map.of("person_email", email, "account_password", password));
    }

    @Override
    public Optional<AccountDTO> find(Map<String, Object> properties) throws DatabaseException {
        return entityManager.find(AccountDTO.class, properties);
    }

    public List<AccountDTO> getAll() throws DatabaseException {
        return entityManager.getAll(AccountDTO.class);
    }

    @Override
    public Optional<List<AccountDTO>> findAll(Map<String, Object> properties) throws DatabaseException {
        return entityManager.findAll(AccountDTO.class, properties);
    }

    @Override
    public AccountDTO insert(AccountDTO accountDTO) throws DatabaseException {
        if (find(Map.of("person_email", accountDTO.personDTO().person().getEmail())).isPresent())
            throw new DatabaseException("Email is already in use");
        return entityManager.insert(AccountDTO.class, accountDTO);
    }

    @Override
    public boolean delete(AccountDTO accountDTO) throws DatabaseException {
        return entityManager.delete(AccountDTO.class, Map.of("account_ID", accountDTO.account().getId())) == 1;
    }

    @Override
    public boolean update(AccountDTO accountDTO) throws DatabaseException {
        return false;
    }
}
