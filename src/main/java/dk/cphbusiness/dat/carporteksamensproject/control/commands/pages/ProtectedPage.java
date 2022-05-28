package dk.cphbusiness.dat.carporteksamensproject.control.commands.pages;

import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.PageDirect;
import dk.cphbusiness.dat.carporteksamensproject.control.webtypes.RedirectType;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Role;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProtectedPage extends UnprotectedPage {
    private final Set<Role> roles;

    public ProtectedPage(String pageName, Role... roles) {
        super(pageName);
        this.roles = new HashSet<>(List.of(roles));
    }

    @Override
    public PageDirect execute(HttpServletRequest request, HttpServletResponse response, ConnectionPool connectionPool) {
        return new PageDirect(RedirectType.DEFAULT, getPageName());
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
