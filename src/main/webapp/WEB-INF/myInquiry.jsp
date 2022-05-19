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

        <div class="row">
            <div class="col-md-4">
                <h4>Kundeoplysninger</h4>
                <p>
                    <strong>${sessionScope.account.personDTO().person().getForename()} ${sessionScope.account.personDTO().person().getSurname()}</strong><br>
                    ${sessionScope.account.personDTO().address().getNumber()} ${sessionScope.account.personDTO().address().getFloor()} ${sessionScope.account.personDTO().address().getStreetName()}<br>
                    ${sessionScope.account.personDTO().address().getZipcode()} ${sessionScope.account.personDTO().address().getCityName()}<br>
                </p>

                <p>
                    ${requestScope.inquiry.person().person().getEmail()} <br>
                    mobil: ${requestScope.inquiry.person().person().getPhoneNumber()}
                </p>
            </div>
            <div class="col-md-4">
                <h4>Leveringsoplysninger</h4>
                <p>
                    ${requestScope.inquiry.person().address().getNumber()} ${requestScope.inquiry.person().address().getFloor()} ${requestScope.inquiry.person().address().getStreetName()}<br>
                    ${requestScope.inquiry.person().address().getZipcode()} ${requestScope.inquiry.person().address().getCityName()}<br>
                    Danmark
                </p>

            </div>
            <div class="col-md-4">
                <h4>Forespørgselsoplysninger</h4>
                <strong>${requestScope.inquiry.person().person().getForename()} ${requestScope.inquiry.person().person().getSurname()}</strong><br>
                <strong>Nummer: </strong>${requestScope.inquiry.inquiry().getId()} <br>
                <strong>Status: </strong>${requestScope.inquiry.inquiry().getInquiryStatus().name().toLowerCase()} <br>
                <strong>Oprettede: </strong>${requestScope.inquiry.inquiry().getCreated().toLocalDate()}<br>
                <strong>Sidst opdateret: </strong>${requestScope.inquiry.inquiry().getUpdated().toLocalDate()} <br>
                <strong>Kommentar: </strong> ${requestScope.inquiry.inquiry().getComment()}<br>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-6">
                <h4>Carport</h4>
                    <%-- todo: show roof --%>
                <strong>Bredde: </strong>${requestScope.inquiry.carport().carport().getWidth()}<br>
                <strong>Længde: </strong>${requestScope.inquiry.carport().carport().getLength()}<br>
                <strong>Højde: </strong>${requestScope.inquiry.carport().carport().getHeight()}<br>
                <strong>Tag type: </strong> ${requestScope.inquiry.carport().carport().getRoofType().getValue()}<br>
                <strong>Sidst opdateret: </strong>${requestScope.inquiry.carport().carport().getUpdated().toLocalDate()}
            </div>
            <div class="col-md-6">
                <c:if test="${requestScope.inquiry.carport().shack().isPresent()}">
                    <h4>Shack</h4>
                    <strong>Bredde: </strong>${requestScope.inquiry.carport().shack().get().getWidth()}<br>
                    <strong>Længde: </strong>${requestScope.inquiry.carport().shack().get().getLength()}<br>
                </c:if>
            </div>
        </div>
        <div class="row">
            <h3>Tegninger</h3>
            <div class="col-md-6">
                <div class="row">
                    <h5>Carport set fra siden</h5>
                </div>
                <div class="row justify-content-center align-items-center h-100">
                    ${requestScope.svgSide}
                </div>
            </div>
            <div class="col-md-6">
                <div class="row">
                    <h5>Carport set oppe fra</h5>
                </div>
                <div class="row justify-content-center align-items-center h-100">
                    ${requestScope.svgTop}
                </div>
            </div>
        </div>


        <h3>Stykliste</h3>
        <c:if test="${!requestScope.inquiry.inquiry().getInquiryStatus().equals(InquiryStatus.ORDERED)}">
            Styklisten bliver tilgængelig når ordren er gået igennem.
        </c:if>
        <c:if test="${requestScope.inquiry.inquiry().getInquiryStatus().equals(InquiryStatus.ORDERED)}">
            <c:if test="${requestScope.inquiry.billOfMaterial().isPresent()}">
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
                    <c:forEach items="${requestScope.inquiry.billOfMaterial().get().lineItems()}" var="lineItem">
                        <tr>
                            <td>${lineItem.product().product().product().getDescription()}</td>
                            <td>${lineItem.product().size().getDetail()} ${lineItem.product().size().getType().getValue()}</td>
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