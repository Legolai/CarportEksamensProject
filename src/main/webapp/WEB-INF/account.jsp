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


        <h3 id="inquiryTable">Forespørgselsler</h3>
        <table class="table table-responsive table-hover" aria-describedby="inquiryTable">
            <thead>
            <tr>
                <th scope="col">Forespørgselsnummer</th>
                <th scope="col">Dato</th>
                <th scope="col">Beskrivelse</th>
                <th scope="col">Status</th>
                <th scope="col">Totale beløb</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody class="table-group-divider align-middle">
            <c:forEach items="${requestScope.inquiries}" var="inquiry">
                <tr>
                    <th scope="row">${inquiry.inquiry().getId()}</th>
                    <td>${inquiry.inquiry().getCreated().toLocalDate()}</td>
                    <td>Carport med ${inquiry.carport().carport().getRoofType().getValue()}
                        (${inquiry.carport().carport().getWidth()} x ${inquiry.carport().carport().getLength()})
                        <c:if test="${inquiry.carport().shack().isPresent()}"> med skur</c:if>
                    </td>
                    <td>${inquiry.inquiry().getInquiryStatus().name().toLowerCase()}</td>
                    <td>${inquiry.inquiry().getPrice()} kr.</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/fc/my-inquiry-page">
                            <button class="btn btn-outline-primary" type="submit" name="inquiry-ID" value="${inquiry.inquiry().getId()}">Se detaljer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>


    </jsp:body>

</t:pagetemplate>