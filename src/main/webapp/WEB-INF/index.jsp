<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        Fog Quickbyg
    </jsp:attribute>

    <jsp:attribute name="footer">
        Quickbyg
    </jsp:attribute>

    <jsp:body>

        <p>Med vores quickbyg tilbud kan du lynhurtigt få et specialiseret tilbud for din drømme carport.
        <br>Tilbud og skitsetegningen fremsendes til din email hurtigst muligt.</p>

        <br>
        <p>Carport med <a
                href="${pageContext.request.contextPath}/fc/roofFlat-page">flat tag</a>.</p>
        <p>Carport med <a
                href="${pageContext.request.contextPath}/fc/roofSloped-page">rejsning</a>.</p>



    </jsp:body>

</t:pagetemplate>