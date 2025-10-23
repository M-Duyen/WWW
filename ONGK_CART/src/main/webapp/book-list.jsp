<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/27/2025
  Time: 10:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container-fluid">
    <h2>Book List</h2>
    <a href="${pageContext.request.contextPath}/cart">View cart</a>

    <div style="display: flex; justify-content: center; align-items: center">
        <c:forEach var="item" items="${books}">
            <div class="card text-center m-2 p-2">
                <label>${item.name}</label>
                <img src="images/${item.image}" style="width: 220px; height: 250px">
                <span class="text-danger"><b>${item.price}$</b></span>


                <form action="${pageContext.request.contextPath}/cart" method="post">
                    <input type="number" name="quantity" min=1 max="${item.quantity}" value="1" class="text-center">
                    <br>
                    <input type="hidden" name="id" value="${item.id}">

                    <input type="hidden" name="action" value="add">



                    <button type="submit" class="btn btn-outline-info mt-2">Add to cart</button>
                </form>
                <a href="book?action=list&id=${item.id}" class="btn btn-link">Detail</a>
            </div>
        </c:forEach>
    </div>

</div>

</body>
</html>
