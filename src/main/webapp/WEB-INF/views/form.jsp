<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="0_header.jsp"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>

<header class="header--form-page">
    <jsp:include page="0_navbar.jsp"/>

    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                Oddaj rzeczy, których już nie chcesz<br/>
                <span class="uppercase">potrzebującym</span>
            </h1>

            <div class="slogan--steps">
                <div class="slogan--steps-title">Wystarczą 4 proste kroki:</div>
                <ul class="slogan--steps-boxes">
                    <li>
                        <div><em>1</em><span>Wybierz rzeczy</span></div>
                    </li>
                    <li>
                        <div><em>2</em><span>Spakuj je w worki</span></div>
                    </li>
                    <li>
                        <div><em>3</em><span>Wybierz fundację</span></div>
                    </li>
                    <li>
                        <div><em>4</em><span>Zamów kuriera</span></div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</header>

<section class="form--steps">
    <div class="form--steps-instructions">
        <div class="form--steps-container">
            <h3>Ważne!</h3>
            <p data-step="1" class="active">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="2">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="3">
                Wybierz jedną, do
                której trafi Twoja przesyłka.
            </p>
            <p data-step="4">Podaj adres oraz termin odbioru rzeczy.</p>
        </div>
    </div>

    <div class="form--steps-container">
        <div class="form--steps-counter">Krok <span>1</span>/4</div>
        <form:form modelAttribute="donation" method="post">
            <!-- STEP 1: class .active is switching steps -->
            <div data-step="1" class="active">
                <h3>Zaznacz co chcesz oddać:</h3>


                <c:forEach items="${categories}" var="category">
                    <div class="form-group form-group--checkbox">
                        <label>
                            <input type="checkbox" name="categories" value="${category.id}"/>
                            <span class="checkbox"></span>
                            <span class="description">${category.name}</span>
                        </label>
                    </div>
                </c:forEach>
                <form:errors path="categories" element="div" cssClass="formError"/>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 2 -->
            <div data-step="2">
                <h3>Podaj liczbę 60l worków, w które spakowałeś/aś rzeczy:</h3>

                <div class="form-group form-group--inline">
                    <form:label path="quantity">Liczba 60l worków:</form:label>
                    <form:input path="quantity" type="number" step="1" min="1" id="formQuantity"/>
                    <form:errors path="quantity" element="div" cssClass="formError"/>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 3 -->
            <div data-step="3">
                <h3>Wybierz organizacje, której chcesz pomóc:</h3>

                <c:forEach items="${institutions}" var="institution">
                    <div class="form-group form-group--checkbox">
                        <label>
                            <input type="radio" name="institution" value="${institution.id}"/>
                            <span class="checkbox radio"></span>
                            <span class="description">
                  <div class="title">Fundacja “${institution.name}”</div>
                  <div class="subtitle">
                    Cel i misja: ${institution.description}
                  </div>
                </span>
                        </label>
                    </div>
                </c:forEach>
                <form:errors path="institution" element="div" cssClass="formError"/>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 4 -->
            <div data-step="4">
                <h3>Podaj adres oraz termin odbioru rzecz przez kuriera:</h3>

                <div class="form-section form-section--columns">
                    <div class="form-section--column">
                        <h4>Adres odbioru</h4>
                        <div class="form-group form-group--inline">
                            <form:label path="street">Ulica</form:label>
                            <form:input path="street" id="formStreet"/>
                            <form:errors path="street" element="div" cssClass="formError"/>
                        </div>

                        <div class="form-group form-group--inline">
                            <form:label path="city">Miasto</form:label>
                            <form:input path="city" id="formCity"/>
                            <form:errors path="city" element="div" cssClass="formError"/>
                        </div>

                        <div class="form-group form-group--inline">
                            <form:label path="zipCode">Kod pocztowy</form:label>
                            <form:input path="zipCode" id="formZipCode"/>
                            <form:errors path="zipCode" element="div" cssClass="formError"/>
                        </div>

                        <div class="form-group form-group--inline">
                            <label for="formPhone">Numer telefonu</label>
                            <input type="phone" name="phone" id="formPhone"/>
                        </div>
                    </div>

                    <div class="form-section--column">
                        <h4>Termin odbioru</h4>
                        <div class="form-group form-group--inline">
                            <form:label path="pickUpDate">Data</form:label>
                            <form:input path="pickUpDate" type="date" id="formDate"/>
                            <form:errors path="pickUpDate" element="div" cssClass="formError"/>
                        </div>

                        <div class="form-group form-group--inline">
                            <form:label path="pickUpTime">Godzina</form:label>
                            <form:input path="pickUpTime" type="time" id="formTime"/>
                            <form:errors path="pickUpTime" element="div" cssClass="formError"/>
                        </div>

                        <div class="form-group form-group--inline">
                            <form:label path="pickUpComment">Uwagi dla kuriera</form:label>
                            <form:textarea path="pickUpComment" rows="5" id="formComment"/>
                            <form:errors path="pickUpComment" element="div" cssClass="formError"/>
                        </div>
                    </div>
                </div>
                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 6 -->
            <div data-step="5">
                <h3>Podsumowanie Twojej darowizny</h3>

                <div class="summary">
                    <div class="form-section">
                        <h4>Oddajesz:</h4>
                        <ul>
                            <li>
                                <span class="icon icon-bag"></span>
                                <span class="summary--text" id="summItems">4 worki ubrań w dobrym stanie dla dzieci</span>
                            </li>

                            <li>
                                <span class="icon icon-hand"></span>
                                <span class="summary--text">Dla fundacji "<span id="summInstitution">Mam marzenie</span>" w Warszawie</span>
                            </li>
                        </ul>
                    </div>

                    <div class="form-section form-section--columns">
                        <div class="form-section--column">
                            <h4>Adres odbioru:</h4>
                            <ul>
                                <li>Adres:&nbsp<span id="summStreet">brak danych</span></li>
                                <li>Miasto:&nbsp<span id="summCity">brak danych</span></li>
                                <li>Kod Zip:&nbsp<span id="summZipCode">brak danych</span></li>
                                <li>Nr tel.:&nbsp<span id="summPhone">brak danych</span></li>
                            </ul>
                        </div>

                        <div class="form-section--column">
                            <h4>Termin odbioru:</h4>
                            <ul>
                                <li>Data:&nbsp<span id="summDate">brak danych</span></li>
                                <li>Godzina:&nbsp<span id="summTime">brak danych</span></li>
                                <li>Uwagi:&nbsp<span id="summComment">brak danych</span></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="submit" class="btn">Potwierdzam</button>

                </div>
            </div>
        </form:form>
    </div>
</section>


<jsp:include page="0_footer.jsp"/>
<script src="<c:url value="/resources/js/summary.js"/>"></script>

</body>
</html>
