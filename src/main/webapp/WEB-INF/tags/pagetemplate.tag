<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@tag import="dk.cphbusiness.dat.carporteksamensproject.model.entities.Role" %>

<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="script" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<!DOCTYPE html>
<html lang="da">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/fc/index">
                <img src="${pageContext.request.contextPath}/images/FogLogo.png" width="100px;" class="img-fluid"/>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                    aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <c:if test="${sessionScope.account == null }">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/fc/login-page">Login</a>
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/fc/register-page">Sign
                            up</a>
                    </c:if>
                    <c:if test="${sessionScope.account != null }">
                        <c:if test="${sessionScope.account.account().role.equals(Role.ADMIN)}">
                            <a class="nav-item nav-link"
                               href="${pageContext.request.contextPath}/fc/materials-overview-page">Materialer</a>
                        </c:if>
                        <c:if test="${sessionScope.account.account().role.equals(Role.EMPLOYEE) || sessionScope.account.account().role.equals(Role.ADMIN)}">
                            <a class="nav-item nav-link"
                               href="${pageContext.request.contextPath}/fc/employee-dashboard-page">Forespørgselser</a>
                        </c:if>
                        <c:if test="${sessionScope.account.account().role.equals(Role.COSTUMER)}">
                            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/fc/my-inquiries-page">Mine
                                Forespørgselser</a>
                        </c:if>
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/fc/account-page">Min
                            profil</a>
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/fc/logout-command">Log
                            ud</a>
                    </c:if>
                </div>
            </div>
        </div>
    </nav>
</header>

<div id="body" class="container mt-4" style="min-height: 400px;">
    <jsp:invoke fragment="header"/>
    <jsp:doBody/>
</div>

<!-- Footer -->
<div class="container mt-3">
    <hr/>
    <div class="row mt-4">
        <div class="col">
            Nørgaardsvej 30<br/>
            2800 Lyngby
        </div>
        <div class="col">
            <jsp:invoke fragment="footer"/>
            <br/>
            <p>&copy; 2022 Cphbusiness</p>
        </div>
        <div class="col">
            Datamatikeruddannelsen<br/>
            2. semester forår 2022
        </div>
    </div>

</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
<div>
    <jsp:invoke fragment="script"/>
</div>


</body>
</html>