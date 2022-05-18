<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        Fog Quickbyg - Account
    </jsp:attribute>

    <jsp:attribute name="header">
       <h1> Welcome to the account side</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
        Welcome to the account side
    </jsp:attribute>

    <jsp:body>
        <h2>Hello ${sessionScope.account.personDTO().person().getForename()}</h2>

        <p>This is your account side </p>


        <h3>Forespørgselsler</h3>
        <c:forEach items="${requestScope.inquires}" var="inquiry">
            <details>
                <summary>Inquiry ${inquiry.inquiry().getId()}</summary>
            </details>
        </c:forEach>


    </jsp:body>

</t:pagetemplate>