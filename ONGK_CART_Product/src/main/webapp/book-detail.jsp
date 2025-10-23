<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/26/2025
  Time: 4:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Detail</title>
</head>
<body>
<div class="container-fluid">
    <c:if test="${not empty book}">
        <div>
            <label>${book.name}</label>
        </div>
        <div>
            <img src="images/${book.image}" style="width: 300px"; height="350px">
        </div>
       <div>
           <label style="color: red">${book.price}$</label>
       </div>
        <div>
            <label>${book.quantity}</label>
        </div>

    </c:if>

</div>

</body>
</html>
