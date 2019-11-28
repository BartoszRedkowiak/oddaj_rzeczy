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
    <h2>Edytuj dane</h2>
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
        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">Zapisz zmiany</button>
        </div>
    </form:form>
</section>

<jsp:include page="0_footer.jsp"/>
</body>
</html>
