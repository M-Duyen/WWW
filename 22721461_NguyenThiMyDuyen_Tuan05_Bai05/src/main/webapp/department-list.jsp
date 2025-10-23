<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/18/2025
  Time: 10:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Department List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <h2 class="table-header">Department List</h2>
    <div class="mb-3">
        <form action="departments" method="get" class="d-flex">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" class="form-control" placeholder="Search departments...">
            <button type="submit" class="btn btn-primary ms-2">Search</button>
        </form>
    </div>

    <c:if test="${not empty param.error}">
        <script>
            alert("${param.error}")
        </script>
    </c:if>

    <button class="btn btn-info"><a href="department-form.jsp" style="text-decoration: none; color: white">Add Department</a></button>
    <table class="table">
        <tr>
            <th>Department ID</th>
            <th>Department Name</th>
            <th>Action</th>

        </tr>
        <c:forEach var = "dept" items= "${departments}">
            <tr>
                <td>${dept.id}</td>
                <td>${dept.name}</td>
                <td>
                    <form action="departments" method="get" class="d-inline">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="id" value="${dept.id}">
                        <button type="submit" class="btn btn-sm btn-warning">Edit</button>
                    </form>
                    <form action="departments" method="post" class="d-inline">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${dept.id}">
                        <button type="submit" class="btn btn-sm btn-danger"
                                onclick="return confirm('Are you sure you want to delete this department?');">
                            Delete
                        </button>
                    </form>
                    <form action="departments" method="post" class="d-inline">
                        <input type="hidden" name="action" value="list">
                        <input type="hidden" name="id" value="${dept.id}">
                        <button type="submit" class="btn btn-sm btn-primary">View Employees</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
