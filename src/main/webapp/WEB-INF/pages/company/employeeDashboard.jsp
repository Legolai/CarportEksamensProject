<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page errorPage="../../../error.jsp" isErrorPage="false" %>
<%@page import="dk.cphbusiness.dat.carporteksamensproject.model.entities.InquiryStatus" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        Fog Quickbyg - Dashboard
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
        <h3 id="inquiryTable">Forespørgselsler</h3>
        <div class="table-responsive">
            <table class="table" aria-describedby="inquiryTable">
                <thead>
                <tr>
                    <th scope="col">Forespørgselsnummer</th>
                    <th scope="col">Dato</th>
                    <th scope="col">Beskrivelse</th>
                    <th scope="col">Status</th>
                    <th scope="col">Totale beløb</th>
                    <th scope="col">Materiale pris</th>
                    <th scope="col">Indtjening</th>
                    <th scope="col">Værktøjer</th>
                </tr>
                </thead>
                <tbody class="table-group-divider">
                <c:forEach items="${requestScope.inquiries}" var="inquiry">
                    <tr>
                        <th scope="row">
                                ${inquiry.inquiry().getId()}
                        </th>
                        <td>${inquiry.inquiry().getCreated().toLocalDate()}</td>
                        <td>Carport med ${inquiry.carport().carport().getRoofType().getValue()}
                            (${inquiry.carport().carport().getWidth()} x ${inquiry.carport().carport().getLength()})
                            <c:if test="${inquiry.carport().shack().isPresent()}"> med skur</c:if>
                        </td>
                        <td>
                            <form method="post" id="statusChangeForm${inquiry.inquiry().getId()}"
                                  action="${pageContext.request.contextPath}/fc/update-inquiry-status-command">
                                <input hidden name="inquiry-ID" value="${inquiry.inquiry().getId()}">
                                <select name="inquiry-status" class="form-select"
                                        onchange="formSubmit('statusChangeForm${inquiry.inquiry().getId()}')">
                                    <c:forEach items="${InquiryStatus.values()}" var="status">
                                        <option <c:out
                                                value="${status.equals(inquiry.inquiry().getInquiryStatus()) ? 'selected' : ''}"/>
                                                value="${status}">${status.name().toLowerCase()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </form>
                        </td>
                        <td><fmt:formatNumber value="${inquiry.inquiry().getPrice()}" type="currency"/></td>
                        <td>
                            <c:set var="price" value="-"/>
                            <c:if test="${inquiry.billOfMaterial().isPresent()}">
                                <c:set var="price" value="${inquiry.billOfMaterial().get().calcTotalPrice()}"/>
                            </c:if>
                            <fmt:formatNumber value="${price}" type="currency"/>
                        </td>
                        <td>
                            <c:set var="diffPrice" value="${inquiry.inquiry().getPrice() - price}"/>
                            <div id="diff-price" style="color: ${diffPrice < 0 ? "red" : "black"}">
                                <fmt:formatNumber value="${diffPrice}" type="currency"/>
                            </div>

                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/fc/edit-inquiry-page">
                                <button class="btn btn-outline-primary" type="submit" name="inquiry-ID"
                                        value="${inquiry.inquiry().getId()}">Rediger
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