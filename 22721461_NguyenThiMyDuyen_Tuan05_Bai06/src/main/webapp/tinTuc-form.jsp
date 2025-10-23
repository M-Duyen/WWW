<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/22/2025
  Time: 11:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thong tin tin tuc</title>
</head>
<body>
<div class="container-fluid">
    <h2>Cap nhat tin tuc</h2>
    <form action="${pageContext.request.contextPath}/tintuc" method="post">
        <input type="hidden" name="action" value="${tinTuc == null ? 'add' : 'edit'}">
        <c:if test="${tinTuc != null}">
            <input type="hidden" name="maTT" value="${tinTuc.maTT}">
        </c:if>

        <label>Tieu de:</label>
        <input type="text" name="tieuDe" value="${tinTuc != null ? tinTuc.tieuDe : ''}" style="width: 300px">
        <br>
        <label>Noi dung:</label>
        <input type="text" name="noiDung" value="${tinTuc != null ? tinTuc.noiDung : ''}" style="width: 300px">
        <br>
        <label>Lien ket:</label>
        <input type="text" name="lienKet" value="${tinTuc != null ? tinTuc.lienKet : ''}" style="width: 300px">
        <br>
        <label>Danh muc</label>
        <select name="maDM" style="width: 100px">
            <c:forEach var="danhMuc" items="${danhMucs}">
                <option value="${danhMuc.maDM}"
                        <c:if test="${tinTuc != null && tinTuc.danhMuc.maDM == danhMuc.maDM}">
                            selected
                        </c:if>>
                        ${danhMuc.tenDM}
                </option>
            </c:forEach>
        </select>
        <br/>

        <button type="submit">Luu</button>
    </form>

</div>

</body>
</html>
