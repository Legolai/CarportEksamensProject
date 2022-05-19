<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
       <h1>Forespørgsler</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
        Forespørgsler
    </jsp:attribute>

    <jsp:body>

        <table class="table table-striped table-hover" aria-describedby="inquiryTable">
            <thead>
            <tr>
                <th scope="col">Forespørgselsnummer</th>
                <th scope="col">Dato</th>
                <th scope="col">Beskrivelse</th>
                <th scope="col">Status</th>
                <th scope="col">Totale beløb</th>
            </tr>
            </thead>
            <tbody class="table-group-divider">
            <c:forEach items="${requestScope.inquiries}" var="inquiry">
                <tr>
                    <th scope="row">
                        <form action="${pageContext.request.contextPath}/fc/my-inquiry-page">
                            <button type="submit" name="inquiry-ID" value="${inquiry.inquiry().getId()}">${inquiry.inquiry().getId()}</button>
                        </form>
                    </th>
                    <td>${inquiry.inquiry().getCreated().toLocalDate()}</td>
                    <td>Carport med ${inquiry.carport().carport().getRoofType().getValue()}
                        (${inquiry.carport().carport().getWidth()} x ${inquiry.carport().carport().getLength()})
                        <c:if test="${inquiry.carport().shack().isPresent()}"> med skur</c:if>
                    </td>
                    <td>${inquiry.inquiry().getInquiryStatus().name().toLowerCase()}</td>
                    <td>${inquiry.inquiry().getPrice()} kr.</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>


    </jsp:body>

</t:pagetemplate>