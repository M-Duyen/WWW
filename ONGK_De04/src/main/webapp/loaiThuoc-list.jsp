<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/27/2025
  Time: 3:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sach loai thuoc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>

</head>
<body>
<div class="container-fluid">
    <h2>Danh sach loai thuoc</h2>
    <table class="table">
        <tr>
            <th>Ma loai thuoc</th>
            <th>Ten loai</th>
            <th>Hanh dong</th>
        </tr>
        <c:forEach var="item" items="${loaiThuocs}">
            <tr>
                <td>${item.maLoai}</td>
                <td>${item.tenLoai}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/loaithuoc" method="get">
                        <input type="hidden" name="maLoai" value="${item.maLoai}">
                        <input type="hidden" name="action" value="getList">
                        <button type="submit" class="btn btn-link">Danh sach</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
