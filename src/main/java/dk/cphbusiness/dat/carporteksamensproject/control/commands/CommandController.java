package dk.cphbusiness.dat.carporteksamensproject.control.commands;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.*;
import dk.cphbusiness.dat.carporteksamensproject.control.commands.actions.*;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;

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
        commands.put("roofFlat-page", new FlatRoofPageCommand("roofFlat"));
        commands.put("roofSloped-page", new UnprotectedPageCommand("roofSloped"));
        commands.put("my-inquiry-page", new MyInquiryPageCommand("myInquiry", Role.COSTUMER));
        commands.put("inquiry-page", new UnprotectedPageCommand("inquiry"));
        commands.put("account-page", new AccountPageCommand("account", Role.COSTUMER));
        commands.put("inquiriesAll-page", new InquiriesAllPageCommand("inquiriesAll", Role.EMPLOYEE));

        commands.put("login-command", new LoginActionCommand());
        commands.put("logout-command", new LogoutActionCommand());
        commands.put("register-command", new RegisterActionCommand());
        commands.put("inquiry-flatRoof-command", new InquiryFlatRoofActionCommand());

        commands.put("svgExperiments-page", new UnprotectedPageCommand("svgExperiments"));
        commands.put("SVG-command", new SVGCommand(""));
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
