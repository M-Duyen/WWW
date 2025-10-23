<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/15/2025
  Time: 4:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .header{
            background: antiquewhite;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
            margin-bottom: 10px;
        }
        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }
        li{
            float: left;
            margin-right: 20px;
        }
    </style>
</head>
<body>
<nav >
    <div class="header">
        <div>
            <h2>IUH BOOKSTORE</h2>
        </div>
        <div>
            <ul>
                <li class="nav-item"><a class="nav-link" href="#">HOME</a></li>
                <li class="nav-item"><a class="nav-link" href="#">EXAMPLES</a></li>
                <li class="nav-item"><a class="nav-link" href="#">SERVICES</a></li>
                <li class="nav-item"><a class="nav-link" href="#">PRODUCTS</a></li>
                <li class="nav-item"><a class="nav-link" href="#">CONTACT</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="row container-fluid">
    <div class="col-3">
        <div style="background: aliceblue; border-radius: 5px; padding: 5px">
            <h4 style="color: darkgray">ABOUT US</h4>
            <p>About us information will be here ... </p><a href="#">Read More</a>
        </div>

        <div class="mt-3">
            <h4 style="color: darkgray">SEARCH SITE</h4>
            <input type="text" placeholder="Search.." style="border-radius: 5px; padding: 5px">
        </div>
    </div>
    <div class="col-9">
        <c:if test="${empty cart.cartItemBeans}">
            <p>Cart is empty!</p>
        </c:if>

        <c:if test="${not empty cart.cartItemBeans}">
            <table class="table table-border">
                <tr >
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>SubTotal</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="cartItemBean" items="${cart.cartItemBeans}">
                    <tr>
                        <td>${cartItemBean.book.name}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="update"/>
                                <input type="hidden" name="id" value="${cartItemBean.book.id}"/>
                                <label>
                                    <input type="number" name="quantity" value="${cartItemBean.quantity}" min="1"/>
                                </label>
                                <button type="submit" value="Update">Update</button>
                            </form>
                        </td>
                        <td class="td">${cartItemBean.book.price}</td>
                        <td class="td">${cartItemBean.book.price * cartItemBean.quantity}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="remove"/>
                                <input type="hidden" name="id" value="${cartItemBean.book.id}"/>
                                <input type="submit" value="Remove"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <p><strong>Total: </strong> ${cart.total}</p>

            <form action="${pageContext.request.contextPath}/checkout" method="post">
                <button type="submit">Checkout</button>
            </form>

            <form action="${pageContext.request.contextPath}/cart" method="post">
                <input type="hidden" name="action" value="clear"/>
                <button type="submit">Clear All</button>
            </form>
        </c:if>

        <a href="book">Continue Shopping</a>
    </div>
</div>



</body>
</html>
