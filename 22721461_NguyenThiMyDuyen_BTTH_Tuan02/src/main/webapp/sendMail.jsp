<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 8/25/2025
  Time: 1:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Send Mail</title>
    <form action="send-mail" method="post" enctype="multipart/form-data">
        <label> Người nhận: </label>
        <input type="email" name="to" id="to" required>
        <br>
        <label> Tiêu đề: </label>
        <input type="text" name="subject" id="subject" required>
        <br>
        <label> Nội dung: </label>
        <textarea name="body" id="body" cols="30" rows="10" required></textarea>
        <br>
        <label> File đính kèm: </label>
        <input type="file" name="file" id="file">
        <br>
        <button type="submit">Gửi mail</button>

    </form>
</head>
<body>

</body>
</html>
