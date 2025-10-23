<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/21/2025
  Time: 5:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Employee List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <h2>Employee List</h2>
    <a href="employees?action=form">Add Employee</a>
    <table class="table">
        <tr>
            <th>ID</th>
            <th>Name Employee</th>
            <th>Salary</th>
            <th>Dept</th>
            <th>Action</th>
        </tr>
        <c:forEach var="emp" items="${employees}">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.name}</td>
                <td>${emp.salary}</td>
                <td>${emp.department.id}</td>
                <td>
<%--                    <a href="employees?action=form&id=${emp.id}" class="btn btn-sm btn-warning">Edit</a>--%>
                    <form action="employees" method="get" class="d-inline">
                        <input type="hidden" name="action" value="form">
                        <input type="hidden" name="id" value="${emp.id}">
                        <button type="submit" class="btn btn-sm btn-warning">Edit</button>
                    </form>

                    <form action="employees" method="post" class="d-inline">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${emp.id}">
                        <button type="submit" class="btn btn-sm btn-danger"
                                onclick="return confirm('Are you sure you want to delete this employee?');">
                            Delete
                        </button>
                    </form>
                </td>
            </tr>

        </c:forEach>

    </table>

</div>

</body>
</html>
