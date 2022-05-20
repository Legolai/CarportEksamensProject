<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        Fog Quickbyg - Ændringer for forespørgsel - nr. ${requestScope.inquiry.inquiry().getId()}
    </jsp:attribute>

    <jsp:attribute name="header">
            <h1> Ændringer for forespørgsel nr. ${requestScope.inquiry.inquiry().getId()}</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
            Ændringer for forespørgsel
    </jsp:attribute>

    <jsp:attribute name="script">
        <script>
            function wishShack(ckType) {
                const shackWidthElem = document.getElementById("shack-width");
                const shackLengthElem = document.getElementById("shack-length");
                const checked = document.getElementById(ckType.id);

                if (checked.checked) {
                    shackWidthElem.disabled = false
                    shackLengthElem.disabled = false
                } else {
                    shackWidthElem.disabled = true
                    shackLengthElem.disabled = true
                }
            }
        </script>
    </jsp:attribute>

    <jsp:body>
        <fmt:setLocale value = "da_DK"/>
        <div class="row">
            <div class="col-lg-3 col-md-6">
                <h4>Leveringsoplysninger</h4>
                <p>
                        ${requestScope.inquiry.person().address().getNumber()} ${requestScope.inquiry.person().address().getFloor()} ${requestScope.inquiry.person().address().getStreetName()}<br>
                        ${requestScope.inquiry.person().address().getZipcode()} ${requestScope.inquiry.person().address().getCityName()}<br>
                    Danmark
                </p>
            </div>
            <div class="col-lg-3 col-md-6">
                <h4>Forespørgselsoplysninger</h4>
                <strong>${requestScope.inquiry.person().person().getForename()} ${requestScope.inquiry.person().person().getSurname()}</strong><br>
                <strong>Nummer: </strong>${requestScope.inquiry.inquiry().getId()} <br>
                <strong>Status: </strong>${requestScope.inquiry.inquiry().getInquiryStatus().name().toLowerCase()} <br>
                <strong>Oprettede: </strong>${requestScope.inquiry.inquiry().getCreated().toLocalDate()}<br>
                <strong>Sidst opdateret: </strong>${requestScope.inquiry.inquiry().getUpdated().toLocalDate()} <br>
                <strong>Kommentar: </strong> ${requestScope.inquiry.inquiry().getComment()}<br>
            </div>
            <div class="col-lg-3 col-md-6">
                <h4>Carport</h4>
                    <%-- todo: show roof --%>
                <strong>Bredde: </strong>${requestScope.inquiry.carport().carport().getWidth()}<br>
                <strong>Længde: </strong>${requestScope.inquiry.carport().carport().getLength()}<br>
                <strong>Højde: </strong>${requestScope.inquiry.carport().carport().getHeight()}<br>
                <strong>Tag type: </strong> ${requestScope.inquiry.carport().carport().getRoofType().getValue()}<br>
                <strong>Sidst opdateret: </strong>${requestScope.inquiry.carport().carport().getUpdated().toLocalDate()}
            </div>
            <div class="col-lg-3 col-md-6">
                <c:if test="${requestScope.inquiry.carport().shack().isPresent()}">
                    <h4>Shack</h4>
                    <strong>Bredde: </strong>${requestScope.inquiry.carport().shack().get().getWidth()}<br>
                    <strong>Længde: </strong>${requestScope.inquiry.carport().shack().get().getLength()}<br>
                </c:if>
            </div>
        </div>


        <div class="row">
            <div class="col-lg-6">
                <h2>Carport ændringer</h2>
                <form class="row g-3 mb-3" action="${pageContext.request.contextPath}/fc/update-carport-command"
                      method="post">
                    <input hidden name="inquiry-ID" value="${requestScope.inquiry.inquiry().getId()}">
                    <input hidden name="carport-ID" value="${requestScope.inquiry.inquiry().getCarportId()}">
                    <div class="col-md-12">
                        <div class="row">
                            <div class="col">
                                <label for="carport-width" class="form-label">Carport bredde</label>
                                <select id="carport-width" name="carport-width" class="form-select form-select-md mb3"
                                        required>
                                    <c:forEach var="i" begin="240" end="600" step="30">
                                        <option <c:out
                                                value="${requestScope.inquiry.carport().carport().getWidth() eq i ? 'selected' : ''}"/>
                                                value="${i}">${i} cm
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col">
                                <label for="carport-length" class="form-label">Carport længde</label>
                                <select id="carport-length" name="carport-length" class="form-select form-select-md mb3"
                                        required>
                                    <option disabled hidden selected>Vælg længde</option>
                                    <c:forEach var="i" begin="240" end="780" step="30">
                                        <option <c:out
                                                value="${requestScope.inquiry.carport().carport().getLength() eq i ? 'selected' : ''}"/>
                                                value="${i}">${i} cm
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                    </div>

                    <div class="col-md-12">
                        <label for="roof-material" class="form-label">Tag</label>
                        <select id="roof-material" name="roof-material" class="form-select form-select-md mb3" required>
                            <option disabled hidden selected>Vælg tag</option>
                            <c:forEach var="roof" items="${requestScope.roofs}">
                                <option <c:out
                                        value="${requestScope.inquiry.carport().carport().getRoofMaterialId() eq roof.product().getId() ? 'selected' : ''}"/>
                                        value="${roof.product().getId()}">${roof.product().getDescription()}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-12">
                        <h3>Redskabsrum:</h3>
                    </div>


                    <c:set var="shack" value="${requestScope.inquiry.carport().shack()}"/>
                    <input hidden name="shack-ID" value="${shack.isPresent() ? shack.get().getId() : ""}">
                    <div class="col-md-12">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="has-shack" name="has-shack"
                                   onchange="wishShack(this)"
                                <c:out
                                        value="${shack.isPresent() ? 'checked' : ''}"/> value="true">
                            <label for="has-shack" class="form-label">Ønsker Redskabsrum</label>
                        </div>
                    </div>

                    <div class="col-md-12">
                        <label for="shack-width" class="form-label">Redskabsrum bredde</label>
                        <select <c:out value="${shack.isEmpty() ? 'disabled' : ''}"/> id="shack-width"
                                                                                      name="shack-width"
                                                                                      class="form-select form-select-md mb3"
                                                                                      required>
                            <c:if test="${shack.isEmpty()}">
                                <option disabled hidden selected>Ønsker ikke redskabsrum</option>
                            </c:if>
                            <c:forEach var="i" begin="210" end="720" step="30">
                                <option <c:out
                                        value="${shack.isPresent() && shack.get().getWidth() eq i ? 'selected' : ''}"/>
                                        value="${i}">${i} cm
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-12 mb-20">
                        <label for="shack-length" class="form-label">Redskabsrum længde</label>
                        <select <c:out value="${requestScope.inquiry.carport().shack().isEmpty() ? 'disabled' : ''}"/>
                                id="shack-length" name="shack-length" class="form-select form-select-md mb3"
                                required>
                            <c:if test="${requestScope.inquiry.carport().shack().isEmpty()}">
                                <option disabled hidden selected>Ønsker ikke redskabsrum</option>
                            </c:if>
                            <c:forEach var="i" begin="150" end="690" step="30">
                                <option <c:out
                                        value="${shack.isPresent() && shack.get().getLength() eq i ? 'selected' : ''}"/>
                                        value="${i}">${i} cm
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col">
                        <div class="row">
                            <div class="col">
                                <button type="submit" class="btn btn-outline-primary w-100">Preview</button>
                            </div>
                            <div class="col">
                                <button type="submit" class="btn btn-primary w-100">Opdater</button>
                            </div>
                        </div>


                    </div>

                </form>
            </div>
            <div class="col-lg-6">
                <h3>Tegninger</h3>
                <div class="accordion" id="accordionExample">
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="headingOne">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                Nuværende
                            </button>
                        </h2>
                        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne"
                             data-bs-parent="#accordionExample">
                            <div class="accordion-body">
                                <div class="row">
                                    <div class="row">
                                        <h5>Carport set fra siden</h5>
                                    </div>
                                    <div class="row justify-content-center align-items-center h-100">
                                            ${requestScope.svgSide}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="row">
                                        <h5>Carport set oppe fra</h5>
                                    </div>
                                    <div class="row justify-content-center align-items-center h-100">
                                            ${requestScope.svgTop}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="headingTwo">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                Preview
                            </button>
                        </h2>
                        <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo"
                             data-bs-parent="#accordionExample">
                            <div class="accordion-body">
                                <div class="row">
                                    <div class="row">
                                        <h5>Carport set fra siden</h5>
                                    </div>
                                    <div class="row justify-content-center align-items-center h-100">
                                            ${requestScope.previewSvgSide}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="row">
                                        <h5>Carport set oppe fra</h5>
                                    </div>
                                    <div class="row justify-content-center align-items-center h-100">
                                            ${requestScope.previewSvgTop}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <h3>Stykliste</h3>
        <c:if test="${requestScope.inquiry.billOfMaterial().isPresent()}">
            <table class="table">
                <thead>
                <tr>
                    <th>Varebeskrivelse</th>
                    <th>Størrelse</th>
                    <th>Antal</th>
                    <th>Enhed</th>
                    <th>Stk. pris</th>
                    <th>Pris</th>
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
                        <td><fmt:formatNumber value = "${lineItem.product().getPrice()}" type = "currency"/></td>
                        <td><fmt:formatNumber value = "${lineItem.getPrice()}" type = "currency"/></td>
                        <td>${lineItem.lineItem().getComment()}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </c:if>

    </jsp:body>

</t:pagetemplate>