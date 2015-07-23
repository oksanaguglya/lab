<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="resources.gui">
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/library/css/style.css" rel="stylesheet" type="text/css"/>
        <title><fmt:message key="errorpage.title"/></title>
    </head>
    <body>
    <div class="text-error">
            <%--general info--%>
        <div class="text-message"><h2><fmt:message key="errorpage.resource"/></h2></div>

        <div class="center">
            <button class="btn"><a class="a-no-dec" style="color: white"
                                   href="/library/LibraryServlet?command=home"><fmt:message
                    key="errorpage.redirect"/></a></button>
        </div>
    </div>
    </body>
</fmt:bundle>
</html>