package dk.cphbusiness.dat.carporteksamensproject.model.persistence;

import dk.cphbusiness.dat.carporteksamensproject.model.entities.User;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;

public interface IUserMapper
{
    public User login(String email, String kodeord) throws DatabaseException;
    public User createUser(String username, String password, String role) throws DatabaseException;
}
