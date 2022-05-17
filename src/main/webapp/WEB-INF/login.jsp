<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
            <h1 style="text-align: center">Login</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
            Login
    </jsp:attribute>

    <jsp:body>

        <div class="container justify-content-center" style="width: clamp(200px, 100%, 700px)">
            <form class="row align-items-center" action="${pageContext.request.contextPath}/fc/login-command" method="post">
                <c:if test="${requestScope.error != null || !requestScope.equals('')}">
                    <span>${requestScope.error}</span>
                </c:if>
                <div class="col-12 mb-3">
                    <label class="form-label" for="email">E-mail: </label>
                    <input class="form-control" type="text" id="email" name="email"/>
                </div>
                <div class="col-12 mb-3">
                    <label class="form-label" for="password" >Password: </label>
                    <input class="form-control" type="password" id="password" name="password"/>
                </div>
                <div class="col-12 mb-3">
                    <input class="col-12 btn btn-primary mb-3" type="submit" value="Log in"/>
                </div>
            </form>
            <div class="row">
                <p>Hvis du ikke har en bruger, kan du oprette en <a href="${pageContext.request.contextPath}/fc/register-page">her</a>.</p>
            </div>
        </div>





    </jsp:body>
</t:pagetemplate>