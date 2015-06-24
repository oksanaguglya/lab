<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function () {
            $("#sendBtn").click(function (e) {
                var items = $("input:checked").map(function () {
                    return this.value;
                }).get();
                document.getElementById('items').value = items;
                var qty = [];
                items.forEach(function (item, i, items) {
                    qty.push(document.getElementById('qty' + item).value);
                });
                document.getElementById('qty').value = qty;
            });

            $('#table').on('click', '#radio', function () {
                if (this.getAttribute('checked')) {
                    $(this).removeAttr('checked')
                } else {
                    $(this).attr('checked', true)
                }
            });
        });
    </script>
    <%@include file="header.jsp" %>
    <head>
        <link href="/css/1.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

    <form class="search" name="search" action="LibraryServlet" id="form" method="POST">
        <input type="hidden" name="command" value="go_to_catalog_page"/>
        <input class="search-text" type="text" name="search" value="${sessionScope.search}"
               placeholder=<fmt:message key="catalog.search.placeholder"/>/>
        <button type="submit" class="btn btn-info"><fmt:message key="catalog.search.button.text"/></button>
    </form>

    <c:if test="${noOfPages != 0}">
        <table border="2" id="table" cellpadding="5" cellspacing="1">
            <tr>
                <c:choose>
                    <c:when test="${sessionScope.user.getType() == 'READER'}">
                        <th class="table-col-check"></th>
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
                <th><fmt:message key="catalog.title"></fmt:message></th>
                <th><fmt:message key="catalog.author"></fmt:message></th>
                <th class="table-col-year"><fmt:message key="catalog.year"></fmt:message></th>
                <th class="table-col-bookType"><fmt:message key="catalog.bookType"></fmt:message></th>
                <th class="table-col-quantity"><fmt:message key="catalog.quantity"></fmt:message></th>
                <c:choose>
                    <c:when test="${sessionScope.user.getType() == 'READER'}">
                        <th class="table-col-qty"><fmt:message key="catalog.qty"></fmt:message></th>
                    </c:when>
                    <c:when test="${sessionScope.user.getType() == 'ADMINISTRATOR'}">
                        <th class="table-col-del_edit"></th>
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
            </tr>
            <c:forEach var="item" items="${requestScope.catalogItems}">
                <tr>
                    <c:choose>
                        <c:when test="${sessionScope.user.getType() == 'READER'}">
                            <td class="table-col-check"><input type="radio" name="selectedItem${item.getId()}"
                                                               value="${item.getId()}" id="radio"/></td>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                    <td><c:out value="${item.getBook().getTitle()}"/></td>
                    <td><c:out value="${item.getBook().getAuthor()}"/></td>
                    <td class="table-col-year"><c:out value="${item.getBook().getYear()}"/></td>
                    <c:choose>
                        <%--<c:when test="${item.getBook().getType() == 'LIBRARY_CARD'}">--%>
                        <c:when test="${item.getBook().getType() == 'LIBRARY_CARD'}">
                            <td class="table-col-bookType"><fmt:message key="catalog.library_card"></fmt:message></td>
                        </c:when>
                        <c:otherwise>
                            <td class="table-col-bookType"><fmt:message key="catalog.reading_room"></fmt:message></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="table-col-quantity"><c:out value="${item.getQuantity()}"/></td>
                    <c:choose>
                        <c:when test="${sessionScope.user.getType() == 'READER'}">
                            <td class="table-col-qty"><input class="text-qty" type="text" name="qty${item.getId()}"
                                                             value="1" id="qty${item.getId()}"/></td>
                        </c:when>
                        <c:when test="${sessionScope.user.getType() == 'ADMINISTRATOR'}">
                            <td class="table-col-del_edit">
                                <div class="center">
                                    <form class="inline" name="EditBookFromCatalog" action="LibraryServlet"
                                          method="POST">
                                        <input type="hidden" name="command" value="edit_book_from_catalog"/>
                                        <input type="hidden" name="id" value="${item.getId()}">
                                        <input type="hidden" name="page" value=${currentPage}>
                                        <button class="btn edit" type="submit" id="del${item.getId()}"
                                                name="delBook"></button>
                                    </form>
                                    <form class="inline" name="DelBookFromCatalog" action="LibraryServlet"
                                          method="POST">
                                        <input type="hidden" name="command" value="del_book_from_catalog"/>
                                        <input type="hidden" name="id" value="${item.getId()}">
                                        <input type="hidden" name="page" value=${currentPage}>
                                        <button class="btn del" type="submit" id="del${item.getId()}"
                                                name="delBook"></button>
                                    </form>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
        <br>

    <span class="btn-wrap">
        <div class="btn-order">
            <c:choose>
                <c:when test="${sessionScope.user.getType() == 'READER'}">
                    <form name="AddBooksToBasket" action="LibraryServlet" method="POST">
                        <input type="hidden" name="command" value="add_books_to_basket"/>
                        <input type="hidden" id="items" name="selectedItems" value=""/>
                        <input type="hidden" id="qty" name="selectedItemsQty" value=""/>
                        <input type="hidden" name="page" value=${currentPage}>
                        <button type="submit" id="sendBtn" class="btn" name="AddBooksToBasket"><fmt:message
                                key="catalog.addBooksToBasket"/></button>
                    </form>
                </c:when>
                <c:when test="${sessionScope.user.getType() == 'ADMINISTRATOR'}">
                    <form name="AddBookToCatalog" action="LibraryServlet" method="POST">
                        <input type="hidden" name="command" value="add_book_to_catalog"/>
                       <%-- <input type="hidden" name="page" value=${currentPage}>--%>
                        <button type="submit" id="sendBtn" class="btn" name="AddBookToCatalog"><fmt:message
                                key="catalog.addBookToCatalog"/></button>
                    </form>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
        </div>
    </span>
    </c:if>

    <span class="pagination-wrap">
        <div class="pagination-wrap2">
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
        </div>
    </span>

    <div class="text-message"><h2>${emptySearchResultMessage}</h2></div>
    <c:choose>
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
    </c:choose>

    </body>
    <%@include file="footer.jsp" %>
</fmt:bundle>
</html>
