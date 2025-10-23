<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 8/18/2025
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="login" method="post">
    <h1>Đăng nhập</h1>
    <table>
        <tr>
            <td>Tên đăng nhập:</td>
            <td><input type="text" name="username" required></td>
        </tr>
        <tr>
            <td>Mật khẩu:</td>
            <td><input type="password" name="password" required></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Đăng nhập"></td>
        </tr>
    </table>
</form>
</body>
</html>
