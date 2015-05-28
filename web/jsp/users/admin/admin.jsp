<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
<%@include file="../../header.jsp" %>
    <head>
        <link href="/css/1.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
    <div class="enter">
        <div class="enter-list">
            <form name="submitForm1" method="POST" action="LibraryServlet">
                <input type="hidden" name="command" value="log_out">
                <A HREF="javascript:document.submitForm1.submit()"><fmt:message key="login.out"/></A>
            </form>
        </div>
        <ul class="list">
            <li><a href=""><fmt:message key="main.catalog"/></a></li>
            <li><a href=""><fmt:message key="about_us.link"/></a></li>
        </ul>
    </div>
    </body>
    <%@include file="../../footer.jsp" %>
</fmt:bundle>
</html>
