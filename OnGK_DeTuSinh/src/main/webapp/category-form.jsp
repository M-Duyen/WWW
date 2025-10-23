<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/28/2025
  Time: 9:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Category form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container-fluid">
    <h2${category != null ? "Update category" : "Add category"}></h2>

    <form action="${pageContext.request.contextPath}/category" method="post" class="form-control" onsubmit="return validateForm()">
        <c:if test="${category != null}">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${category.id}">
        </c:if>

        <c:if test="${category == null}">
            <input type="hidden" name="action" value="add">
        </c:if>
        <div class="mb-3">
            <label>ID: </label>
            <c:if test="${category != null}">
                <input type="text" id="id" name="id" value="${category.id}" readonly>
            </c:if>
            <c:if test="${category == null}">
                <input type="text" id="id" name="id" value="${category.id}">
            </c:if>
        </div>
        <div class="mb-3">
            <label>Name: </label>
            <input type="text" id="name" name="name" value="${category.name}">
        </div>

        <div>
            <button type="submit" class="btn btn-outline-info">${category != null ? "Update" : "Add"}</button>
        </div>
    </form>

    <script>
        function validateForm(){
            const id = document.getElementById('id').value;
            const name = document.getElementById('name').value;
            const regex = /^C\d{5}$/;

            if(!regex.test(id)){
                alert('Invalid id');
                return false;
            }else if(name.length > 255){
                alert('Name less than 255');
                return false;
            }
            return true;
        }
    </script>


</div>

</body>
</html>
