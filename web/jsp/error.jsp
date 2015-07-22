<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/1.css" rel="stylesheet" type="text/css"/>
        <title><fmt:message key="errorpage.title"/></title>
    </head>
    <body>
    <div class="text-error">
            <%--general info--%>
        <div class="text-message"><h2><fmt:message key="errorpage.message"/></h2></div>
            <%--database error--%>
        <c:if test="${errorDatabaseMessage != null}">
            <div class="text-message"><h2>Database error!</h2></div>
            <div class="text-message"><h2>(${errorDatabaseMessage})</h2></div>
        </c:if>

        <div class="center">
            <form name="home" action="LibraryServlet" method="POST">
                <input type="hidden" name="command" value="home"/>
                <button type="submit" class="btn" name=home>Redirect to main page</button>
            </form>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>