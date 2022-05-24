<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../../../error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
       Fog Quickbyg - Forespørgsel
    </jsp:attribute>

    <jsp:attribute name="header">
       <h1>Tak for din henvendelse.</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
        Inquiry
    </jsp:attribute>

    <jsp:body>
        <p>Dit forespørgselsnummer er ${requestScope.inquiry.inquiry().getId()}</p>
        <p>For at se status på din forespørgsel så <a href="${pageContext.request.contextPath}/fc/login-page">login</a>
            eller <a href="${pageContext.request.contextPath}/fc/register-page">opret en bruger</a></p>
    </jsp:body>

</t:pagetemplate>