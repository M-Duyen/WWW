<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/28/2025
  Time: 2:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>

</head>
<body>
<div class="container-fluid">
    <form action="${pageContext.request.contextPath}/bank" method="post">
        <label>Owner name:</label>
        <input type="text" name="ownerName">
        <br>
        <label>Card number:</label>
        <input type="text" name="cardNumber">
        <br>
        <label>Owner address:</label>
        <input type="text" name="ownerAddress">
        <br>
        <label>Amount:</label>
        <input type="number" name="amount">
        <br>

        <input type="hidden" name="action" value="add">
        <button type="submit" class="btn btn-outline-info">Add</button>
    </form>

</div>

</body>
</html>
