<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/25/2025
  Time: 1:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sach tin tuc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container-fluid">
    <h2>Danh sach tin tuc</h2>
    <a href="tintuc?action=form">Them tin tuc</a>

    <table class="table">
        <tr>
            <th>Ma Tin Tuc</th>
            <th>Tieu de</th>
            <th>Noi dung tin tuc</th>
            <th>Lien ket</th>
            <th>Ma danh muc</th>
        </tr>
        <c:forEach var="item" items="${tinTucs}">
           <tr>
               <td>${item.id}</td>
               <td>${item.tieuDe}</td>
               <td>${item.noiDungTT}</td>
               <td>${item.lienKet}</td>
               <td>${item.danhMuc.maDM}</td>
           </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
