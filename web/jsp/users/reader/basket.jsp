<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="resources.gui">
    <%@include file="../../header.jsp" %>
    <head></head>
    <body>
        <%--Table orders--%>
    <c:if test="${noOfPages != 0}">
        <table border="2" id="table" cellpadding="5" cellspacing="1">
            <tr>
                <th><fmt:message key="catalog.title"></fmt:message></th>
                <th><fmt:message key="catalog.author"></fmt:message></th>
                <th class="table-col-year"><fmt:message key="catalog.year"></fmt:message></th>
                <th class="table-col-bookType"><fmt:message key="catalog.bookType"></fmt:message></th>
                <th class="table-col-qty"><fmt:message key="catalog.qty"></fmt:message></th>
                <th class="table-col-del"></th>
            </tr>
            <c:forEach var="item" items="${requestScope.basketItems}">
                <tr>
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
                    <td class="table-col-del">
                        <form name="DelBookFromBasket" action="LibraryServlet" method="POST">
                            <input type="hidden" name="command" value="del_book_from_basket"/>
                            <input type="hidden" name="idOrderDel" value="${item.getId()}">
                            <input type="hidden" name="page" value=${currentPage}>
                            <button class="btn del" type="submit" id="del${item.getId()}" name="delBook"></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <%--Buttons--%>
    <span class="btn-wrap">
        <div class="btn-order">
            <c:if test="${sessionScope.user.getType() == 'READER'}">
                <form name="AddBooksToBasket" action="LibraryServlet" method="POST">
                    <input type="hidden" name="command" value="make_an_order"/>
                    <input type="hidden" name="page" value=${currentPage}>
                    <button type="submit" id="sendBtn" class="btn" name="AddBooksToBasket">Оформить заказ</button>
                </form>
            </c:if>
        </div>
    </span>
    </c:if>
        <%--Pagination--%>
    <span class="pagination-wrap">
        <div class="pagination-wrap2">
            <tag:pagination currentPage="${currentPage}" command="my_basket"
                            numberOfPages="${noOfPages}"></tag:pagination>
        </div>
    </span>
        <%--Messages--%>
    <div class="text-message"><h2>${successDelBookFromBasket}${OrderMakeSuccessMessage}${OrderMakeNoSuccessMessage}</h2>
    </div>
    <div class="text-message"><h2>${emptyBasketMessage}</h2></div>

    </body>
    <%@include file="../../footer.jsp" %>
</fmt:bundle>
</html>
