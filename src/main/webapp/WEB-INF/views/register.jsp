<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <jsp:include page="0_header.jsp"/>
</head>
<body>
<header>
    <jsp:include page="0_navbar.jsp"/>
</header>

<section class="login-page">
    <h2>Załóż konto</h2>
    <form:form modelAttribute="user" method="post">
        <div class="form-group">
            <form:input path="firstName" placeholder="Imię" />
            <form:errors path="firstName" element="div" cssClass="formError"/>
        </div>
        <div class="form-group">
            <form:input path="lastName" placeholder="Nazwisko" />
            <form:errors path="lastName" element="div" cssClass="formError"/>
        </div>
        <div class="form-group">
            <form:input path="email" type="email" placeholder="Email" />
            <form:errors path="email" element="div" cssClass="formError"/>
        </div>
        <div class="form-group">
            <form:password path="password" placeholder="Hasło" />
            <form:errors path="password" element="div" cssClass="formError"/>
        </div>
        <div class="form-group">
            <input type="password" name="password2" placeholder="Powtórz hasło" />
            <form:errors path="password" element="div" cssClass="formError"/>
        </div>
        <div class="form-group form-group--buttons">
            <a href="/login" class="btn btn--without-border">Zaloguj się</a>
            <button class="btn" type="submit">Załóż konto</button>
        </div>
    </form:form>
</section>

<jsp:include page="0_footer.jsp"/>
</body>
</html>
