<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <h2>Lista użytkowników</h2>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Id</th>
                <th scope="col">Imię</th>
                <th scope="col">Nazwisko</th>
                <th scope="col">Email</th>
                <th scope="col">Aktywny</th>
                <th scope="col">Akcje</th>
            </tr>
            </thead>
            <tbody class="table-striped table-borderless">
            <c:forEach items="${users}" var="user" varStatus="status">
                <tr>
                    <td scope="row">${status.index + 1}</td>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td><a href="/admin/toggle/${user.id}" class="btn btn--small">${user.enabled == true ? 'TAK' : 'NIE'}</a></td>
                    <td>
                        <a href="/admin/edit/${user.id}" class="btn btn--small">Edytuj</a>
                        <a href="/admin/delete/${user.id}" class="btn btn--small">Usuń</a>
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
