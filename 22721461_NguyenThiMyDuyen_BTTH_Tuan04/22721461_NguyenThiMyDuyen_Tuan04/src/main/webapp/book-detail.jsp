<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 9/15/2025
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Detail</title>
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
            <form action="${pageContext.request.contextPath}/search" method="get">
                <input type="text" name="query" placeholder="Search.." style="border-radius: 5px; padding: 5px">
                <button type="submit" style="margin-top: 5px; border-radius: 5px; padding: 5px">Search</button>
            </form>
        </div>
    </div>
    <div class="col-9">
        <c:if test="${not empty book}">
            <b>Model:</b> ${book.name} <br><br>
            <img src="images/${book.image}" alt=${book.name}>
            <br><br>
            <b>Price:</b> ${book.price} <br><br>
            <%--    <b>Description:</b> ${product.description} <br><br>--%>

            <form action="${pageContext.request.contextPath}/cart" method="post">
                <label>
                    Quantity:
                    <input type="number" size="1" value="1" name="quantity" style="width: 40px; text-align: center">
                </label>
                <br><br>
                <input type="hidden" name="id" value="${book.id}">
                <input type="hidden" name="price" value="${book.price}">
                <input type="hidden" name="model" value="${book.name}">
                <input type="hidden" name="action" value="add"><br/>
                <button type="submit" name="addToCart">Add To Cart</button>
            </form>
            <a href="cart">View cart</a>
            <br>
            <a href="book">Back</a>

        </c:if>
    </div>
</div>



</body>
</html>
