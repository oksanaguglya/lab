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
        <%-- Search form--%>
    <form class="search" name="search" action="LibraryServlet" id="form" method="POST">
        <input type="hidden" name="command" value="go_to_order_reader_page">
        <input class="search-text" type="text" name="searchOrder" value="${sessionScope.searchOrder}"
               placeholder=<fmt:message key="catalog.search.placeholder"/>/>
        <button type="submit" class="btn btn-info"><fmt:message key="catalog.search.button.text"/></button>
    </form>
        <%--Table of orders--%>
    <c:if test="${noOfPages != 0}">
        <table border="2" id="table" cellpadding="5" cellspacing="1">
            <tr>
                <th class="table-col-date"><fmt:message key="order.date"></fmt:message></th>
                <th><fmt:message key="catalog.title"></fmt:message></th>
                <th><fmt:message key="catalog.author"></fmt:message></th>
                <th class="table-col-year"><fmt:message key="catalog.year"></fmt:message></th>
                <th class="table-col-bookType"><fmt:message key="catalog.bookType"></fmt:message></th>
                <th class="table-col-qty"><fmt:message key="catalog.qty"></fmt:message></th>
                <th class="table-col-state"><fmt:message key="order.state"></fmt:message></th>
                <th class="table-col-process-user"></th>
            </tr>
            <c:forEach var="item" items="${requestScope.ordersItems}">
                <tr>
                    <td class="table-col-date fs"><c:out value="${item.getDateOfOrder()}"/></td>
                    <td class="fs"><c:out value="${item.getCatalogItem().getBook().getTitle()}"/></td>
                    <td class="fs"><c:out value="${item.getCatalogItem().getBook().getAuthor()}"/></td>
                    <td class="table-col-year fs"><c:out value="${item.getCatalogItem().getBook().getYear()}"/></td>
                    <c:choose>
                        <c:when test="${item.getCatalogItem().getBook().getType() == 'READING_ROOM'}">
                            <td class="table-col-bookType fs"><fmt:message
                                    key="catalog.reading_room"></fmt:message></td>
                        </c:when>
                        <c:otherwise>
                            <td class="table-col-bookType fs"><fmt:message
                                    key="catalog.library_card"></fmt:message></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="table-col-qty fs"><c:out value="${item.getQuantity()}"/></td>
                    <c:choose>
                        <c:when test="${item.getType() == 'APPROVED'}">
                            <td class="table-col-qty fs"><fmt:message key="order.approved"></fmt:message></td>
                        </c:when>
                        <c:when test="${item.getType() == 'DENIED'}">
                            <td class="table-col-qty fs"><fmt:message key="order.denied"></fmt:message></td>
                        </c:when>
                        <c:when test="${item.getType() == 'RETURNED'}">
                            <td class="table-col-qty fs"><fmt:message key="order.returned"></fmt:message></td>
                        </c:when>
                        <c:otherwise>
                            <td class="table-col-qty fs"><fmt:message key="order.proc"></fmt:message></td>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                    <c:when test="${item.getType() == 'APPROVED'}">
                    <td class="table-col-process-user">
                        <div class="center">
                            <form class="inline" name="returnOrder" action="LibraryServlet" method="POST">
                                <input type="hidden" name="command" value="return_user_order"/>
                                <input type="hidden" name="idOrderReturn" value="${item.getId()}">
                                <input type="hidden" name="page" value=${currentPage}>
                                <button class="btn" type="submit" name="action"><fmt:message key="catalog.return"></fmt:message></button>
                            </form>
                        </div>
                    </td>
                    </c:when>
                    <c:when test="${item.getType() == 'IN_PROCESSING'}">
                    <td class="table-col-process-user">
                        <div class="center">
                            <form class="inline" name="delOrder" action="LibraryServlet" method="POST">
                                <input type="hidden" name="command" value="del_user_order"/>
                                <input type="hidden" name="idOrderDel" value="${item.getId()}">
                                <input type="hidden" name="page" value=${currentPage}>
                                <button class="btn" type="submit" name="action"><fmt:message key="catalog.delete"></fmt:message></button>
                            </form>
                        </div>
                    </td>
                        </c:when>
                            <c:otherwise> <td class="table-col-process-user"></td></c:otherwise>
                        </c:choose>
                </tr>
            </c:forEach>
        </table>
        <br>
    </c:if>
        <%--Pagination--%>
    <tag:pagination currentPage="${currentPage}" command="go_to_order_reader_page"
                    numberOfPages="${noOfPages}"></tag:pagination>
        <%--Messages--%>
    <div class="text-message"><h2>${successDelOrderMessage}${successReturnOrderMessage}</h2></div>
    <div class="text-message"><h2>${emptySearchOrderMessage}</h2></div>

    </body>
    <%@include file="../../footer.jsp" %>
</fmt:bundle>
</html>
