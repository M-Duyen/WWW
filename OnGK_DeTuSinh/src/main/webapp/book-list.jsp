<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/28/2025
  Time: 10:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book List</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <script>
    function toggleInputs(){
      const criteria = document.getElementById("criteria").value;
      document.getElementById("priceInput").style.display = criteria === 'price' ? 'block' : 'none'
      document.getElementById("authorInput").style.display = criteria === 'author' ? 'block' : 'none'
      document.getElementById("nameInput").style.display = criteria === 'name' ? 'block' : 'none';
    }
  </script>

</head>
<body>
<div class="container-fluid">
  <h2> Book List</h2>
  <a href="${pageContext.request.contextPath}/cart">View cart</a>

  <form action="${pageContext.request.contextPath}/book" method="get">
    <div>
      <label>Criteria</label>
      <select id="criteria" name="criteria" onchange="toggleInputs()" class="form-select">
        <option value="price">Price</option>
        <option value="author">Author</option>
        <option value="name">Name</option>
      </select>
    </div>

    <div id="priceInput">
      <div>
        <label>Min:</label>
        <input type="number" id="min" name="min" step="0.01">
      </div>
      <div>
        <label>Max:</label>
        <input type="number" id="max" name="max" step="0.01">
      </div>

    </div>
    <div id="authorInput" style="display: none">
      <div>
        <label>Author:</label>
        <select name="author">
          <c:forEach var="item" items="${authors}" >
              <option value="${item}">${item}</option>
          </c:forEach>
        </select>
      </div>

    </div>
    <div id="nameInput" style="display: none">
      <div>
        <label>Name:</label>
        <input type="text" id="name" name="name">
      </div>
    </div>
    <button type="submit" class="btn btn-outline-success">Search</button>

  </form>

  <table class="table">
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Author</th>
      <th>Price</th>
      <th>Publication</th>
      <th>Category ID</th>
      <th>Action</th>
    </tr>
    <c:forEach var="item" items="${books}">
      <tr>
        <td>${item.id}</td>
        <td>${item.name}</td>
        <td>${item.author}</td>
        <td>${item.price}</td>
        <td>${item.publication}</td>
        <td>${item.category.id}</td>
        <td>
          <a href="book?action=detail&id=${item.id}" class="d-inline">View</a>
          <form action="${pageContext.request.contextPath}/book" method="post" class="d-inline">
            <input type="hidden" name="id" value="${item.id}">
            <input type="hidden" name="action" value="update">
            <button type="submit" class="btn btn-outline-warning">Update</button>
          </form>
          <form action="${pageContext.request.contextPath}/book" method="post" class="d-inline">
            <input type="hidden" name="id" value="${item.id}">
            <input type="hidden" name="action" value="delete">
            <button type="submit" class="btn btn-outline-danger">Delete</button>
          </form>
          <form action="${pageContext.request.contextPath}/cart" method="post" class="d-inline">
            <input type="number" name="quantity" value="1">

            <input type="hidden" name="id" value="${item.id}">
            <input type="hidden" name="action" value="add">
            <button type="submit" class="btn btn-outline-info">Add to cart</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>

</body>
</html>
