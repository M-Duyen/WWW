<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/14/2025
  Time: 3:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            align-items: center;
            width: auto;
        }

        .hinh {
            width: 150px;
            height: 150px;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<br>
<a href="cart">View cart</a>

<h1 style="display: flex; justify-content: center">Product List</h1>

<c:if test="${empty products}">
    <p>No products available</p>
</c:if>
<div class="container">
    <c:forEach items="${products}" var="p">

        <div class="product-class"
             style="border: 1px solid black; padding: 10px; margin: 10px; display: inline-block; text-align: center; border-radius: 5px">
            <b> ${p.model}</b>
            <br><br>
            <img src="images/${p.image}" class="hinh" alt=${p.model}>
            <br><br>
            Price: ${p.price}
            <br>
            <form action="${pageContext.request.contextPath}/cart" method="post">
                <label>
                    <input type="number" size="1" value="1" name="quantity" style="width: 40px; text-align: center">
                </label>
                <br>
                <input type="hidden" name="id" value="${p.id}">
                <input type="hidden" name="price" value="${p.price}">
                <input type="hidden" name="model" value="${p.model}">
                <input type="hidden" name="action" value="add"><br/>
                <button type="submit" name="addToCart">Add To Cart</button>
                <br>
            </form>
            <a href="${pageContext.request.contextPath}/product?id=${p.id}">Product Detail</a> <br>
        </div>


    </c:forEach>
</div>
</body>
</html>
