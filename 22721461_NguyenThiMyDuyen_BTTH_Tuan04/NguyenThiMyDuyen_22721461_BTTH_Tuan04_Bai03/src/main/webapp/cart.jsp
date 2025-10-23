<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/14/2025
  Time: 5:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <style>
        .td {
            margin: 10px;
        }
    </style>
</head>
<body>
<body>
<div class="container">
    <h2>Cart</h2>

    <%--@elvariable id="cart" type="com.sun.tools.javac.jvm.Gen"--%>
    <c:if test="${empty cart.items}">
        <p>Cart is empty!</p>
    </c:if>

    <c:if test="${not empty cart.items}">
        <table class="table table-border">
            <tr >
                <th>Model</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>SubTotal</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>${item.product.model}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="update"/>
                            <input type="hidden" name="id" value="${item.product.id}"/>
                            <label>
                                <input type="number" name="quantity" value="${item.quantity}" min="1"/>
                            </label>
                            <button type="submit" value="Update">Update</button>
                        </form>
                    </td>
                    <td class="td">${item.product.price}</td>
                    <td class="td">${item.product.price * item.quantity}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="remove"/>
                            <input type="hidden" name="productId" value="${item.product.id}"/>
                            <input type="submit" value="Remove"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <p><strong>Total: </strong> ${cart.total}</p>

        <form action="${pageContext.request.contextPath}/cart" method="post">
            <input type="hidden" name="action" value="clear"/>
            <button type="submit">Clear All</button>
        </form>
    </c:if>

    <a href="product">Continue Shopping</a>
</div>
</body>


</body>
</html>
