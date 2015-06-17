<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <%@include file="../../header.jsp" %>
    <head>
        <link href="/css/1.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

    <form class="search" name="search" action="LibraryServlet" id="form" method="POST">
        <input type="hidden" name="command" value="go_to_order_reader_page">
        <input class="search-text" type="text" name="searchOrder" value="${sessionScope.searchOrder}"
               placeholder=<fmt:message key="catalog.search.placeholder"/>/>
        <button type="submit" class="btn btn-info"><fmt:message key="catalog.search.button.text"/></button>
    </form>

    <c:if test="${noOfPages != 0}">
        <table border="2" id="table" cellpadding="5" cellspacing="1">
            <tr>
                <th><fmt:message key="catalog.title"></fmt:message></th>
                <th><fmt:message key="catalog.author"></fmt:message></th>
                <th class="table-col-year"><fmt:message key="catalog.year"></fmt:message></th>
                <th class="table-col-bookType"><fmt:message key="catalog.bookType"></fmt:message></th>
                <th class="table-col-qty"><fmt:message key="catalog.qty"></fmt:message></th>
                <th class="table-col-state"><fmt:message key="order.state"></fmt:message></th>
            </tr>
            <c:forEach var="item" items="${requestScope.ordersItems}">
                <tr>
                    <td><c:out value="${item.getBook().getTitle()}"/></td>
                    <td><c:out value="${item.getBook().getAuthor()}"/></td>
                    <td class="table-col-year"><c:out value="${item.getBook().getYear()}"/></td>
                    <c:choose>
                        <c:when test="${item.getBook().getType() == 'LIBRARY_CARD'}">
                            <td class="table-col-bookType"><fmt:message key="catalog.library_card"></fmt:message></td>
                        </c:when>
                        <c:otherwise>
                            <td class="table-col-bookType"><fmt:message key="catalog.reading_room"></fmt:message></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="table-col-qty"><c:out value="${item.getQuantity()}"/></td>
                    <c:choose>
                        <c:when test="${item.getState() == 'APPROVED'}">
                            <td class="table-col-qty"><fmt:message key="order.approved"></fmt:message></td>
                        </c:when>
                        <c:when test="${item.getState() == 'DENIED'}">
                            <td class="table-col-qty"><fmt:message key="order.denied"></fmt:message></td>
                        </c:when>
                        <c:otherwise>
                            <td class="table-col-qty"><fmt:message key="order.proc"></fmt:message></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
        <br>
    </c:if>

        <%-- <span class="pagination-wrap">--%>
    <div class="pagination">
        <ul>
            <c:if test="${currentPage != 1}">
                <li>
                    <form name="goPreviousPageForm" method="POST" action="LibraryServlet">
                        <input type="hidden" name="command" value="go_to_order_reader_page">
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
                                <input type="hidden" name="command" value="go_to_order_reader_page">
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
                        <input type="hidden" name="command" value="go_to_order_reader_page"/>
                        <input type="hidden" name="page" value=${currentPage + 1}>
                        <A HREF="javascript:document.goNextPageForm.submit()" class="pagination_next">></A>
                    </form>
                </li>
            </c:if>
        </ul>
    </div>
        <%--</span>--%>

    <div class="text-message"><h2>${emptySearchOrderMessage}</h2></div>
        <%--<c:choose>
            <c:when test="${sessionScope.user.getType() == 'READER'}">
                <div class="center">
                    <div class="text-message inline"><h2>${orderNoChecksMessage}${successOrderMessage}</h2></div>
                    <c:if test="${numOfOrdersMessage > 0}">
                        <div class="text-message inline"><h2>(${numOfSuccessOrdersMessage}/${numOfOrdersMessage})</h2></div>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>--%>

    </body>
    <%@include file="../../footer.jsp" %>
</fmt:bundle>
</html>
