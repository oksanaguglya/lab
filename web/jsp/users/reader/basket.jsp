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

    <span class="btn-wrap">
        <div class="btn-order">
            <c:choose>
                <c:when test="${sessionScope.user.getType() == 'READER'}">
                    <form name="AddBooksToBasket" action="LibraryServlet" method="POST">
                        <input type="hidden" name="command" value="make_an_order"/>
                        <input type="hidden" name="page" value=${currentPage}>
                        <button type="submit" id="sendBtn" class="btn" name="AddBooksToBasket">Оформить заказ</button>
                    </form>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
        </div>
    </span>
    </c:if>

    <span class="pagination-wrap">
        <div class="pagination">
            <ul>
                <c:if test="${currentPage != 1}">
                    <li>
                        <form name="goPreviousPageForm" method="POST" action="LibraryServlet">
                            <input type="hidden" name="command" value="my_basket">
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
                                    <input type="hidden" name="command" value="my_basket">
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
                            <input type="hidden" name="command" value="my_basket"/>
                            <input type="hidden" name="page" value=${currentPage + 1}>
                            <A HREF="javascript:document.goNextPageForm.submit()" class="pagination_next">></A>
                        </form>
                    </li>
                </c:if>
            </ul>
        </div>
    </span>

    <div class="text-message"><h2>${successDelBookFromBasket}${OrderMakeSuccessMessage}${OrderMakeNoSuccessMessage}</h2></div>
    <div class="text-message"><h2>${emptyBasketMessage}</h2></div>

    </body>
    <%@include file="../../footer.jsp" %>
</fmt:bundle>
</html>
