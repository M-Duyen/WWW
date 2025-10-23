<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/25/2025
  Time: 11:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thong tin danh muc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container-fluid">
    <h2>${danhMuc != null ? "Cap nhat danh muc" : "Them danh muc"}</h2>
    <form action="danhmuc" method="post" onsubmit="return validateForm()">
        <c:if test="${danhMuc != null }">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="maDM" value="${danhMuc.maDM}">

        </c:if>

        <c:if test="${danhMuc == null}">
            <input type="hidden" name="action" value="add">
        </c:if>

        <label>Ten danh muc: </label>
        <label>
            <input type="text" id="tenDanhMuc" name="tenDanhMuc" value="${danhMuc != null ? danhMuc.tenDanhMuc : ""}"
                   style="width: 500px">
        </label>
        <br>
        <label>Nguoi quan ly: </label>
        <label>
            <input type="text" id="nguoiQuanLy" name="nguoiQuanLy"
                   value="${danhMuc != null ? danhMuc.nguoiQuanLy : ""}" style="width: 500px">
        </label>
        <br>
        <label>Ghi chu: </label>
        <label>
            <textarea type="text" id="ghiChu" name="ghiChu"
                   style="width: 500px">
                ${danhMuc.ghiChu}
            </textarea>
        </label>
        <br>

        <button type="submit">Luu</button>


    </form>
    <script>
        function validateForm() {
            const tenDanhMuc = document.getElementById('tenDanhMuc').value;
            const nguoiQuanLy = document.getElementById('nguoiQuanLy').value;
            const ghiChu = document.getElementById('ghiChu').value;

            if (tenDanhMuc.length > 255) {
                alert("Ten danh muc it hon 255 ky tu");
                return false;
            }
            if (nguoiQuanLy.length > 255) {
                alert("Nguoi quan ly it hon 255 ky tu");
                return false;
            }

            if (ghiChu.length > 255) {
                alert("Ghi chu it hon 255 ky tu");
                return false;
            }
            return true;

        }
    </script>

</div>

</body>
</html>
