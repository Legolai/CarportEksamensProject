<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
            Register
    </jsp:attribute>

    <jsp:attribute name="footer">
            Register
    </jsp:attribute>

    <jsp:body>

        <h3>Her kan du oprette en bruger</h3>

        <form action="${pageContext.request.contextPath}/fc/register-command" method="post">
            <label for="firstName">Fornavn: </label>
            <input type="text" id="firstName" name="firstName"/> <br>
            <label for="lastName">Efternavn: </label>
            <input type="text" id="lastName" name="lastName"/> <br>
            <label for="email">Email: </label>
            <input type="text" id="email" name="email"/> <br>
            <label for="password">Kode ord: </label>
            <input type="password" id="password" name="password"/> <br>
            <label for="confirmedPassword">Gentag kode ord: </label>
            <input type="password" id="confirmedPassword" name="confirmedPassword"/> <br>
            <label for="street">Gade: </label>
            <input type="text" id="street" name="street"/>
            <label for="streetNumber">nr: </label>
            <input type="text" id="streetNumber" name="streetNumber"/> <br>
            <label for="zip">Zip kode: </label>
            <input type="text" id="zip" name="zip"/> <br>
            <label for="city">By: </label>
            <input type="text" id="city" name="city"/> <br>

            <input type="submit"  value="Opret bruger"/>
        </form>

    </jsp:body>
</t:pagetemplate>