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
    <input type="hidden" name="command" value="go_to_catalog_page"/>
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

<c:if test="${currentPage != 1}">
    <form class="pad" name="goPreviousPageForm" method="POST" action="LibraryServlet">
        <input type="hidden" name="command" value="go_to_catalog_page">
        <input type="hidden" name="page" value=${currentPage - 1}>
        <td><A HREF="javascript:document.goPreviousPageForm.submit()">Previous</A></td>
    </form>
</c:if>

<table border="1" cellpadding="2" cellspacing="2">
    <tr>
        <c:forEach var="i" begin="1" end="${noOfPages}">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <form class="pad" id="goPageForm${i}" method="POST" action="LibraryServlet">
                        <input type="hidden" name="command" value="go_to_catalog_page">
                        <input type="hidden" name="page" value=${i}>
                        <td><A HREF="javascript:document.getElementById('goPageForm${i}').submit()">${i}</A></td>
                    </form>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<c:if test="${currentPage lt noOfPages}">
    <form class="pad" name="goNextPageForm" method="POST" action="LibraryServlet">
        <input type="hidden" name="command" value="go_to_catalog_page"/>
        <input type="hidden" name="page" value=${currentPage + 1}>
        <td><A HREF="javascript:document.goNextPageForm.submit()">Next</A></td>
    </form>
</c:if>

</body>
<%@include file="footer.jsp" %>
</fmt:bundle>
</html>
