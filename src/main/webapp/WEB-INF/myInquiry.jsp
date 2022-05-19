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
        ${requestScope.inquiry.inquiry().getId()}
        ${requestScope.inquiry.inquiry().getId()}

        <c:if test="${requestScope.inquiry.inquiry().getInquiryStatus().equals(InquiryStatus.ORDERED)}">
            
        </c:if>

    </jsp:body>
</t:pagetemplate>