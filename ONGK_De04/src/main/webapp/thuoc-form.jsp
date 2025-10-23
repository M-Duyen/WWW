<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/27/2025
  Time: 4:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Them thuoc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container-fluid">
    <h2>Them thuoc</h2>
    <form action="${pageContext.request.contextPath}/thuoc" method="post">
        <input type="hidden" name="action" value="add">

        <label>Ten thuoc</label>
        <input type="text" name="tenThuoc">
        <br>

        <label>Gia</label>
        <label>
            <input type="text" name="gia">
        </label>
        <br>

        <label>Nam san xuat</label>
        <input type="number" name="namSX">
        <br>

        <label>Ma loai</label>
        <select name="maLoai">
            <c:forEach var="item" items="${loaiThuocs}">
                <option value="${item.maLoai}">
                        ${item.tenLoai}
                </option>
            </c:forEach>
        </select>
        <button type="submit" class="btn btn-primary">Luu</button>
    </form>
</div>


</body>
</html>
