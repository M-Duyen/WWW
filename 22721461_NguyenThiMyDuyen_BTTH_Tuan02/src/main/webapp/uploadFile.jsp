<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 8/25/2025
  Time: 12:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload file</title>
    <form action="upload" method="post" enctype="multipart/form-data">
        <label>File #1: </label>
        <input type="file" name="file" id="file1" required>
        <br>
<%--        <label>File #2: </label>--%>
<%--        <input type="file" name="file" id="file2" required>--%>
<%--        <br>--%>
<%--        <label>File #3: </label>--%>
<%--        <input type="file" name="file" id="file3" required>--%>
<%--        <br>--%>
<%--        <label>File #4: </label>--%>
<%--        <input type="file" name="file" id="file4" required>--%>
<%--        <br>--%>
<%--        <label>File #5: </label>--%>
<%--        <input type="file" name="file" id="file5" required>--%>
        <br>
        <button type="submit">Upload</button>
        <button type="reset">Reset</button>

    </form>
</head>
<body>

</body>
</html>
