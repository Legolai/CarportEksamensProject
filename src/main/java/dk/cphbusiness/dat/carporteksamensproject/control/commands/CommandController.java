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
        commands.put("index", new UnprotectedPage("index"));
        commands.put("login-page", new UnprotectedPage("login"));
        commands.put("register-page", new UnprotectedPage("register"));
        commands.put("roofFlat-page", new FlatRoofPage("roofFlat"));
        commands.put("roofSloped-page", new UnprotectedPage("roofSloped"));
        commands.put("my-inquiry-page", new MyInquiryPage("myInquiry", Role.COSTUMER));
        commands.put("inquiry-page", new UnprotectedPage("inquiry"));
        commands.put("account-page", new AccountPage("account", Role.COSTUMER));
        commands.put("employee-dashboard-page", new EmployeeDashboardPage("employeeDashboard", Role.EMPLOYEE));
        commands.put("inquiriesAll-page", new InquiriesAllPage("inquiriesAll", Role.EMPLOYEE));
        commands.put("edit-inquiry-page", new EditInquiryPage("editInquiry", Role.EMPLOYEE));

        commands.put("login-command", new LoginAction());
        commands.put("logout-command", new LogoutAction());
        commands.put("register-command", new RegisterAction());
        commands.put("inquiry-flatRoof-command", new InquiryFlatRoofAction());
        commands.put("update-inquiry-command", new UpdateInquiryStatusAction(Role.EMPLOYEE));
        commands.put("update-carport-command", new UpdateCarportAction(Role.EMPLOYEE));

        commands.put("svgExperiments-page", new UnprotectedPage("svgExperiments"));
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
