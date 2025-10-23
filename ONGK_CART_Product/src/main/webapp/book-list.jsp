<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/26/2025
  Time: 1:16 PM
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
    <a href="cart.jsp">Cart</a>
    <h2>Book List</h2>
    <div>
        <form action="book" name="serach" method="get">
            <label>Search: </label>
            <input type="text" name="search" placeholder="Search here..."/>
            <button type="submit" class="btn btn+primary">Search</button>
        </form>
    </div>
    <div>
        <c:if test="empty books">
            <p>No books available </p>
        </c:if>
        <div class="row" style="display: flex; flex-wrap: wrap; justify-content: center; align-items: center;">
            <c:forEach var="book" items="${books}">
                <div class="col-md-2 text-center" style="border: 1px solid black; border-radius: 5px; margin: 5px; padding: 5px">
                    <div>
                        <b>${book.name}</b>

                    </div>

                    <img src="images/${book.image}" alt="${book.name}" style="width:220px;height:300px;"/>

                    <div>
                        <b style="color: red ; margin-bottom: 5px">${book.price}$</b>

                    </div>
                    <form action="${pageContext.request.contextPath}/cart" method="post">
                        <div>
                            <label>
                                <input class="text-center" type="number" name = "quantity" min="1" value="1" max="${book.quantity}" style="margin-bottom: 5px">
                            </label>
                        </div>

                        <input type="hidden" name = "id" value="${book.id}">
<%--                        <input type="hidden" name="price" value="${book.price}">--%>
                        <input type="hidden" name="action" value="add">

                        <div>
                            <button type="submit" class="btn btn-primary">Add to cart</button>
                        </div>

                    </form>

                    <form action="${pageContext.request.contextPath}/book" method="get">
                        <input type="hidden" name = "id" value="${book.id}">
                        <input type="hidden" name="action" value="details">
                        <button type="submit" class="btn btn-link">Details</button>
                    </form>
                </div>
            </c:forEach>
        </div>



    </div>


</div>

</body>
</html>
