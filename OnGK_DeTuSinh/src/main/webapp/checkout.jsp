<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/29/2025
  Time: 1:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Checkout</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <table class="table">
        <tr>
            <th>Book ID</th>
        </tr>
        <c:forEach var="item" items="${listID}">
            <tr>
                <td>${item}</td>
            </tr>
        </c:forEach>
    </table>
    <h6>Total: ${total}</h6>
</div>

</body>
</html>
