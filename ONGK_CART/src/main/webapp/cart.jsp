<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/27/2025
  Time: 11:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
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
            <th>Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>SubTotal</th>
            <th>Action</th>
        </tr>
        <c:forEach var="item" items="${cart.cartItems}">
            <tr>
                <td>${item.book.name}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="id" value="${item.book.id}"/>
                        <input type="number" name="quantity" value="${item.quantity}" max="${item.book.quantity}">
                        <input type="hidden" name="action" value="update"/>
                        <button type="submit" class="btn btn-outline-primary">Update</button>
                    </form>
                </td>
                <td>${item.book.price}</td>
                <td>${item.subTotal}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name = "id" value="${item.book.id}">
                        <input type="hidden" name="action" value = "remove">
                        <button type="submit" class="btn btn-outline-danger">Remove</button>

                    </form>
                </td>
            </tr>
        </c:forEach>
        </form>
    </table>
    <h6>Total: ${cart.total}</h6>

    <form action="${pageContext.request.contextPath}/cart" method="post">
        <input type="hidden" name="clear" value="clear">
        <button type="submit" class="btn btn-outline-danger">Clear All</button>
        </c:if>


</div>

</body>
</html>
