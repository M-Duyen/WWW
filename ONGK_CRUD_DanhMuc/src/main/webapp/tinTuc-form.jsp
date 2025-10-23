<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/25/2025
  Time: 1:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thong tin Tin tuc</title>
</head>
<body>
<div class="container-fluid">
    <h2>${tinTuc != null ? "Chinh sua tin tuc" : "Them tin tuc"}</h2>

    <form action="${pageContext.request.contextPath}/tintuc" method="post" onsubmit="return validateForm()">
        <c:if test="${tinTuc != null}">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${tinTuc.id}">

        </c:if>

        <c:if test="${tinTuc == null}">
            <input type="hidden" name="action" value="add">
        </c:if>

        <label>Tieu de:</label>
        <input type="text" name="tieuDe" id="tieuDe" value="${tinTuc.tieuDe}">
        <br>
        <label>Noi dung:</label>
        <input type="text" name="noiDung" id="noiDung" value="${tinTuc.noiDung}">
        <br>
        <label>Lien ket:</label>
        <input type="text" name="lienKet" id="lienKet" value="${tinTuc.lienKet}">
        <br>
        <label>Danh muc: </label>

        <select name="danhMuc">
            <c:forEach var="item" items="${danhMucs}">
                <option value="${item.maDM}"
                        <c:if test="${tinTuc != null && tinTuc.danhMuc.maDM == item.maDM}">
                            selected
                        </c:if>
                >
                        ${item.tenDanhMuc}
                </option>


            </c:forEach>
        </select>
        <button type="submit">Luu</button>

    </form>

</div>
<script>
    function validateForm() {
        const lienKet = document.getElementById("lienKet").value;
        const regex = /^https?:\/\//
        if (!regex.test(lienKet)) {
            alert('Please input a valid email address');
            return false;
        }
        return true;
    }
</script>

</body>
</html>
