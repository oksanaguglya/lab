<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <%@include file="header.jsp" %>
<head>
    <link href="/css/1.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<form name="search" action="../LibraryServlet" method="POST">
    <input type="hidden" name="command" value="search"/>
    <input type="text" name="search" placeholder=<fmt:message key="catalog.search.placeholder"/> />
    <button type="submit" class="btn btn-info"><fmt:message key="catalog.search.button.text"/></button>
</form>
<table border="2" cellpadding="8" cellspacing="1">
    <tr>
        <th><fmt:message key="catalog.title"></fmt:message></th>
        <th><fmt:message key="catalog.author"></fmt:message></th>
        <th><fmt:message key="catalog.year"></fmt:message></th>
        <th><fmt:message key="catalog.quantity"></fmt:message></th>
        <th><fmt:message key="catalog.bookType"></fmt:message></th>
    </tr>
    <c:forEach var="item" items="${requestScope.catalogItems}">
        <tr>
            <td><c:out value="${item.getBook().getTitle()}"/></td>
            <td><c:out value="${item.getBook().getAuthor()}"/></td>
            <td><c:out value="${item.getBook().getYear()}"/></td>
            <td><c:out value="${item.getQuantity()}"/></td>
            <td><c:out value="${item.getBook().getType()}"/></td>
        </tr>
    </c:forEach>
    </table>
</body>
<%@include file="footer.jsp" %>
</fmt:bundle>
</html>
