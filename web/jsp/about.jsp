<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="resources.gui">
    <%@include file="header.jsp" %>
    <head></head>
    <body>
    <div class="about">
        <div class="enter"><h2><fmt:message key="about_us.text1"/></h2></div>
    </div>
    <div class="about">
        <div class="enter"><h2><fmt:message key="about_us.text2"/></h2></div>
    </div>
    </body>
    <%@include file="footer.jsp" %>
</fmt:bundle>
</html>
