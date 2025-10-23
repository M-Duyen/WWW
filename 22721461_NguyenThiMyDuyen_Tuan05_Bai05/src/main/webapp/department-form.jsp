<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/18/2025
  Time: 11:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Department Information</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <h2>${department != null ? "Edit Department" : "Add Department"}</h2>
    <form action="departments" method="post">
        <c:if test="${department != null}">
            <input type="hidden" name = "action" value="update">
            <input type="hidden" name="id" value="${department.id}">

        </c:if>

        <c:if test="${department == null}">
            <input type="hidden" name="action" value="add">
        </c:if>

        <label>
            Department name:
        </label>
        <label>
            <input type="text" name="name" value="${department != null ? department.name : ""}">
        </label>
        <button type="submit">Save</button>
    </form>
</div>

</body>
</html>
