<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/29/2025
  Time: 1:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <h2>${book != null ? "Update book" : "Add book"}</h2>
    <form action="${pageContext.request.contextPath}/book" method="post">
        <label>Id</label>
        <input type="text" name="id" value="${book.id}">
        <br>

        <label>Category name: </label>
        <select name="category">
            <c:forEach var="item" items="${categories}">
                <option value="${item.id}"
                        <c:if test="${item != null && item.id eq book.category.id}">
                            selected
                        </c:if>>
                ${item.name}
                </option>
            </c:forEach>
        </select>

    </form>

</div>

</body>
</html>
