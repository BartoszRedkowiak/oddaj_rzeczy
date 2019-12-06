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

        <h2>Edytuj hasło</h2>
        <form method="post">
            <div class="form-group">
                <input type="password" name="password1" placeholder="Podaj nowe hasło"/>
                <div class="formError">${passwordPatternError}</div>
            </div>
            <div class="form-group">
                <input type="password" name="password2" placeholder="Powtórz nowe hasło"/>
                <div class="formError">${passwordEqualsError}</div>
            </div>
            <div class="form-group form-group--buttons">
                <button class="btn" type="submit">Zapisz</button>
            </div>
        </form>
</section>

<jsp:include page="0_footer.jsp"/>
</body>
</html>
