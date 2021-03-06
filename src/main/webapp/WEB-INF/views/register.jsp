<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<body>
<jsp:include page="header.jsp"/>

<section class="login-page">
    <h2>Załóż konto</h2>
    <form:form modelAttribute="user" method="post">
        <div class="form-group">
            <form:input path="name" type="text" name="name" placeholder="Imię" />
            <br/>
            <form:errors path="name" cssClass="error"/>
        </div>
        <div class="form-group">
            <form:input path="surname" type="text" name="surname" placeholder="Nazwisko" />
            <br/>
            <form:errors path="surname" cssClass="error"/>
        </div>
        <div class="form-group">
            <form:input path="email" type="email" name="email" placeholder="Email" />
            <form:errors cssClass="error" path="email" element="div"/>
            <br/>
            <span class="error">${emailMessage}</span>
        </div>
        <div class="form-group">
            <form:input path="password" type="password" name="password" placeholder="Hasło" />
            <br/>
            <form:errors path="password" cssClass="error"/>
        </div>
        <div class="form-group">
            <input type="password" name="password2" placeholder="Powtórz hasło" />
            <br/>
            <span class="error">${passwordNotMatch}</span>
        </div>

        <div class="form-group--buttons">
            <a href="/login" class="btn btn--without-border">Zaloguj się</a>
            <button class="btn" type="submit">Załóż konto</button>
        </div>
    </form:form>
</section>

<jsp:include page="footer.jsp"/>
</body>
</html>
