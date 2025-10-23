<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 9/15/2025
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Store</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>


        .hinh {
            width: 150px;
            height: 150px;
            border-radius: 5px;
        }
        .header{
            background: antiquewhite;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
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
        <h2>IUH BOOKSTORE</h2>
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
<div style="display: flex; justify-content: end; margin: 10px; padding-right: 50px">
    <a style="background: azure; color: black; border-radius: 5px; text-decoration: none; padding: 5px" href="cart" >View cart</a>
</div>

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
        <c:if test="${empty books}">
            <p>No products available</p>
        </c:if>
        <div class="row">
            <c:forEach items="${books}" var="p">
                <div class="col-md-3 mb-4">
                    <div class="card h-100 text-center shadow-sm">
                        <!-- Tên sách -->
                        <div class="card-header">
                            <b>${p.name}</b>
                        </div>

                        <!-- Ảnh sách -->
                        <img src="images/${p.image}" class="card-img-top p-2" alt="${p.name}" style="height: 200px; object-fit: contain;">

                        <!-- Thông tin sản phẩm -->
                        <div class="card-body">
                            <p class="card-text text-muted mb-1">Price: <span class="fw-bold text-danger">${p.price}₫</span></p>

                            <!-- Form thêm vào giỏ -->
                            <form action="${pageContext.request.contextPath}/cart" method="post" class="mt-2">
                                <div class="input-group input-group-sm mb-2" style="max-width: 120px; margin: auto;">
                                    <input type="number" name="quantity" value="1" min="1" class="form-control text-center">
                                </div>

                                <input type="hidden" name="id" value="${p.id}">
                                <input type="hidden" name="price" value="${p.price}">
                                <input type="hidden" name="model" value="${p.name}">
                                <input type="hidden" name="action" value="add">

                                <button type="submit" class="btn btn-sm btn-primary w-100">Add to Cart</button>
                            </form>
                        </div>

                        <!-- Footer -->
                        <div class="card-footer bg-white">
                            <a href="${pageContext.request.contextPath}/book?id=${p.id}" class="btn btn-link btn-sm">Book Detail</a>
                        </div>
                    </div>
                </div>


            </c:forEach>
        </div>
    </div>
</div>




</body>
</html>
