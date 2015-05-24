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
<table border="2" cellpadding="5" cellspacing="5">
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>PublicationYear</th>
        <th>Quantity</th>
        <th>Home/ReaderRoom</th>
    </tr>

    <%--<c:forEach var="item" items="${CatalogItem}">
        <tr>
            <td>${}</td>
            <td>${}}</td>
            <td>${}</td>
            <td>${}</td>
        </tr>
    </c:forEach>--%>
    </table>
</body>
<%@include file="footer.jsp" %>
</fmt:bundle>
</html>
