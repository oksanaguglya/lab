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
            <h2 class="header"><fmt:message key="header.library"/></h2>
            </div>
        </span>
        <form action="LibraryServlet" method="POST" class="header-button">
            <input type="hidden" name="command" value="home"/>
            <button type="submit" class="btn btn-home" name=home><fmt:message key="header.home"/></button>
        </form>
    </div>
    </body>
</fmt:bundle>
</html>
