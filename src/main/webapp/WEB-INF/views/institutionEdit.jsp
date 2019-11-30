<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <jsp:include page="0_header.jsp"/>
</head>
<body>

<header>
    <jsp:include page="0_navbar.jsp"/>
</header>

<section>
    <div class="table--container">
        <h2>Edytuj dane instytucji</h2>
        <form:form modelAttribute="institution" method="post">
            <div class="form-group">
                <form:input path="name" placeholder="Nazwa instytucji"/>
                <form:errors path="name" element="div" cssClass="formError"/>
            </div>
            <div class="form-group">
                <form:textarea path="description" placeholder="Opis instytucji" rows="5"/>
                <form:errors path="description" element="div" cssClass="formError"/>
            </div>
            <div class="form-group form-group--buttons">
                <button class="btn btn--small" type="submit">Zapisz</button>
            </div>
        </form:form>
    </div>
</section>

<script src="<c:url value="/webjars/jquery/3.1.1/jquery.min.js"/>"></script>
<script src="<c:url value="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"/>"></script>
</body>
</html>
