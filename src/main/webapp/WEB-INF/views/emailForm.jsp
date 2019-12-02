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
    <h2>Podaj adres email w celu odzyskania hasłą</h2>
    <form method="post" action="/forgot-password">
        <div class="form-group">
            <input type="email" name="email" placeholder="Email" />
        </div>
        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">Wyślij</button>
        </div>
    </form>
</section>

<jsp:include page="0_footer.jsp"/>
</body>
</html>
