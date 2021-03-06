<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="resources.gui">
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
    <head></head>
    <body>
        <%-- Search form --%>
    <form class="search" name="search" action="LibraryServlet" id="form" method="POST">
        <input type="hidden" name="command" value="go_to_catalog_page"/>
        <input class="search-text" type="text" name="search" value="${sessionScope.search}"
               placeholder=<fmt:message key="catalog.search.placeholder"/>/>
        <button type="submit" class="btn btn-info"><fmt:message key="catalog.search.button.text"/></button>
    </form>
        <%--Catalog table--%>
    <c:if test="${noOfPages != 0}">
        <table border="2" id="table" cellpadding="5" cellspacing="1">
            <tr>
                <c:if test="${sessionScope.user.getType() == 'READER'}">
                    <th class="table-col-check"></th>
                </c:if>
                <th><fmt:message key="catalog.title"></fmt:message></th>
                <th><fmt:message key="catalog.author"></fmt:message></th>
                <th class="table-col-year"><fmt:message key="catalog.year"></fmt:message></th>
                <th class="table-col-bookType"><fmt:message key="catalog.bookType"></fmt:message></th>
                <th class="table-col-quantity"><fmt:message key="catalog.quantity"></fmt:message></th>
                <c:if test="${sessionScope.user.getType() == 'READER'}">
                    <th class="table-col-qty"><fmt:message key="catalog.qty"></fmt:message></th>
                </c:if>
                <c:if test="${sessionScope.user.getType() == 'ADMINISTRATOR'}">
                    <th class="table-col-del_edit"></th>
                </c:if>
            </tr>
            <c:forEach var="item" items="${requestScope.catalogItems}">
                <tr>
                    <c:if test="${sessionScope.user.getType() == 'READER'}">
                        <td class="table-col-check fs"><input type="radio" name="selectedItem${item.getId()}"
                                                              value="${item.getId()}" id="radio"/></td>
                    </c:if>
                    <td class="fs"><c:out value="${item.getBook().getTitle()}"/></td>
                    <td class="fs"><c:out value="${item.getBook().getAuthor()}"/></td>
                    <td class="table-col-year fs"><c:out value="${item.getBook().getYear()}"/></td>
                    <c:choose>
                        <c:when test="${item.getBook().getType() == 'READING_ROOM'}">
                            <td class="table-col-bookType fs"><fmt:message
                                    key="catalog.reading_room"></fmt:message></td>
                        </c:when>
                        <c:otherwise>
                            <td class="table-col-bookType fs"><fmt:message
                                    key="catalog.library_card"></fmt:message></td>
                        </c:otherwise>
                    </c:choose>
                    <td class="table-col-quantity fs"><c:out value="${item.getQuantity()}"/></td>
                    <c:if test="${sessionScope.user.getType() == 'READER'}">
                        <td class="table-col-qty fs"><input class="text-qty" type="text" name="qty" value="1"
                                                            id="qty${item.getId()}"/></td>
                    </c:if>
                    <c:if test="${sessionScope.user.getType() == 'ADMINISTRATOR'}">
                        <td class="table-col-del_edit fs">
                            <div class="center">
                                <form class="inline" name="EditBookFromCatalog" action="LibraryServlet" method="POST">
                                    <input type="hidden" name="command" value="go_to_edit_book_from_catalog_page"/>
                                    <input type="hidden" name="idCatalogEdit" value="${item.getId()}">
                                    <button class="btn edit" type="submit" name="editBook"></button>
                                </form>
                                <form class="inline" name="DelBookFromCatalog" action="LibraryServlet" method="POST">
                                    <input type="hidden" name="command" value="del_book_from_catalog"/>
                                    <input type="hidden" name="idCatalogDel" value="${item.getId()}">
                                    <input type="hidden" name="page" value=${currentPage}>
                                    <button class="btn del" type="submit" name="delBook"></button>
                                </form>
                            </div>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
        <br>
    </c:if>
        <%--Buttons--%>
    <span class="btn-wrap">
        <div class="btn-order">
            <c:if test="${sessionScope.user.getType() == 'READER'}">
                <form name="AddBooksToBasket" action="LibraryServlet" method="POST">
                    <input type="hidden" name="command" value="add_books_to_basket"/>
                    <input type="hidden" id="items" name="selectedItems" value=""/>
                    <input type="hidden" id="qty" name="selectedItemsQty" value=""/>
                    <input type="hidden" name="page" value=${currentPage}>
                    <button type="submit" id="sendBtn" class="btn" name="AddBooksToBasket"><fmt:message
                            key="catalog.addBooksToBasket"/></button>
                </form>
            </c:if>
            <c:if test="${sessionScope.user.getType() == 'ADMINISTRATOR'}">
                <form name="AddBookToCatalog" action="LibraryServlet" method="POST">
                    <input type="hidden" name="command" value="go_to_add_book_to_catalog_page"/>
                    <button type="submit" id="sendBtn" class="btn" name="AddBookToCatalog"><fmt:message
                            key="catalog.addBookToCatalog"/></button>
                </form>
            </c:if>
        </div>
    </span>
        <%--Pagination--%>
     <span class="pagination-wrap">
         <div class="pagination-wrap2">
             <tag:pagination currentPage="${currentPage}" command="go_to_catalog_page"
                             numberOfPages="${noOfPages}"></tag:pagination>
         </div></span>
        <%--Messages--%>
    <div class="text-message"><h2>${emptySearchResultMessage}</h2></div>
    <c:if test="${sessionScope.user.getType() == 'READER'}">
        <div class="center">
            <div class="text-message inline"><h2>${orderNoChecksMessage}${successOrderMessage}</h2></div>
            <c:if test="${numOfOrdersMessage > 0}">
                <div class="text-message inline"><h2>(${numOfSuccessOrdersMessage}/${numOfOrdersMessage})</h2></div>
            </c:if>
        </div>
    </c:if>
    <c:if test="${sessionScope.user.getType() == 'ADMINISTRATOR'}">
        <div class="center">
            <div class="text-message"><h2>${successDelBookFromCatalog}${unsuccessfulDelBookFromCatalog}${resultMessage}</h2></div>
        </div>
    </c:if>

    </body>
    <%@include file="footer.jsp" %>
</fmt:bundle>
</html>
