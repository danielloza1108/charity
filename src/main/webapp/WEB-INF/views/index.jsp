<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<body>
<jsp:include page="header.jsp"/>
<i id="myBtn" class="fas fa-arrow-alt-circle-up"></i>
<section class="stats">
    <div class="container container--85">
        <div class="stats--item">

            <em>${donationsCount.get()}</em>

            <h3>Oddanych worków</h3>
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Eius est beatae, quod accusamus illum
                tempora!</p>
        </div>

        <div class="stats--item">
            <em>${donations.get()}</em>
            <h3>Przekazanych darów</h3>
            <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Laboriosam magnam, sint nihil cupiditate quas
                quam.</p>
        </div>

    </div>
</section>

<section class="steps" id="steps">
    <h2>Wystarczą 4 proste kroki</h2>

    <div class="steps--container">
        <div class="steps--item">
            <span class="icon icon--hands"></span>
            <h3>Wybierz rzeczy</h3>
            <p>ubrania, zabawki, sprzęt i inne</p>
        </div>
        <div class="steps--item">
            <span class="icon icon--arrow"></span>
            <h3>Spakuj je</h3>
            <p>skorzystaj z worków na śmieci</p>
        </div>
        <div class="steps--item">
            <span class="icon icon--glasses"></span>
            <h3>Zdecyduj komu chcesz pomóc</h3>
            <p>wybierz zaufane miejsce</p>
        </div>
        <div class="steps--item">
            <span class="icon icon--courier"></span>
            <h3>Zamów kuriera</h3>
            <p>kurier przyjedzie w dogodnym terminie</p>
        </div>
    </div>
    <sec:authorize access="isAnonymous()">
    <a href="/register" class="btn btn--large">Załóż konto</a>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
    <a href="/form" class="btn btn--large">Złoż dotacje</a>
    </sec:authorize>
</section>

<section class="about-us" id="about-us">
    <div class="about-us--text">
        <h2>O nas</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptas vitae animi rem pariatur incidunt libero
            optio esse quisquam illo omnis.</p>
        <img src="<c:url value="resources/images/signature.svg"/>" class="about-us--text-signature" alt="Signature"/>
    </div>
    <div class="about-us--image"><img src="<c:url value="resources/images/about-us.jpg"/>" alt="People in circle"/>
    </div>
</section>

<section class="help" id="help">
    <h2>Komu pomagamy?</h2>

    <!-- SLIDE 1 -->
    <div class="help--slides active" data-id="1">
        <p>W naszej bazie znajdziesz listę zweryfikowanych Fundacji, z którymi współpracujemy.
            Możesz sprawdzić czym się zajmują.</p>

        <ul class="help--slides-items">
            <li>
                <c:forEach items="${institution}" var="sth" end="${institution.size()/2-1}">
                <div class="col">
                    <div class="title">Fundacja "${sth.getName()}"</div>
                    <div class="subtitle">Cel i misja: ${sth.getDescription()}</div>

                </div>
                </c:forEach>
            </li>
            <li>
                <c:forEach items="${institution}" var="sth" begin="${institution.size()/2}">
                <div class="col">
                        <div class="title">Fundacja "${sth.getName()}"</div>
                        <div class="subtitle">Cel i misja: ${sth.getDescription()}</div>
                </div>
                </c:forEach>
            </li>
        </ul>
    </div>

</section>
<footer>
    <div class="contact" id="contact">
        <h2>Skontaktuj się z nami</h2>
        <h3>Formularz kontaktowy</h3>
        <form class="form--contact" action="/message" method="post">
            <div class="form-group form-group"><spring:bind path="message.email"><input type="text" name="email" placeholder="Email"/></spring:bind></div>
            <div class="form-group form-group--50"><spring:bind path="message.name"><input type="text" name="name" placeholder="Imię"/></spring:bind></div>
            <div class="form-group form-group--50"><spring:bind path="message.surname"><input type="text" name="surname" placeholder="Nazwisko"/></spring:bind></div>

            <div class="form-group"><spring:bind path="message.message"><textarea name="message" placeholder="Wiadomość" rows="1"></textarea></spring:bind></div>

            <button class="btn" type="submit">Wyślij</button>
        </form>
    </div>
    <div class="bottom-line">
        <span class="bottom-line--copy">Copyright &copy; 2018</span>
        <div class="bottom-line--icons">
            <a href="#" class="btn btn--small"><img src="<c:url value="resources/images/icon-facebook.svg"/>"/></a> <a href="#"
                                                                                                                       class="btn btn--small"><img
                src="<c:url value="resources/images/icon-instagram.svg"/>"/></a>
        </div>
    </div>
</footer>
<script src="<c:url value="resources/js/app.js"/>"></script>
</body>
</html>
