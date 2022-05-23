<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page errorPage="../../../error.jsp" isErrorPage="false" %>


<t:pagetemplate>

    <jsp:attribute name="title">
        Fog Quickbyg - materials
    </jsp:attribute>

    <jsp:attribute name="header">

  </jsp:attribute>

    <jsp:attribute name="footer">
        Welcome to the employee site
  </jsp:attribute>

    <jsp:attribute name="script">
    <script>
        function formSubmit(formId) {
            const thisForm = document.getElementById(formId);
            if (thisForm) {
                thisForm.submit();
            }
        }
    </script>
  </jsp:attribute>

    <jsp:body>
        <fmt:setLocale value="da_DK"/>
        <h3 id="inquiryTable">Material</h3>
        <div class="table-responsive">
            <table class="table align-middle " aria-describedby="inquiryTable">
                <thead>
                <tr>
                    <th scope="col">Varenummer</th>
                    <th scope="col">Beskrivelse</th>
                    <th scope="col">Type</th>
                    <th scope="col">Enhed</th>
                    <th scope="col">Pris</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody class="table-group-divider">
                <c:forEach items="${requestScope.materials}" var="material">
                    <tr>
                        <th scope="row">${material.product().getId()}</th>
                        <td>${material.product().getDescription()}</td>
                        <td>${material.type().getType()}</td>
                        <td>${material.product().getAmountUnit().getValue()}</td>
                        <td><fmt:formatNumber value="${material.product().getUnitPrice()}"
                                              type="currency"/>/${material.product().getUnit().getValue()}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/fc/edit-material-page">
                                <button class="btn btn-outline-primary" type="submit" name="material-ID"
                                        value="${material.product().getId()}">Rediger
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>

    </jsp:body>

</t:pagetemplate>