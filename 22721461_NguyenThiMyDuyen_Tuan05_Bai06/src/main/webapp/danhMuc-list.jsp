<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 9/22/2025
  Time: 2:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sach danh muc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
 <div class="container-fluid">
     <h2>Danh sach danh muc</h2>
     <table class="table">
         <tr>
             <th>Ma Danh Muc</th>
             <th>Ten Danh Muc</th>
             <th>Nguoi quan ly</th>
             <th>Ghi chu</th>
             <th>Hanh Dong</th>
         </tr>
         <c:forEach var="danhmucItem" items="${danhMucs}">
             <tr>
                 <td>${danhmucItem.maDM}</td>
                 <td>${danhmucItem.tenDM}</td>
                 <td>${danhmucItem.nguoiQuanLy}</td>
                 <td>${danhmucItem.ghiChu}</td>
                 <td>
                     <a href="danhmuc?action=list&id=${danhmucItem.maDM}">Danh sach tin tuc</a>
                 </td>
             </tr>

         </c:forEach>

     </table>
 </div>

</body>
</html>
