<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../../../error.jsp" isErrorPage="false" %>

<t:carportInquriyTemplate>
    <jsp:attribute name="header">
         <h1 class="mb-3">Carport med fladt tag</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
        Carport med fladt tag
    </jsp:attribute>

    <jsp:attribute name="links">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/fc/roofFlat-page">Carport med fladt tag</a>
        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/fc/roofSloped-page">Carport med
            rejsning</a>
        <a class="btn btn-outline-primary">Standard carporte</a>
    </jsp:attribute>

    <jsp:attribute name="inquiryForm">
        <form class="row g-3 mb-3" action="${pageContext.request.contextPath}/fc/inquiry-flatRoof-command"
              method="post">
            <div class="col-md-12">
                <label for="carport-width" class="form-label">Carport bredde</label>
                <select id="carport-width" name="carport-width" class="form-select form-select-md mb3" required>
                    <option disabled hidden selected>Vælg bredde</option>
                    <c:forEach var="i" begin="240" end="600" step="30">
                        <option value="${i}">${i} cm</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-12">
                <label for="carport-length" class="form-label">Carport længde</label>
                <select id="carport-length" name="carport-length" class="form-select form-select-md mb3" required>
                    <option disabled hidden selected>Vælg længde</option>
                    <c:forEach var="i" begin="240" end="780" step="30">
                        <option value="${i}">${i} cm</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-12">
                <label for="roof-material" class="form-label">Tag</label>
                <select id="roof-material" name="roof-material" class="form-select form-select-md mb3" required>
                    <option disabled hidden selected>Vælg tag</option>
                    <c:forEach var="roof" items="${requestScope.roofs}">
                        <option value="${roof.product().getId()}">${roof.product().getDescription()}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-12">
                <h3>Redskabsrum:</h3>
                <p>OBS: Redskabsrummet skal være min. 30 cm af mindre end målene på carporten!</p>
            </div>

            <div class="col-md-12">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="has-shack" name="has-shack"
                           onchange="wishShack(this)" value="true">
                    <label for="has-shack" class="form-label">Ønsker Redskabsrum</label>
                </div>
            </div>

            <div class="col-md-12">
                <label for="shack-width" class="form-label">Redskabsrum bredde</label>
                <select disabled id="shack-width" name="shack-width" class="form-select form-select-md mb3" required>
                    <option disabled hidden selected>Ønsker ikke redskabsrum</option>
                    <c:forEach var="i" begin="210" end="720" step="30">
                        <option value="${i}">${i} cm</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-12 mb-20">
                <label for="shack-length" class="form-label">Redskabsrum længde</label>
                <select disabled id="shack-length" name="shack-length" class="form-select form-select-md mb3" required>
                    <option disabled hidden selected>Ønsker ikke redskabsrum</option>
                    <c:forEach var="i" begin="150" end="690" step="30">
                        <option value="${i}">${i} cm</option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-12">
                <hr>
            </div>

            <div class="col-md-6">
                <label for="firstName" class="form-label">Fornavn</label>
                <input type="text" class="form-control" id="firstName" name="firstName" required>
            </div>
            <div class="col-md-6">
                <label for="lastName" class="form-label">Efternavn</label>
                <input type="text" class="form-control" id="lastName" name="lastName" required>
            </div>
            <div class="col-md-12">
                <label class="form-label" for="email">Email</label>
                <input class="form-control" type="email" id="email" name="email" required/>
            </div>
            <div class="col-md-5 col-8">
                <label class="form-label" for="street">Gade</label>
                <input class="form-control" type="text" id="street" name="street" required/>
            </div>
            <div class="col-md-2 col-4">
                <label class="form-label" for="streetNumber">Nr.</label>
                <input class="form-control" type="text" id="streetNumber" name="streetNumber"/>
            </div>
            <div class="col-md-3 col-8">
                <label class="form-label" for="city">By</label>
                <input class="form-control" type="text" id="city" name="city" required/>
            </div>
            <div class="col-md-2 col-4">
                <label class="form-label" for="zip">Zip kode</label>
                <input class="form-control" type="text" id="zip" name="zip" required/>
            </div>


            <div class="col-md-12">
                <label for="comment" class="form-label">Evt. bemærkninger</label>
                <textarea class="form-control" cols="20" id="comment" name="comment" rows="2"></textarea>
            </div>

            <div class="col">
                <button type="submit" class="btn btn-primary w-100">Send forespørgsel</button>
            </div>

        </form>

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

</t:carportInquriyTemplate>