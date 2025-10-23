<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/28/2025
  Time: 9:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Category List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <h2>Category List</h2>
    <c:if test="${not empty sessionScope.message}">
        <script>
            alert("${sessionScope.message}")
        </script>
        <c:remove var="message" scope="session"/>
    </c:if>


    <a href="category-form.jsp">Add Category</a>

    <form action="${pageContext.request.contextPath}/category" method="get" class="form-control" >
        <input name="action" type="hidden" value="search">
        <label>Search</label>
        <input type="text" name="name" placeholder="Search here...">
        <button type="submit" class="btn btn-outline-success">Search</button>

    </form>
    <table class="table">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Action</th>
        </tr>
        <c:forEach var="item" items="${categories}">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>
                    <a href="category?action=detail&id=${item.id}">Edit</a>
                    <form action="${pageContext.request.contextPath}/category" method="post" class="d-inline">
                        <input type="hidden" name="id" value="${item.id}">
                        <input type="hidden" name="action" value="delete">
                        <button type="submit" class="btn btn-outline-danger">Delete</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/category" method="get" class="d-inline">
                        <input type="hidden" name="id" value="${item.id}">
                        <input type="hidden" name="action" value="list">
                        <button type="submit" class="btn btn-outline-info">List</button>
                    </form>
                </td>
            </tr>

        </c:forEach>

    </table>



</div>

</body>
</html>
