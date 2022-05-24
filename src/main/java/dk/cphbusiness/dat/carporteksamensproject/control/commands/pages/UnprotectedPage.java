package dk.cphbusiness.dat.carporteksamensproject.control.commands.pages;

import dk.cphbusiness.dat.carporteksamensproject.control.commands.Command;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnprotectedPage implements Command {
    private final String pageName;

    public UnprotectedPage(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        return new PageDirect(RedirectType.DEFAULT, getPageName());
    }
}
