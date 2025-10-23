<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/21/2025
  Time: 6:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee Form</title>
</head>
<body>
<div class="container-fluid">
    <h2>${employee != null ? "Edit Employee" : "Add Employee"}</h2>
    <form action="employees" method="post">
        <c:if test="${employee != null}">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${employee.id}">

        </c:if>

        <c:if test="${employee == null}">
            <input type="hidden" name="action" value="add">
        </c:if>

        <label>
            Employee name:
        </label>
        <label>
            <input type="text" name="name" value="${employee != null ? employee.name : ""}">
        </label>
        <br>
        <label>
            Salary:
        </label>
        <label>
            <input type="number" name="salary" value="${employee != null ? employee.salary : ""}">
        </label>
        <br>
        <label>
            Department:
        </label>
        <select name="departmentId" style="width: 100px">
            <c:forEach var="dept" items="${departments}">
                <option value="${dept.id}"
                        <c:if test="${employee != null && employee.department.id == dept.id}">
                            selected
                        </c:if>
                >${dept.name}
                </option>
            </c:forEach>
        </select>
        <br>
        <button type="submit">Save</button>
    </form>
</div>
</body>
</html>
