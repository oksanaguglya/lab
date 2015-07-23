<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jsp" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="resources.gui">
    <head></head>
    <body>
    <script src="js/validation.js"></script>
    <div class="enter">
        <div class="enter-list">
            <a href="/library/LibraryServlet?command=go_to_registration_page" class="pad-enter-list pad-left15"><fmt:message key="register.link"/></a>
        </div>
    </div>
    <div>
        <div class="login-form">
            <h4><fmt:message key="login.authorization"/></h4>

            <form name="log" action="LibraryServlet" onsubmit="return validateForm()" method="POST">
                <input type="hidden" name="command" value="login"/>

                <div><fmt:message key="login.login"/></div>
                <input type="text" name="login" placeholder=<fmt:message key="login.text.login"/>/><br/><br/>

                <div><fmt:message key="login.password"/></div>
                <input type="password" name="password" placeholder=<fmt:message key="login.text.password"/>/><br/><br/>
                <button type="submit" class="btn btn-info"><fmt:message key="login.submit"/></button>
            </form>
        </div>
        <div class="text-message"><h2>${resultLoginMessage}${resultRegMessage}</h2></div>
    </div>
    </body>
</fmt:bundle>
<%@include file="../footer.jsp" %>
</html>
