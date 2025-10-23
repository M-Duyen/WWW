<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 9/22/2025
  Time: 2:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sach Tin Tuc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <h2>Danh sach tin tuc</h2>
    <a href="${pageContext.request.contextPath}/tintuc?action=add">Them tin tuc</a>
    <table class="table">
        <tr>
            <th>Ma Tin Tuc</th>
            <th>Tieu De</th>
            <th>Noi Dung</th>
            <th>Lien ket</th>
            <th>Ma danh muc</th>
            <th>Hanh dong</th>
        </tr>
        <c:forEach var="tinTuc" items="${tinTucs}">
            <tr>
                <td>${tinTuc.maTT}</td>
                <td>${tinTuc.tieuDe}</td>
                <td>${tinTuc.noiDung}</td>
                <td>${tinTuc.lienKet}</td>
                <td>${tinTuc.danhMuc.maDM}</td>
                <td>
                    <form action="tintuc" method="get" class="d-inline">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="maTT" value="${tinTuc.maTT}">
                        <button class="btn btn-link">Edit</button>
                    </form>
                    <form action="tintuc" method="post"  class="d-inline">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="maTT" value="${tinTuc.maTT}">
                        <button class="btn btn-link">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>
</div>

</body>
</html>
