<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        Fog Quickbyg
    </jsp:attribute>

    <jsp:attribute name="header">
        <h1>Fog Quickbyg</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
        Quickbyg
    </jsp:attribute>

    <jsp:body>

        <p>Med vores quickbyg tilbud kan du lynhurtigt få et specialiseret tilbud for din drømme carport.
        <br>Tilbud og skitsetegningen fremsendes til din email hurtigst muligt.</p>

        <br>
        <p>Carport med <a href="${pageContext.request.contextPath}/fc/roofFlat-page">flat tag</a>.</p>
        <p>Carport med <a href="${pageContext.request.contextPath}/fc/roofSloped-page">rejsning</a>.</p>

        <br>
        <p>SVG experiments <a
                href="${pageContext.request.contextPath}/fc/svgExperiments-page">SVGs</a>.</p>



    </jsp:body>

</t:pagetemplate>