<%@ page import="by.bsu.guglya.library.managers.ConfigurationManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglib" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/library/css/style.css" rel="stylesheet" type="text/css"/>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
    <div class="header-wrap">
        <div class="header-background">
        <span class="header-title-wrap0">
                <tag:langmenu forwardString="${pageContext.request.servletPath}"/>
        </span>
        <span class="header-title-wrap">
            <div class="header-title-wrap2">
                <button class="btn-header-home"><a class="a-no-dec" href="/library/LibraryServlet?command=home"><h2 class="header"><fmt:message
                        key="header.library"/></h2></a></button>
            </div>
        </span>

            <c:if test="${sessionScope.user.getType() == 'READER'}">
                <c:if test="${pageContext.request.servletPath != '/jsp/users/reader/basket.jsp'}">
                 <span class="header-title-wrap1">
                    <form name="basket" action="LibraryServlet" method="POST">
                        <input type="hidden" name="command" value="my_basket"/>
                        <button type="submit" name="basket" class="btn basket"></button>
                    </form>
                </span>
                </c:if>
            </c:if>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>
