<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/26/2025
  Time: 3:44 PM
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
                        <input type="hidden" name = "action" value="update">
                           <input type="hidden" name="id" value="${item.book.id}">
                           <input type="number" name="quantity" value="${item.quantity}" max="${item.book.quantity}"/>
                           <button type="submit">Update</button>
                       </form>
                   </td>
                   <td>${item.book.price}</td>
                   <td>${item.subTotal}</td>
                   <td>
                       <form action="${pageContext.request.contextPath}/cart" method="post">
                           <input type="hidden" name = "id" value="${item.book.id}">
                           <input type="hidden" name="action" value="delete">
                           <button type="submit">Delete</button>
                       </form>
                   </td>
               </tr>

            </c:forEach>

        </table>
        <h5>Total: ${cart.totalPrice}</h5>
        <form action="${pageContext.request.contextPath}/cart" method="post">
            <input type="hidden" name="action" value="clear">
            <button type="submit">Clear All</button>
        </form>
        <form action="${pageContext.request.contextPath}/cart" method="post">
            <input type="hidden" name="action" value="checkout">
            <button type="submit">CheckOut</button>
        </form>

    </c:if>
</div>

</body>
</html>
