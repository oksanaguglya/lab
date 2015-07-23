<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="resources.gui">
    <head></head>
    <body>
    <div class="footer">
        <h4><a href="mailto:<fmt:message key="footer.mail"/>"><fmt:message key="footer"/><fmt:message
                key="footer.mail"/></a></h4>
    </div>
    </body>
</fmt:bundle>
</html>
