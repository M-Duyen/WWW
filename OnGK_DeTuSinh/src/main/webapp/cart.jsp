<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/29/2025
  Time: 12:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <h2>Cart</h2>

    <c:if test="${empty cart.cartItems}">
        <p>Cart is empty</p>
    </c:if>

    <c:if test="${not empty cart.cartItems}">
        <table class="table">
            <tr>
                <th>Book ID</th>
                <th>Book Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>SubTotal</th>
                <th>Action</th>
            </tr>
            <c:forEach var="item" items="${cart.cartItems}">
                <tr>
                    <td>${item.book.id}</td>
                    <td>${item.book.name}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="number" name="quantity" value="${item.quantity}">

                            <input type="hidden" name="id" value="${item.book.id}">

                            <input type="hidden" name="action" value="update">
                            <button type="submit" class="btn btn-outline-warning">Update</button>
                        </form>
                    </td>
                    <td>
                            ${item.book.price}
                    </td>
                    <td>${item.subTotal}</td>
                </tr>
            </c:forEach>
        </table>
        <h6>Total: ${cart.total}</h6>
        <form action="${pageContext.request.contextPath}/cart" method="post">
            <input type="hidden" name="action" value="clear">
            <button type="submit" class="btn btn-outline-danger">Clear All</button>
        </form>
        <form action="${pageContext.request.contextPath}/cart" method="post">
            <input type="hidden" name="action" value="checkout">
            <button type="submit" class="btn btn-outline-primary">Checkout</button>
        </form>
    </c:if>

</div>

</body>
</html>
