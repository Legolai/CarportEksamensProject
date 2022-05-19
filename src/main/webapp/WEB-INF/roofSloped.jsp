<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:carportInquriyTemplate>
    <jsp:attribute name="header">
         <h1>Carport med rejsning</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
        Carport med rejsning
    </jsp:attribute>

    <jsp:attribute name="links">
        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/fc/roofFlat-page">Carport med fladt tag</a>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/fc/roofFlat-page">Carport med rejsning</a>
        <a class="btn btn-outline-primary">Standard carporte</a>
    </jsp:attribute>

    <jsp:attribute name="inquiryForm">
        <form class="row g-3 mb-3">
            <div class="col-md-12">
                <label class="form-label">Carport bredde</label>
                <select class="form-select form-select-md mb3">
                    <option selected>Vælg bredde</option>
                </select>
            </div>
            <div class="col-md-12">
                <label class="form-label">Carport længde</label>
                <select class="form-select form-select-md mb3">
                    <option selected>Vælg længde</option>
                </select>
            </div>
            <div class="col-md-12">
                <label class="form-label">Tag</label>
                <select class="form-select form-select-md mb3">
                    <option selected>Vælg tag</option>
                </select>
            </div>
            <div class="col-md-12">
                <label class="form-label">Taghældning</label>
                <select class="form-select form-select-md mb3">
                    <option selected>25 grader</option>
                </select>
            </div>


            <fieldset>
                <legend>Redskabsrum:</legend>
                <div class="col-md-12">
                    <label class="form-label">Redskabsrum bredde</label>
                    <select class="form-select form-select-md mb3">
                        <option selected>Ønsker ikke redskabsrum</option>
                    </select>
                </div>
                <div class="col-md-12">
                    <label class="form-label">Redskabsrum længde</label>
                    <select class="form-select form-select-md mb3">
                        <option selected>Ønsker ikke redskabsrum</option>
                    </select>
                </div>
            </fieldset>

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
            <div class="col-md-5 col-8">
                <label class="form-label" for="street">Gade</label>
                <input class="form-control" type="text" id="street" name="street"/>
            </div>
            <div class="col-md-2 col-4">
                <label class="form-label" for="streetNumber">Nr.</label>
                <input class="form-control" type="text" id="streetNumber" name="streetNumber"/>
            </div>
            <div class="col-md-3 col-8">
                <label class="form-label" for="city">By</label>
                <input class="form-control" type="text" id="city" name="city"/>
            </div>
            <div class="col-md-2 col-4">
                <label class="form-label" for="zip">Zip kode</label>
                <input class="form-control" type="text" id="zip" name="zip"/>
            </div>

            <div class="col">
                <button type="submit" class="btn btn-primary w-100">Send forespørgsel</button>
            </div>

        </form>

    </jsp:attribute>

</t:carportInquriyTemplate>