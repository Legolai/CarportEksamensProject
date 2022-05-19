<%@tag description="Inquiry page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="links" fragment="true" %>
<%@attribute name="inquiryForm" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="script" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        Fog Quickbyg - Ny Forespørgsel
    </jsp:attribute>

    <jsp:attribute name="header">
        <jsp:invoke fragment="header"/>
    </jsp:attribute>

    <jsp:attribute name="footer">
        <jsp:invoke fragment="footer"/>
    </jsp:attribute>

    <jsp:attribute name="script">
        <jsp:invoke fragment="script"/>
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <div class="row">
                <div class="col-lg-3">
                    <div class="btn-group-vertical w-100" role="group">
                        <jsp:invoke fragment="links"/>
                    </div>
                </div>
                <div class="col-lg-9">

                    <jsp:invoke fragment="inquiryForm"/>
                </div>
            </div>
        </div>
    </jsp:body>


</t:pagetemplate>