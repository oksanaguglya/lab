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

    <form class="search" name="search" action="../LibraryServlet" method="POST">
        <input type="hidden" name="command" value="go_to_catalog_page"/>
        <input class="search_text" type="text" name="search" value="${sessionScope.search}"
               placeholder=<fmt:message key="catalog.search.placeholder"/>/>
        <button type="submit" class="btn btn-info"><fmt:message key="catalog.search.button.text"/></button>
    </form>

    <table border="2" cellpadding="8" cellspacing="1">
        <tr>
            <th><fmt:message key="catalog.title"></fmt:message></th>
            <th><fmt:message key="catalog.author"></fmt:message></th>
            <th class="table_col_year"><fmt:message key="catalog.year"></fmt:message></th>
            <th class="table_col_quantity"><fmt:message key="catalog.quantity"></fmt:message></th>
            <th class="table_col_bookType"><fmt:message key="catalog.bookType"></fmt:message></th>
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
    <br>

    <div class="pagination">
        <ul>
            <c:if test="${currentPage != 1}">
                <li>
                    <form name="goPreviousPageForm" method="POST" action="LibraryServlet">
                        <input type="hidden" name="command" value="go_to_catalog_page">
                        <input type="hidden" name="page" value=${currentPage - 1}>
                        <A HREF="javascript:document.goPreviousPageForm.submit()" class="pagination_prev"><</A>
                    </form>
                </li>
            </c:if>


            <c:forEach var="i" begin="1" end="${noOfPages}">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="pagination_active">
                            <a href="#">${i}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <form id="goPageForm${i}" method="POST" action="LibraryServlet">
                                <input type="hidden" name="command" value="go_to_catalog_page">
                                <input type="hidden" name="page" value=${i}>
                                <A HREF="javascript:document.getElementById('goPageForm${i}').submit()">${i}</A>
                            </form>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>


            <c:if test="${currentPage lt noOfPages}">
                <li>
                    <form name="goNextPageForm" method="POST" action="LibraryServlet">
                        <input type="hidden" name="command" value="go_to_catalog_page"/>
                        <input type="hidden" name="page" value=${currentPage + 1}>
                        <A HREF="javascript:document.goNextPageForm.submit()" class="pagination_next">></A>
                    </form>
                </li>
            </c:if>
        </ul>
    </div>
    </body>
    <%@include file="footer.jsp" %>
</fmt:bundle>
</html>
