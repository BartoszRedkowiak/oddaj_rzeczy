<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h2>Podaj adres email w celu odzyskania hasła</h2>
    <form method="post">
        <div class="form-group">
            <input type="email" name="email" placeholder="Email" />
            <div class="formError">${emailPatternError}</div>
        </div>
        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">Wyślij</button>
        </div>
    </form>
    <c:if test="${not empty errorMessage}">
        <div class="formError">${errorMessage}</div>
    </c:if>
</section>

<jsp:include page="0_footer.jsp"/>
</body>
</html>
