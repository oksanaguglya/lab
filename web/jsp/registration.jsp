<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <div class="enter">
        <div class="enter-list">
            <form name="submitForm1" method="POST" action="LibraryServlet">
                <input type="hidden" name="command" value="go_to_login_page">
                <A HREF="javascript:document.submitForm1.submit()"><fmt:message key="login.link"/></A>
            </form>
         </div>
    </div>
    <div>
        <div class="login-form">
            <h4><fmt:message key="register.link"/></h4>
            <form action="LibraryServlet" method="POST">
                <input type="hidden" name="command" value="login"/>
                <div><fmt:message key="login.login"/></div>
                <input type="login" name="login" placeholder=<fmt:message key="login.text.login"/>/><br/><br/>
                <div><fmt:message key="login.password"/></div>
                <input type="password" name="password" placeholder=<fmt:message key="login.text.password"/>/><br/><br/>
                <div><fmt:message key="login.password"/></div>
                <input type="password" name="repeatPassword" placeholder=<fmt:message key="register.repeat"/>/><br/><br/>
                <button type="submit" class="btn btn-info"><fmt:message key="login.submit"/></button>
            </form>
        </div>
    </div>
</body>
<%@include file="footer.jsp" %>
</fmt:bundle>
</html>
