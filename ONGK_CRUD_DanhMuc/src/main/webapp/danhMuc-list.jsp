<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/25/2025
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sach danh muc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container-fluid">
    <c:if test="${not empty error}">
        <script>
            alert("${error}");
        </script>
    </c:if>


    <h2>Danh sach danh muc</h2>
    <a href="danhMuc-form.jsp">Them danh muc</a>
    <form action="${pageContext.request.contextPath}/danhmuc" method="get" >
        <input type="hidden" name="action" value="search"/>
        <label>Tim kiem</label>
        <input type="text" name="search" placeholder="Tim kiem" class="input-text">
        <button type="submit" class="btn btn-primary">Tim</button>
    </form>


    <table class="table">
        <tr>
            <th>Ma danh muc</th>
            <th>Ten danh muc</th>
            <th>Nguoi quan ly</th>
            <th>Ghi chu</th>
            <th>Hanh dong</th>
        </tr>

        <c:forEach var="item" items="${danhMucs}">
            <tr>
                <td>${item.maDM}</td>
                <td>${item.tenDanhMuc}</td>
                <td>${item.nguoiQuanLy}</td>
                <td>${item.ghiChu}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/danhmuc" method="get" class="d-inline">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="maDM" value="${item.maDM}">
                        <button type="submit" class="btn btn-warning">Chinh sua</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/danhmuc" method="post" class="d-inline">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="maDM" value="${item.maDM}">
                        <button type="submit" class="btn btn-danger"
                                onclick="return confirm('Ban chac chac muon xoa danh muc nay')">Xoa
                        </button>
                    </form>
                    <form action="${pageContext.request.contextPath}/danhmuc" method="get" class="d-inline">
                        <input type="hidden" name="action" value="listTinTuc">
                        <input type="hidden" name="maDM" value="${item.maDM}">
                        <button type="submit" class="btn btn-primary">Tin tuc</button>
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>
    <c:if test="${empty danhMucs}">
        <h7>Khong tim thay</h7>
    </c:if>

</div>

</body>
</html>
