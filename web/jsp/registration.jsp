<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:bundle basename="by.bsu.guglya.library.resources.gui">
    <head></head>
    <body>
    <script src="js/validation.js"></script>
    <div class="enter">
        <div class="enter-list">
            <a href="/library/LibraryServlet?command=go_to_login_page" class="pad-enter-list pad-left15"><fmt:message key="login.link"/></a>
        </div>
    </div>
    <div>
        <div class="login-form">
            <h4><fmt:message key="register.link"/></h4>

            <form name="reg" action="LibraryServlet" onsubmit="return validateRegisterForm()" method="POST">
                <input type="hidden" name="command" value="registration"/>

                <div><fmt:message key="login.login"/></div>
                <input type="login" name="login" placeholder=<fmt:message key="login.text.login"/>/><br/><br/>

                <div><fmt:message key="login.password"/></div>
                <input type="password" name="password" placeholder=<fmt:message key="login.text.password"/>/><br/><br/>

                <div><fmt:message key="login.password"/></div>
                <input type="password" name="repeatPassword" placeholder=<fmt:message
                        key="register.repeat"/>/><br/><br/>
                <button type="submit" class="btn btn-info"><fmt:message key="registration.submit"/></button>
            </form>
        </div>
        <div class="text-message"><h2>${resultRegMessage}</h2>
        </div>
    </div>
    </body>
    <%@include file="footer.jsp" %>
</fmt:bundle>
</html>
