package dk.cphbusiness.dat.carporteksamensproject.control;

import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand extends Command {

    @Override
    String execute( HttpServletRequest request, HttpServletResponse response ) throws DatabaseException
    {
        String msg = "Unknown command. Contact IT";
        throw new DatabaseException( msg );
    }

}
