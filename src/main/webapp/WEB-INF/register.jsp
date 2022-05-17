<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
           <h1 style="text-align: center">Register</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
            Register
    </jsp:attribute>

    <jsp:body>

        <div class="container justify-content-center" style="width: clamp(200px, 100%, 700px)">
            <h3 style="text-align: center">Her kan du oprette en bruger</h3>
            <form class="row g-3 mb-3" action="${pageContext.request.contextPath}/fc/register-command" method="post">
                <div class="col-md-6">
                    <label for="firstName" class="form-label">Fornavn</label>
                    <input type="text" class="form-control" id="firstName" name="firstName">
                </div>
                <div class="col-md-6">
                    <label for="lastName" class="form-label">Efternavn</label>
                    <input type="text" class="form-control" id="lastName" name="lastName">
                </div>
                <div class="col-md-12">
                    <label class="form-label" for="email">Email</label>
                    <input class="form-control" type="email" id="email" name="email"/>
                </div>
                <div class="col-12">
                    <label class="form-label" for="password">Kodeord</label>
                    <input class="form-control" type="password" id="password" name="password"/>
                </div>
                <div class="col-12">
                    <label class="form-label" for="confirmedPassword">Gentag kodeord: </label>
                    <input class="form-control" type="password" id="confirmedPassword" name="confirmedPassword"/>
                </div>
                <div class="col-md-5">
                    <label class="form-label" for="street">Gade</label>
                    <input class="form-control" type="text" id="street" name="street"/>
                </div>
                <div class="col-md-2">
                    <label class="form-label" for="streetNumber">Nr.</label>
                    <input class="form-control" type="text" id="streetNumber" name="streetNumber"/>
                </div>
                <div class="col-md-3">
                    <label class="form-label" for="city">By</label>
                    <input class="form-control" type="text" id="city" name="city"/>
                </div>
                <div class="col-md-2">
                    <label class="form-label" for="zip">Zip kode</label>
                    <input class="form-control" type="text" id="zip" name="zip"/>
                </div>
                <div class="col-12">
                    <input class="col-12 btn btn-primary" type="submit"  value="Opret bruger"/>
                </div>
            </form>
            <div class="row mb-3">
                <p>Har du allerede en bruger, så login <a href="${pageContext.request.contextPath}/fc/login-page">her</a>.</p>
            </div>
        </div>
    </jsp:body>
</t:pagetemplate>