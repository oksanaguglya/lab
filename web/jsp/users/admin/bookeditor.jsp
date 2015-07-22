<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../../header.jsp" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <head></head>
    <body>
    <script src="js/validation.js"></script>
    <div>
        <div class="add-book-form">
            <form name="editorBook" action="LibraryServlet" onsubmit="return validateBookForm()" method="POST">
                <input type="hidden" name="command" value="edit_book_in_catalog"/>

                <div><fmt:message key="catalog.title"/></div>
                <input class="text-width" type="text" name="title" value="${requestScope.title}"
                       placeholder=<fmt:message key="catalog.text.title"/>/><br/><br/>

                <div><fmt:message key="catalog.author"/></div>
                <input class="text-width" type="text" name="author" value="${requestScope.author}"
                       placeholder=<fmt:message key="catalog.text.author"/>/><br/><br/>

                <div><fmt:message key="catalog.year.full"/></div>
                <input class="text-width" type="text" name="year" value="${requestScope.year}"
                       placeholder=<fmt:message key="catalog.text.year"/>/><br/><br/>

                <div><fmt:message key="catalog.bookType.full"/></div>
                <select name="bookType" class="text-width">
                    <c:choose>
                        <c:when test="${requestScope.bookType == 'LIBRARY_CARD'}">
                            <option value="LIBRARY_CARD"><fmt:message key="catalog.bookType.library_card"/></option>
                            <option value="READING_ROOM"><fmt:message key="catalog.bookType.reading_room"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value="READING_ROOM"><fmt:message key="catalog.bookType.reading_room"/></option>
                            <option value="LIBRARY_CARD"><fmt:message key="catalog.bookType.library_card"/></option>
                        </c:otherwise>
                    </c:choose>
                </select><br/><br/>

                <div><fmt:message key="catalog.quantity.full"/></div>
                <input class="text-width" type="text" name="quantity" value="${requestScope.quantity}"
                       placeholder=<fmt:message key="catalog.text.quantity"/>/><br/><br/>

                <button type="submit" class="btn btn-info"><fmt:message key="catalog.editBookInCatalog"/></button>
            </form>
        </div>

        <div class="text-message"><h2>${resultMessage}</h2></div>

    </div>
    </body>
    <%@include file="../../footer.jsp" %>
</fmt:bundle>
</html>
