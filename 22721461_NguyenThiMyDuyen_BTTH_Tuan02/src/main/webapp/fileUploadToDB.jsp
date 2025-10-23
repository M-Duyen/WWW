<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 8/25/2025
  Time: 2:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload File to Database</title>
</head>
<body>
    <form action="upload-contact" method="post" enctype="multipart/form-data">
        <label>First name: </label>
        <input type="text" name="firstName" id="firstName" required>
        <br>
        <label>Last name: </label>
        <input type="text" name="lastName" id="lastName" required>
        <br>
        <label>Portrait Photo: </label>
        <input type="file" name="photo" id="photo" required>
        <br>
        <button type="submit">Save</button>
    </form>

</body>
</html>
