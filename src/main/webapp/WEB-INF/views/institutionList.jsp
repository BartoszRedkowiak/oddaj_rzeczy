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
        <h2>Dodaj instytucję</h2>
        <form:form modelAttribute="newInstitution" method="post" action="/admin/newInstitution">
            <div class="form-group">
                <form:input path="name" placeholder="Nazwa instytucji"/>
                <form:errors path="name" element="div" cssClass="formError"/>
            </div>
            <div class="form-group">
                <form:textarea path="description" placeholder="Opis instytucji" rows="5"/>
                <form:errors path="description" element="div" cssClass="formError"/>
            </div>
            <div class="form-group form-group--buttons">
                <button class="btn btn--small" type="submit">Dodaj</button>
            </div>
        </form:form>
    </div>
    <div class="table--container">
        <h2>Lista instytucji</h2>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Id</th>
                <th scope="col">Nazwa</th>
                <th scope="col">Opis</th>
                <th scope="col">Akcje</th>
            </tr>
            </thead>
            <tbody class="table-striped table-borderless">
            <c:forEach items="${institutions}" var="institution" varStatus="status">
                <tr>
                    <td scope="row">${status.index + 1}</td>
                    <td>${institution.id}</td>
                    <td>${institution.name}</td>
                    <td>${institution.description}</td>
                    <td>
                        <a href="#" class="btn btn--small">Edytuj</a>
                        <a href="#" class="btn btn--small">Usuń</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>

<script src="<c:url value="/webjars/jquery/3.1.1/jquery.min.js"/>"></script>
<script src="<c:url value="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"/>"></script>
</body>
</html>
