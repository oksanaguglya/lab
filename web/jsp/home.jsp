<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <%@include file="header.jsp" %>
    <head>
        <link href="/css/1.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
    <div class="enter">
        <div class="enter-list">
            <c:choose>
                <c:when test="${sessionScope.user.getType() == 'READER' || sessionScope.user.getType() == 'ADMINISTRATOR'}">
                    <form class="pad-enter-list" name="logOut" method="POST" action="LibraryServlet">
                        <input type="hidden" name="command" value="log_out">
                        <A HREF="javascript:document.logOut.submit()"><fmt:message key="login.out"/></A>
                    </form>
                </c:when>
                <c:otherwise>
                    <form class="pad-enter-list" name="login" method="POST" action="LibraryServlet">
                        <input type="hidden" name="command" value="go_to_login_page">
                        <A HREF="javascript:document.login.submit()"><fmt:message key="login.link"/></A>
                    </form>
                    <form class="pad-enter-list" name="registration" method="POST" action="LibraryServlet">
                        <input type="hidden" name="command" value="go_to_registration_page">
                        <A HREF="javascript:document.registration.submit()"><fmt:message key="register.link"/></A>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
        <ul class="list">
            <li>
                <%--<form class="pad-enter-list" name="catalog" method="POST" action="LibraryServlet">
                    <input type="hidden" name="command" value="go_to_catalog_page">
                    <A HREF="javascript:document.catalog.submit()"><fmt:message key="home.catalog"/></A>
                </form>--%>
                <a href="/LibraryServlet?command=go_to_catalog_page" class="pad-enter-list"><fmt:message key="home.catalog"/></a>
            </li>

            <c:if test="${sessionScope.user.getType() == 'READER'}">
                <li>
                    <form class="pad-enter-list" name="orderReader" method="POST" action="LibraryServlet">
                        <input type="hidden" name="command" value="go_to_order_reader_page">
                        <A HREF="javascript:document.orderReader.submit()"><fmt:message key="home.order_reader"/></A>
                    </form>
                </li>
            </c:if>

            <c:if test="${sessionScope.user.getType() == 'ADMINISTRATOR'}">
                <li>
                    <form class="pad-enter-list" name="orderNewAdmin" method="POST" action="LibraryServlet">
                        <input type="hidden" name="command" value="go_to_new_order_admin_page">
                        <A HREF="javascript:document.orderNewAdmin.submit()"><fmt:message key="home.new_order_admin"/></A>
                    </form>
                </li>
                <li>
                    <form class="pad-enter-list" name="orderLoginAdmin" method="POST" action="LibraryServlet">
                        <input type="hidden" name="command" value="go_to_login_order_admin_page">
                        <A HREF="javascript:document.orderLoginAdmin.submit()"><fmt:message key="home.login_order_admin"/></A>
                    </form>
                </li>
            </c:if>

            <li>
                <%--<form class="pad-enter-list" name="aboutUs" method="POST" action="LibraryServlet">
                    <input type="hidden" name="command" value="go_to_about_us_page">
                    <A HREF="javascript:document.aboutUs.submit()"><fmt:message key="about_us.link"/></A>
                </form>--%>
                <a href="/LibraryServlet?command=go_to_about_us_page" class="pad-enter-list"><fmt:message key="about_us.link"/></a>
            </li>
        </ul>


        <%--<c:if test="${sessionScope.user.getType() != 'READER' && sessionScope.user.getType() != 'ADMINISTRATOR'}">
            <form name="changeLang" action="LibraryServlet" method="POST" id="change_language" class="lang-button">
                <h2><fmt:message key="home.lang"/></h2>
                <input type="hidden" name="command" value="change_language"/>
                <button type="submit" class="btn btn-lang" name=locale value="RU"><fmt:message
                        key="header.ru"/></button>
                <button type="submit" class="btn btn-lang" name=locale value="EN"><fmt:message
                        key="header.en"/></button>
            </form>
        </c:if>--%>

        <c:if test="${sessionScope.user.getType() == 'READER'}">
            <h2>${sessionScope.user.getLogin()}<fmt:message key="home.hello"/></h2>
        </c:if>

    </div>
    </body>
    <%@include file="footer.jsp" %>
</fmt:bundle>
</html>
