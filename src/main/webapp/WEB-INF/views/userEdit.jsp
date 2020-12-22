<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Document</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css"
          integrity="sha384-vp86vTRFVJgpjF9jiIGPEEqYqlDwgyBgEF109VFjmqGmIY/Y4HV4d3Gp2irVfcrp" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<script src="<c:url value="/resources/js/app.js"/>"></script>


<body>
<header class="header--main-page">
    <i id="myBtn" class="fas fa-arrow-alt-circle-up"></i>
    <nav class="container container--70">
        <sec:authorize access="isAuthenticated()">
            <sec:authorize access="hasRole('USER')">
                <ul class="nav--actions">
                    <li class="logged-user">
                        Witaj ${name}
                        <ul class="dropdown">
                            <li><a href="/user/panel">Profil</a></li>
                            <li><a href="/user/donations">Moje zbiórki</a></li>
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
        <div class="slogan--item sth">
            <h1>
                Panel użytkownika<br/>
            </h1>

            <a href="/" class="btn btn--large userPanel">Strona głowna</a>
            <a href="/user/panel" class="btn btn--large userPanel">Panel użytkownika</a>
        </div>
    </div>
</header>

<div class="form--steps-container panelInput">
    <form:form cssClass="form--contact" method="post" modelAttribute="user">
        <div class="panelInput">
        <div class="form-group form-group panel">
            <h2>Imię:</h2><form:input cssClass="panelInput" path="name"/>
        </div>
        <div class="form-group form-group panel">
            <h2>Nazwisko:</h2><form:input cssClass="panelInput" path="surname"/>
        </div>
        <div class="form-group form-group panel">
            <h2>Email:</h2><form:input cssClass="panelInput" path="email"/>
        </div>
        </div>
        <button class="btn" type="submit">Zapisz</button>

    </form:form>
</div>

<jsp:include page="footer.jsp"/>


</body>
