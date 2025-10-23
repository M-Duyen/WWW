<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 9/8/2025
  Time: 1:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Registration Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            color: #333;
        }

        .container {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
            border: 1px solid #c7d2e2;
        }

        h2, h3 {
            color: #115383;
            border-bottom: 2px solid #e0e0e0;
            padding-bottom: 10px;
            margin-top: 0;
        }

        p {
            font-size: 16px;
            line-height: 1.6;
            margin: 10px 0;
        }

        p b {
            font-weight: bold;
            display: inline-block;
            width: 150px; /* Cố định chiều rộng để các dòng căn chỉnh */
        }

        ul {
            list-style-type: none;
            padding: 0;
            margin: 10px 0;
        }

        li {
            background-color: #f2f2f2;
            padding: 8px 12px;
            border-radius: 4px;
            margin-bottom: 5px;
        }
        .scrollable{
            /* Đặt chiều cao tối đa và cho phép cuộn */
            max-height: 90vh; /* Chiều cao tối đa là 90% chiều cao của viewport */
            overflow-y: auto; /* Tạo thanh cuộn dọc khi cần */
            padding: 20px;
            border-radius: 8px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .container {
            /* Giới hạn chiều rộng của form */
            width: 100%;
            max-width: 600px; /* Giảm chiều rộng tối đa */
            padding: 0; /* Loại bỏ padding ở đây vì đã có ở wrapper */
            border: none; /* Bỏ viền ở đây để tránh viền đôi */
            box-shadow: none; /* Bỏ box-shadow ở đây */
        }
    </style>
</head>
<body>

<div class="scrollable">
    <div class="container">
        <h2>Student Information</h2>
        <p><b>Name:</b> ${student.firstName} ${student.lastName}</p>
        <p><b>Date of Birth:</b> ${student.dob}</p>
        <p><b>Email:</b> ${student.email}</p>
        <p><b>Mobile:</b> ${student.mobile}</p>
        <p><b>Gender:</b> ${student.gender}</p>
        <p><b>Address:</b> ${student.address}, ${student.city}, ${student.state}, ${student.country}</p>
        <p><b>Course:</b> ${student.course}</p>

        <h3>Hobbies</h3>
        <ul>
            <c:forEach var="hobby" items="${student.hobbies}">
                <li>${hobby}</li>
            </c:forEach>
        </ul>

        <h3>Qualification</h3>
        <p><b>Class X Board:</b> ${student.board10}</p>
        <p><b>Class X Percentage:</b> ${student.percentage10}</p>
        <p><b>Class X Year:</b> ${student.year10}</p>

        <p><b>Class XII Board:</b> ${student.board12}</p>
        <p><b>Class XII Percentage:</b> ${student.percentage12}</p>
        <p><b>Class XII Year:</b> ${student.year12}</p>
    </div>
</div>

</body>
</html>