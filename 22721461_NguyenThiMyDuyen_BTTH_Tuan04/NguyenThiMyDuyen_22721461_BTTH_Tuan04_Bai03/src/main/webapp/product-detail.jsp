<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/14/2025
  Time: 6:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Detail</title>
</head>
<body>
<h2>Product Detail</h2>
<c:if test="${not empty product}">
    <b>Model:</b> ${product.model} <br><br>
    <img src="images/${product.image}" alt=${product.model} style="width: 300px; height: 300px; border-radius: 5px"> <br><br>
    <b>Price:</b> ${product.price} <br><br>
    <b>Description:</b> ${product.description} <br><br>

    <form action="${pageContext.request.contextPath}/cart" method="post">
        <label>
            Quantity:
            <input type="number" size="1" value="1" name="quantity" style="width: 40px; text-align: center">
        </label>
        <br><br>
        <input type="hidden" name="id" value="${product.id}">
        <input type="hidden" name="price" value="${product.price}">
        <input type="hidden" name="model" value="${product.model}">
        <input type="hidden" name="action" value="add"><br/>
        <button type="submit" name="addToCart">Add To Cart</button>
    </form>
    <a href="cart">View cart</a>
    <br>
    <a href="product">Back</a>

</c:if>


</body>
</html>
