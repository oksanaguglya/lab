<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <script src="http://code.jquery.com/jquery-latest.js">
    </script>
    <script>
        $(document).ready(function () {
            $("#sendBtn").click(function (e) {
                var items = $("input:checked").map(function () {
                    return this.value;
                }).get();
                document.getElementById('items').value = items;
                /*$.ajax({ url:"http://localhost:8080/AddBooksServlet",*/
                /* $.ajax({ url:"/AddBooksServlet",
                 type:"POST",
                 data: { selectedItems: items },
                 dataType:'json',
                 success:function(data) {
                 alert("success");
                 },
                 error:function(data) {
                 alert("error" + JSON.stringify(data));
                 }
                 })*/
                /*$.post('/LibraryServlet', {command : 'add_books', selectedItems: items }, function(data){ });*/
                /*$.post('/LibraryServlet', {command: 'add_books', selectedItems: items});*/
            });

            $('#table').on('click', '#radio', function() {
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
    <form class="search" name="search" action="../LibraryServlet" id="form" method="POST">
        <input type="hidden" name="command" value="go_to_catalog_page"/>
        <input class="search_text" type="text" name="search" value="${sessionScope.search}"
               placeholder=<fmt:message key="catalog.search.placeholder"/>/>
        <button type="submit" class="btn btn-info"><fmt:message key="catalog.search.button.text"/></button>
    </form>

    <table border="2" id="table" cellpadding="5" cellspacing="1">
        <tr>
            <c:choose>
                <c:when test="${sessionScope.user.getType() == 'READER'}">
                    <th class="table_col_check"></th>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
            <th><fmt:message key="catalog.title"></fmt:message></th>
            <th><fmt:message key="catalog.author"></fmt:message></th>
            <th class="table_col_year"><fmt:message key="catalog.year"></fmt:message></th>
            <th class="table_col_quantity"><fmt:message key="catalog.quantity"></fmt:message></th>
            <th class="table_col_bookType"><fmt:message key="catalog.bookType"></fmt:message></th>
        </tr>
        <c:forEach var="item" items="${requestScope.catalogItems}">
            <tr>
            <c:choose>
                <c:when test="${sessionScope.user.getType() == 'READER'}">
                    <td class="table_col_check"><input type="radio" name="selectedItem${item.getId()}"
                                                       value="${item.getId()}" id="radio"/></td>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
            <td><c:out value="${item.getBook().getTitle()}"/></td>
            <td><c:out value="${item.getBook().getAuthor()}"/></td>
            <td class="table_col_year"><c:out value="${item.getBook().getYear()}"/></td>
            <td class="table_col_quantity"><c:out value="${item.getQuantity()}"/></td>
            <c:choose>
                <c:when test="${item.getBook().getType() == 'LIBRARY_CARD'}">
                    <td class="table_col_bookType"><fmt:message key="catalog.library_card"></fmt:message></td>
                </c:when>
                <c:otherwise>
                    <td class="table_col_bookType"><fmt:message key="catalog.reading_room"></fmt:message></td>
                    </</c:otherwise>
            </c:choose>
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

    <c:choose>
        <c:when test="${sessionScope.user.getType() == 'READER'}">
            <form class="btn-order" name="orderBooks" action="LibraryServlet" method="POST">
                <input type="hidden" name="command" value="order_books"/>
                <input type="hidden" id="items" name="selectedItems" value=""/>
                <button type="submit" id="sendBtn" class="btn btn-order" name="orderBook"><fmt:message
                        key="catalog.orderBooks"/></button>
            </form>
            <div class="text-message"><h2>${successOrderMessage}</h2></div>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>

    </body>
    <%@include file="footer.jsp" %>
</fmt:bundle>
</html>
