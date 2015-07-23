<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <%-- <link href="css/style.css" rel="stylesheet" type="text/css"/>--%>
  </head>
  <body>
      <c:set var="locale" scope="session" value="ru_RU"/>
      <jsp:forward page="jsp/home.jsp"/>
  </body>
</html>