<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
            Login
    </jsp:attribute>

    <jsp:attribute name="footer">
            Login
    </jsp:attribute>

    <jsp:body>

        <form action="${pageContext.request.contextPath}/fc/login-command" method="post">
            <c:if test="${requestScope.error != null || !requestScope.equals('')}">
                <span>${requestScope.error}</span>
            </c:if>
            <label for="email">E-mail: </label>
            <input type="text" id="email" name="email"/>
            <label for="password">Password: </label>
            <input type="password" id="password" name="password"/>
            <input type="submit"  value="Log in"/>
        </form>

        <br>
        <p>Hvis du ikke har en bruger, kan du oprette en <a
                href="${pageContext.request.contextPath}/fc/register-page">her</a>.</p>

    </jsp:body>
</t:pagetemplate>