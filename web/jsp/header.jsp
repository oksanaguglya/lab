<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/css/1.css" rel="stylesheet" type="text/css"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <div>
        <span class="header-title-wrap">
            <div class="header-title-wrap2">
                <form name="home" action="LibraryServlet" method="POST">
                    <input type="hidden" name="command" value="home"/>
                    <button type="submit" class="btn-header-home" name=home><h2 class="header"><fmt:message
                            key="header.library"/></h2></button>
                </form>
            </div>
        </span>
        <c:choose>
            <c:when test="${sessionScope.user.getType() == 'READER'}">
                 <span class="header-title-wrap1">
                    <form name="basket" action="LibraryServlet" method="POST">
                        <input type="hidden" name="command" value="my_basket"/>
                        <button type="submit" name="basket" class="btn basket"></button>
                    </form>
                </span>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
    </div>
    </body>
</fmt:bundle>
</html>
