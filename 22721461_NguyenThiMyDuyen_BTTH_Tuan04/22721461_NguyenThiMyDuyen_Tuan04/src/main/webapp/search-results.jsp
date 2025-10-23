<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/15/2025
  Time: 4:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Results</title>
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
<div class="m-2">
    <c:if test="${empty searchResults}">
        <p>No results found.</p>
    </c:if>
    <c:forEach var="book" items="${searchResults}">
        <div>
            <h3>${book.name}</h3>
            <p>Price: ${book.price}</p>
            <img src="images/${book.image}" alt="${book.name}" style="width: 200px;">
        </div>
    </c:forEach>

    <a href="book">Back</a>
</div>

</body>
</html>
