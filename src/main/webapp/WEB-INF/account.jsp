<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
       <h1> Welcome to the account side</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
        Welcome to the account side
    </jsp:attribute>

    <jsp:body>

        <p>This is your account side </p>


    </jsp:body>

</t:pagetemplate>