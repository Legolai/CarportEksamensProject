<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        SVG experiments
    </jsp:attribute>

    <jsp:attribute name="footer">
        SVG experiments
    </jsp:attribute>

    <jsp:body>
        <p>Svg with java class: <a href="${pageContext.request.contextPath}/fc/SVG-command">opdater</a></p>
        <br>
        ${requestScope.SVG}
        <p>Note: just preliminary experiment</p>



        <br><br>
        <p>Here is some svg practice: </p>
        <p>1. </p>
        <svg width="255" height=210>
            <rect x="0" y="0" height="210" width="255"
                  style="stroke:#000000; fill: #fffe00"/>
            <rect x="0" y="0" height="90" width="90"
                  style="stroke:#000000; fill: #00ffff"/>
            <rect x="120" y="0" height="90" width="135"
                  style="stroke:#000000; fill: #00ffff"/>
            <rect x="0" y="120" height="90" width="90"
                  style="stroke:#000000; fill: #00ffff"/>
            <rect x="120" y="120" height="90" width="135"
                  style="stroke:#000000; fill: #00ffff"/>
        </svg>
        <svg width="200" height=200>
            <rect x="0" y="0" height="200" width="200"
                  style="stroke:#000000; fill: none"/>
            <circle cx="50" cy="50" r="50"
                    style="stroke:#006600; fill:none"/>
            <circle cx="150" cy="50" r="50"
                    style="stroke:#006600; fill:none"/>
            <circle cx="50" cy="150" r="50"
                    style="stroke:#006600; fill:none"/>
            <circle cx="150" cy="150" r="50"
                    style="stroke:#006600; fill:none"/>
        </svg>

        <br>
        <p>2. Simpel carport </p>
        <svg width="500" height=400 viewBox="0 0 780 600">
            <rect x="0" y="0" height="600" width="780"
                  style="stroke:#000000; fill: none"/>
            <rect x="0" y="30" height="10" width="780"
                  style="stroke:#000000; fill: none"/>
            <rect x="0" y="560" height="10" width="780"
                  style="stroke:#000000; fill: none"/>
            <rect x="540" y="30" height="540" width="10"
                  style="stroke:#000000; fill: none"/>
            <rect x="750" y="30" height="540" width="10"
                  style="stroke:#000000; fill: none"/>
            <line x1="55" y1="40" x2="540" y2="560"
                  style="stroke: #000000; fill:none; stroke-width: 4px; stroke-dasharray: 10 5"  />
            <line x1="540" y1="40" x2="55" y2="560"
                  style="stroke: #000000; fill:none; stroke-width: 4px; stroke-dasharray: 10 5"  />


            <rect x="100" y="30" height="11" width="11"
                  style="stroke:#000000; fill: none"/>
            <rect x="100" y="560" height="11" width="11"
                  style="stroke:#000000; fill: none"/>
            <rect x="100" y="30" height="11" width="11"
                  style="stroke:#000000; fill: none"/>
            <rect x="100" y="560" height="11" width="11"
                  style="stroke:#000000; fill: none"/>
            <rect x="410" y="30" height="11" width="11"
                  style="stroke:#000000; fill: none"/>
            <rect x="410" y="560" height="11" width="11"
                  style="stroke:#000000; fill: none"/>
            <rect x="540" y="30" height="11" width="11"
                  style="stroke:#000000; fill: none"/>
            <rect x="540" y="560" height="11" width="11"
                  style="stroke:#000000; fill: none"/>
            <rect x="750" y="30" height="11" width="11"
                  style="stroke:#000000; fill: none"/>
            <rect x="750" y="560" height="11" width="11"
                  style="stroke:#000000; fill: none"/>
            <rect x="539" y="289" height="12" width="12"
                  style="stroke:#000000; fill: none"/>
            <rect x="749" y="289" height="12" width="12"
                  style="stroke:#000000; fill: none"/>

        </svg>


    </jsp:body>

</t:pagetemplate>