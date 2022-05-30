<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page errorPage="../../../error.jsp" isErrorPage="false" %>
<%@page import="dk.cphbusiness.dat.carporteksamensproject.model.entities.Unit" %>
<%@page import="dk.cphbusiness.dat.carporteksamensproject.model.entities.SizeType" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        Fog Quickbyg - Ændring for Material - nr. ${requestScope.inquiry.inquiry().getId()}
    </jsp:attribute>

    <jsp:attribute name="header">
        <a class="btn btn-outline-primary"
           href="${pageContext.request.contextPath}/fc/materials-overview-page">Tilbage</a>
        <p class="h1"> Ændringer for Material nr. ${requestScope.material.product().getId()}</p>
    </jsp:attribute>

    <jsp:attribute name="footer">
            Ændringer for Material
    </jsp:attribute>

    <jsp:attribute name="script">

    </jsp:attribute>

    <jsp:body>
        <fmt:setLocale value="da_DK"/>

        <div class="row">
            <div class="col-md-6">
                <form method="post" action="${pageContext.request.contextPath}/fc/update-product-command">
                    <div class="row">
                        <div class="col-12 mb-3">
                            <label class="form-label" for="material-ID">Varenummer</label>
                            <input readonly class="form-control" id="material-ID" name="material-ID"
                                   value="${requestScope.material.product().getId()}">
                        </div>
                        <div class="col-12 mb-3">
                            <label class="form-label" for="material-description">Beskrivelse</label>
                            <input class="form-control" id="material-description" name="material-description"
                                   value="${requestScope.material.product().getDescription()}" required>
                        </div>
                        <div class="col-12 mb-3">
                            <label class="form-label" for="material-type">Type</label>
                            <input readonly class="form-control" id="material-type" name="material-type"
                                   value="${requestScope.material.type().getType()}">
                            <input hidden class="form-control" id="material-type-ID" name="material-type-ID"
                                   value="${requestScope.material.type().getId()}">
                            <input hidden class="form-control" id="material-amount-size" name="material-amount-size"
                                   value="${requestScope.material.product().getAmountUnit()}">
                        </div>
                        <div class="col-12 mb-3">
                            <label class="form-label" for="material-size-unit">Enhed</label>
                            <select id="material-size-unit" name="material-size-unit"
                                    class="form-select form-select-md mb3" required>
                                <c:forEach items="${Unit.values()}" var="type">
                                    <option <c:out
                                            value="${type.equals(requestScope.material.product().getUnit()) ? 'selected' : ''}"/>
                                            value="${type}">${type.getValue()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-12 mb-3">
                            <label class="form-label" for="material-price">Pris
                                (kr./${requestScope.material.product().getUnit().getValue()})</label>
                            <input class="form-control" id="material-price" name="material-price"
                                   value="${requestScope.material.product().getUnitPrice()}" required>
                        </div>
                        <div class="col-12 mb-3">
                            <button type="submit" class="btn btn-primary">Opdater</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-6">
                <h4>Varianter</h4>
                <form method="post" action="${pageContext.request.contextPath}/fc/create-product-variant-command">
                    <div class="row">
                        <div class="col-12 mb-3">
                            <label class="form-label">Nuværende størrelser</label>
                            <div class="d-flex flex-wrap">
                                <c:forEach items="${requestScope.materialVariants}" var="materialvariant">
                                    <div class="border border-primary rounded-pill px-3 m-1 text-primary flex-shrink-1">
                                            ${materialvariant.size().getDetail()} ${materialvariant.size().getType().getValue()}
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-12 mb-3">
                            <input hidden name="material-ID" value="${requestScope.material.product().getId()}">
                            <label class="form-label" for="material-new-size">Ny Størrelse</label>
                            <div class="input-group input-group-sm">
                                <input class="form-control" type="number" id="material-new-size"
                                       name="material-new-size" value="" required>
                                <div class="input-group-text p-0 bg-white">
                                    <select id="size-type" name="size-type"
                                            class="form-select form-select-md mb3 border-0" required>
                                        <option hidden selected disabled value="">Vælg enhed</option>
                                        <c:forEach items="${SizeType.values()}" var="type">
                                            <option value="${type}">${type.getValue()}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                            </div>
                        </div>

                        <div class="col">
                            <button class="btn btn-primary" type="submit">Opret størrelse</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </jsp:body>

</t:pagetemplate>