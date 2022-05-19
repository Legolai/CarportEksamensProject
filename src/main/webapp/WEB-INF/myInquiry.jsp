<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="dk.cphbusiness.dat.carporteksamensproject.model.entities.InquiryStatus"%>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        Fog Quickbyg - min forespørgsel - nr. ${requestScope.inquiry.inquiry().getId()}
    </jsp:attribute>

    <jsp:attribute name="header">
            <h1>Forespørgsel nr. ${requestScope.inquiry.inquiry().getId()}</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
            min forespørgsel
    </jsp:attribute>

    <jsp:body>
        <h2>Forespørgsel</h2>
        ${requestScope.inquiry.inquiry().getId()}
        ${requestScope.inquiry.inquiry().getInquiryStatus()}
        ${requestScope.inquiry.inquiry().getComment()}
        ${requestScope.inquiry.inquiry().getCreated()}
        ${requestScope.inquiry.inquiry().getUpdated().toLocalDate()}

        <h4>Person</h4>
        <p>
            ${requestScope.inquiry.person().person().getForename()} ${requestScope.inquiry.person().person().getSurname()}
        </p>
        <p>${requestScope.inquiry.person().person().getEmail()}</p>
        <p>${requestScope.inquiry.person().person().getPhoneNumber()}</p>

        <h4>Address</h4>
        <p>
            ${requestScope.inquiry.person().address().getNumber()}
            ${requestScope.inquiry.person().address().getFloor()}
            ${requestScope.inquiry.person().address().getStreetName()},
            ${requestScope.inquiry.person().address().getZipcode()}
            ${requestScope.inquiry.person().address().getCityName()}
        </p>

        <h2>Carport</h2>
        ${requestScope.inquiry.carport().carport().getWidth()}
        ${requestScope.inquiry.carport().carport().getLength()}
        ${requestScope.inquiry.carport().carport().getHeight()}
        ${requestScope.inquiry.carport().carport().getRoofType()}
        ${requestScope.inquiry.carport().carport().getUpdated()}

        <%-- todo: show roof --%>

        <h2>Shack</h2>
        <c:if test="${requestScope.inquiry.carport().shack().isPresent()}">
            ${requestScope.inquiry.carport().shack().get().getWidth()}
            ${requestScope.inquiry.carport().shack().get().getLength()}
            ${requestScope.inquiry.carport().shack().get().isLeftAligned()}
        </c:if>


        <c:if test="${requestScope.inquiry.inquiry().getInquiryStatus().equals(InquiryStatus.ORDERED)}">
            <c:if test="${requestScope.inquiry.billOfMaterial().isPresen()}">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Varebeskrivelse</th>
                            <th>Størrelse</th>
                            <th>Antal</th>
                            <th>Enhed</th>
                            <th>Beskrivelse</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.inquiry.billOfMaterial().get()}" var="lineItem">
                            <tr>
                                <td>${lineItem.product().product().product().getDescription()}</td>
                                <td>${lineItem.product().size().getDetail()} ${lineItem.product().size().getType}</td>
                                <td>${lineItem.lineItem().getAmount()}</td>
                                <td>${lineItem.product().product().product().getAmountUnit().getValue()}</td>
                                <td>${lineItem.lineItem().getComment()}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>
        </c:if>

    </jsp:body>
</t:pagetemplate>