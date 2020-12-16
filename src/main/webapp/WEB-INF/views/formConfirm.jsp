<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Document</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" integrity="sha384-vp86vTRFVJgpjF9jiIGPEEqYqlDwgyBgEF109VFjmqGmIY/Y4HV4d3Gp2irVfcrp" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
</head>
<script src="<c:url value="resources/js/app.js"/>"></script>



<body>
<header class="header--main-page">
    <i id="myBtn" class="fas fa-arrow-alt-circle-up"></i>
    <nav class="container container--70">
        <sec:authorize access="isAuthenticated()">
            <sec:authorize access="hasRole('ADMIN')">
                <ul class="nav--actions">
                    <li class="logged-user">
                        Witaj ${name}
                        <ul class="dropdown">
                            <li><a href="#">Profil</a></li>
                            <li><a href="#">Moje zbiórki</a></li>
                            <li><sec:authorize access="isAuthenticated()">
                                <a href="<c:url value="/logout" />">Logout</a>
                            </sec:authorize></li>
                        </ul>
                    </li>
                </ul>
            </sec:authorize>

            <sec:authorize access="hasRole('USER')">
                <ul class="nav--actions">
                    <li class="logged-user">
                        Witaj ${name}
                        <ul class="dropdown">
                            <li><a href="#">Profil</a></li>
                            <li><a href="#">Moje zbiórki</a></li>
                            <li><sec:authorize access="isAuthenticated()">
                                <a href="<c:url value="/logout" />">Logout</a>
                            </sec:authorize></li>
                        </ul>
                    </li>
                </ul>
            </sec:authorize>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
        <ul class="nav--actions">
            <li><a href="/login" class="btn btn--small btn--without-border">Zaloguj</a></li>
            <li><a href="/register" class="btn btn--small btn--highlighted">Załóż konto</a></li>
        </ul>
        </sec:authorize>
    </nav>
    <div class="slogan container container--90">
        <h2>
            Dziękujemy za przesłanie formularza<br> Na maila prześlemy wszelkie
            informacje o odbiorze.
        </h2>
        <a href="/" class="btn btn--large">Wróć na stronę główną</a>
    </div>

</header>

<jsp:include page="footer.jsp"/>

<script src="js/app.js"></script>
</body>
</html>
