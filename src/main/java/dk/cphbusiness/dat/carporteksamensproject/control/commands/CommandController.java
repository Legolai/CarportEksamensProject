package dk.cphbusiness.dat.carporteksamensproject.control.commands;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.UnprotectedPageCommand;
import dk.cphbusiness.dat.carporteksamensproject.control.commands.actions.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandController {
    private static final CommandController INSTANCE = new CommandController();
    private final Map<String, Command> commands = new HashMap<>();

    private CommandController(){
        commands.put("index", new UnprotectedPageCommand("index"));
        commands.put("login-page", new UnprotectedPageCommand("login"));
        commands.put("register-page", new UnprotectedPageCommand("register"));
        commands.put("roofFlat-page", new UnprotectedPageCommand("roofFlat"));
        commands.put("roofSloped-page", new UnprotectedPageCommand("roofSloped"));
        commands.put("inquiry-page", new UnprotectedPageCommand("inquiry"));
        commands.put("account-page", new UnprotectedPageCommand("account"));
        commands.put("inquiriesAll-page", new UnprotectedPageCommand("inquiriesAll"));

        commands.put("login-command", new LoginCommand(""));
        commands.put("logout-command", new LogoutCommand(""));
        commands.put("register-command", new RegisterCommand(""));

    }

    public static CommandController getInstance(){
        return INSTANCE;
    }


    public Command fromPath(HttpServletRequest request) {
        String route = request.getPathInfo().replaceAll("^/+", "");

        String msg =  "--> " + route;
        String loggerName = "CommandController";
        Logger.getLogger(loggerName).log(Level.SEVERE, msg);

        Command command = commands.getOrDefault(route, new UnknownCommand());   // unknown-command is default
        msg = "Command class" + command.getClass();
        Logger.getLogger(loggerName).log(Level.SEVERE, msg);

        return command;
    }
}
