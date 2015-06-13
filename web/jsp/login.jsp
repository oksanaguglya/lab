<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/css/1.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
    <script src="js/validation.js"></script>
    <div class="enter">
        <div class="enter-list">
            <form name="registration" method="POST" action="LibraryServlet">
                <input type="hidden" name="command" value="go_to_registration_page">
                <A HREF="javascript:document.registration.submit()"><fmt:message key="register.link"/></A>
            </form>
        </div>
    </div>
    <div>
        <div class="login-form">
            <h4><fmt:message key="login.authorization"/></h4>

            <form name="login" action="../LibraryServlet" onsubmit="return validateForm()" method="POST">
                <input type="hidden" name="command" value="login"/>

                <div><fmt:message key="login.login"/></div>
                <input type="text" name="login" placeholder=<fmt:message key="login.text.login"/>/><br/><br/>

                <div><fmt:message key="login.password"/></div>
                <input type="password" name="password" placeholder=<fmt:message key="login.text.password"/>/><br/><br/>
                <button type="submit" class="btn btn-info"><fmt:message key="login.submit"/></button>
            </form>
        </div>
        <div class="text-message"><h2>${noUserMessage}${successRegMessage}</h2></div>
    </div>
    </body>
</fmt:bundle>
<%@include file="footer.jsp" %>
</html>
