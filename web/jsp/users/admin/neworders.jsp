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
        <%--Table of orders--%>
    <c:if test="${noOfPages != 0}">
        <table border="2" id="table" cellpadding="5" cellspacing="1">
            <tr>
                <th class="table-col-date"><fmt:message key="order.date"></fmt:message></th>
                <th class="table-col-reader"><fmt:message key="order.reader"></fmt:message></th>
                <th><fmt:message key="catalog.title"></fmt:message></th>
                <th class="table-col-year"><fmt:message key="catalog.year"></fmt:message></th>
                <th class="table-col-bookType"><fmt:message key="catalog.bookType"></fmt:message></th>
                <th class="table-col-quantity"><fmt:message key="catalog.quantity"></fmt:message></th>
                <th class="table-col-qty"><fmt:message key="catalog.qty"></fmt:message></th>
                <th class="table-col-process"></th>
            </tr>
            <c:forEach var="item" items="${requestScope.newOrdersItems}">
                <tr>
                    <td class="table-col-date fs"><c:out value="${item.getDateOfOrder()}"/></td>
                    <td class="table-col-reader fs"><c:out value="${item.getUserName()}"/></td>
                    <td class="fs"><c:out value="${item.getCatalogItem().getBook().getTitle()}"/></td>
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
                    <td class="table-col-quantity fs"><c:out value="${item.getCatalogItem().getQuantity()}"/></td>
                    <td class="table-col-qty fs"><c:out value="${item.getQuantity()}"/></td>
                    <td class="table-col-process">
                        <div class="center">
                            <form class="inline" name="ProcessOrder" action="LibraryServlet" method="POST">
                                <input type="hidden" name="command" value="process_order"/>
                                <input type="hidden" name="idOrder" value="${item.getId()}">
                                <input type="hidden" name="page" value=${currentPage}>
                                <button class="btn" type="submit" name="action" value="approve_order"><fmt:message
                                        key="catalog.approve"></fmt:message></button>
                                <button class="btn" type="submit" name="action" value="denied_order"><fmt:message
                                        key="catalog.deny"></fmt:message></button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
    </c:if>
        <%--Pagination--%>
    <tag:pagination currentPage="${currentPage}" command="   go_to_new_orders_page"
                    numberOfPages="${noOfPages}"></tag:pagination>
        <%--Messages--%>
    <div class="text-message"><h2>${emptySearchNewOrderMessage}</h2></div>
    <div class="text-message">
        <h2>${OrderProcessApprovedMessage}${OrderProcessDeniedMessage}${OrderProcessNoSuccessMessage}${OrderProcessNoEnoughMessage}</h2>
    </div>

    </body>
    <%@include file="../../footer.jsp" %>
</fmt:bundle>
</html>
