package dk.cphbusiness.dat.carporteksamensproject.control.commands;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.actions.*;
import dk.cphbusiness.dat.carporteksamensproject.control.commands.pages.*;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandController {
    private static final CommandController INSTANCE = new CommandController();
    private final Map<String, Command> commands = new HashMap<>();

    private CommandController() {
        commands.put("index", new UnprotectedPage("pages/general/index"));
        commands.put("login-page", new UnprotectedPage("pages/general/login"));
        commands.put("register-page", new UnprotectedPage("pages/general/register"));
        commands.put("roofFlat-page", new FlatRoofPage("pages/general/roofFlat"));
        commands.put("roofSloped-page", new UnprotectedPage("pages/general/roofSloped"));
        commands.put("my-inquiry-page", new MyInquiryPage("pages/customer/myInquiry", Role.COSTUMER));
        commands.put("inquiry-page", new UnprotectedPage("pages/general/inquiry"));
        commands.put("account-page", new ProtectedPage("pages/general/account", Role.COSTUMER));
        commands.put("my-inquiries-page", new MyInquiriesPage("pages/customer/myInquiries", Role.COSTUMER));
        commands.put("employee-dashboard-page", new EmployeeDashboardPage("pages/company/employeeDashboard", Role.EMPLOYEE));
        commands.put("materials-overview-page", new MaterialsPage("pages/company/materialsOverview", Role.ADMIN));
        commands.put("edit-material-page", new EditMaterialPage("pages/company/editMaterial", Role.ADMIN));
        commands.put("edit-inquiry-page", new EditInquiryPage("pages/company/editInquiry", Role.EMPLOYEE));

        commands.put("login-command", new LoginAction());
        commands.put("logout-command", new LogoutAction());
        commands.put("register-command", new RegisterAction());
        commands.put("inquiry-flatRoof-command", new InquiryFlatRoofAction());

        commands.put("update-inquiry-status-command", new UpdateInquiryStatusAction(Role.EMPLOYEE));
        commands.put("update-inquiry-price-command", new UpdateInquiryPriceAction(Role.EMPLOYEE));
        commands.put("update-carport-command", new UpdateCarportAction(Role.EMPLOYEE));

        commands.put("update-product-command", new UpdateProductAction(Role.ADMIN));
        commands.put("create-product-variant-command", new CreateProductVariantAction(Role.ADMIN));

        commands.put("svgExperiments-page", new UnprotectedPage("pages/general/svgExperiments"));
        commands.put("SVG-command", new SVGCommand(""));
    }

    public static CommandController getInstance() {
        return INSTANCE;
    }


    public Command fromPath(HttpServletRequest request) {
        String route = request.getPathInfo().replaceAll("^/+", "");

        String msg = "--> " + route;
        String loggerName = "CommandController";
        Logger.getLogger(loggerName).log(Level.SEVERE, msg);

        Command command = commands.getOrDefault(route, new UnknownCommand());   // unknown-command is default
        msg = "Command class" + command.getClass();
        Logger.getLogger(loggerName).log(Level.SEVERE, msg);

        return command;
    }
}
