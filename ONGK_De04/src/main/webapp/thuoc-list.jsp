<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/27/2025
  Time: 3:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sach thuoc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container-fluid">
    <a href="${pageContext.request.contextPath}/loaithuoc">Danh sach loai thuoc</a>
    <br>
    <a href="${pageContext.request.contextPath}/thuoc?action=form">Them thuoc</a>
    <h2>Danh sach thuoc</h2>
    <table class="table">
        <tr>
            <th>Ma thuoc</th>
            <th>Ten thuoc</th>
            <th>Gia</th>
            <th>Nam san xuat</th>
            <th>Ma loai</th>
        </tr>
        <c:forEach var="item" items="${thuocs}">
            <tr>
                <td>${item.maThuoc}</td>
                <td>${item.tenThuoc}</td>
                <td>${item.gia}</td>
                <td>${item.namSX}</td>
                <td>${item.loaiThuoc.maLoai}</td>
            </tr>

        </c:forEach>
    </table>

</div>

</body>
</html>
