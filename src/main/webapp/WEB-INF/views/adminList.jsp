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
        <h2>Dodaj administratora</h2>
        <form:form modelAttribute="user" method="post" action="/admin/newAdmin">
            <div class="form-group">
                <form:input path="firstName" placeholder="Imię"/>
                <form:errors path="firstName" element="div" cssClass="formError"/>
            </div>
            <div class="form-group">
                <form:input path="lastName" placeholder="Nazwisko"/>
                <form:errors path="lastName" element="div" cssClass="formError"/>
            </div>
            <div class="form-group">
                <form:input path="email" type="email" placeholder="Email"/>
                <form:errors path="email" element="div" cssClass="formError"/>
            </div>
            <div class="form-group">
                <form:password path="password" placeholder="Hasło"/>
                <form:errors path="password" element="div" cssClass="formError"/>
            </div>
            <div class="form-group">
                <input type="password" name="password2" placeholder="Powtórz hasło"/>
                <form:errors path="password" element="div" cssClass="formError"/>
            </div>
            <div class="form-group form-group--buttons">
                <button class="btn btn--small" type="submit">Dodaj</button>
            </div>
        </form:form>
    </div>
    <div class="table--container">
        <h2>Lista administratorów</h2>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Id</th>
                <th scope="col">Imię</th>
                <th scope="col">Nazwisko</th>
                <th scope="col">Email</th>
                <th scope="col">Akcje</th>
            </tr>
            </thead>
            <tbody class="table-striped table-borderless">
            <c:forEach items="${admins}" var="admin" varStatus="status">
                <tr>
                    <td scope="row">${status.index + 1}</td>
                    <td>${admin.id}</td>
                    <td>${admin.firstName}</td>
                    <td>${admin.lastName}</td>
                    <td>${admin.email}</td>
                    <td>
                        <a href="/admin/edit/${admin.id}" class="btn btn--small">Edytuj</a>
                        <a href="/admin/delete/${admin.id}" class="btn btn--small">Usuń</a>
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
