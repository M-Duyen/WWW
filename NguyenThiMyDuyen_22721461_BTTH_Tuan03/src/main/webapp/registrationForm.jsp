<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 9/8/2025
  Time: 1:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Registration Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }

        .form-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 900px;
            border: 1px solid #c7d2e2;
        }

        .form-header {
            text-align: center;
            padding-bottom: 20px;
            border-bottom: 2px solid #e0e0e0;
            margin-bottom: 20px;
        }

        .form-header h2 {
            margin: 0;
            color: #333;
            font-weight: 600;
        }

        .form-row {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-bottom: 15px;
        }

        .form-group {
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .form-group.full-width {
            flex-basis: 100%;
        }

        .form-group label {
            font-weight: bold;
            color: #555;
            margin-bottom: 5px;
        }

        .form-group input,
        .form-group textarea {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }

        .form-group input:focus,
        .form-group textarea:focus {
            border-color: #007bff;
            outline: none;
        }

        .gender-row,
        .hobbies-options,
        .course-options {
            display: flex;
            align-items: center;
            gap: 20px;
            margin-bottom: 15px;
        }

        .gender-row label,
        .hobbies-options label,
        .course-options label {
            font-weight: bold;
            color: #555;
        }

        .gender-options,
        .hobbies-options,
        .course-options {
            gap: 15px;
            font-weight: normal;
        }

        .form-section {
            margin-top: 25px;
        }

        .form-section label {
            font-weight: bold;
            color: #555;
            display: block;
            margin-bottom: 10px;
        }

        .qualification-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        .qualification-table th,
        .qualification-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        .qualification-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        .qualification-table td input {
            width: 90%;
            border: 1px solid #ccc;
            padding: 8px;
            border-radius: 4px;
        }

        .button-group {
            text-align: center;
            margin-top: 30px;
        }

        .button-group button {
            padding: 10px 25px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        .submit-btn {
            background-color: #007bff;
            color: #fff;
            margin-right: 10px;
        }

        .submit-btn:hover {
            background-color: #0056b3;
        }

        .reset-btn {
            background-color: #6c757d;
            color: #fff;
        }

        .reset-btn:hover {
            background-color: #5a6268;
        }

        /* Responsive design */
        @media (max-width: 768px) {
            .form-row {
                flex-direction: column;
            }
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

        .form-container {
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
    <div class="form-container">
        <div class="form-header">
            <h2>Student Registration Form</h2>
        </div>
        <form action="student-servlet" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName">
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" name="lastName">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="dob">Date of Birth</label>
                    <input type="date" id="dob" name="dob">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email">
                </div>
                <div class="form-group">
                    <label for="mobile">Mobile</label>
                    <input type="tel" id="mobile" name="mobile">
                </div>
            </div>

            <div class="form-row gender-row">
                <label>Gender</label>
                <div class="gender-options">
                    <label><input type="radio" name="gender" value="male"> Male</label>
                    <label><input type="radio" name="gender" value="female"> Female</label>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group full-width">
                    <label for="address">Address</label>
                    <textarea id="address" name="address" rows="4"></textarea>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="city">City</label>
                    <input type="text" id="city" name="city">
                </div>
                <div class="form-group">
                    <label for="pincode">Pin Code</label>
                    <input type="text" id="pincode" name="pincode">
                </div>
                <div class="form-group">
                    <label for="state">State</label>
                    <input type="text" id="state" name="state">
                </div>
                <div class="form-group">
                    <label for="country">Country</label>
                    <input type="text" id="country" name="country" >
                </div>
            </div>

            <div class="form-section">
                <label>Hobbies</label>
                <div class="hobbies-options">
                    <label><input type="checkbox" name="hobbies" value="drawing"> Drawing</label>
                    <label><input type="checkbox" name="hobbies" value="singing"> Singing</label>
                    <label><input type="checkbox" name="hobbies" value="dancing"> Dancing</label>
                    <label><input type="checkbox" name="hobbies" value="sketching"> Sketching</label>
                    <label><input type="checkbox" name="hobbies" value="others"> Others</label>
                </div>
            </div>

            <div class="form-section">
                <label>Qualification</label>
                <table class="qualification-table">
                    <thead>
                    <tr>
                        <th>SL.No</th>
                        <th>Examination</th>
                        <th>Board</th>
                        <th>Percentage</th>
                        <th>Year of Passing</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Class X</td>
                        <td><input type="text" name="board10"></td>
                        <td><input type="text" name="percentage10"></td>
                        <td><input type="text" name="year10"></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Class XII</td>
                        <td><input type="text" name="board12"></td>
                        <td><input type="text" name="percentage12"></td>
                        <td><input type="text" name="year12"></td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="form-section">
                <label>Course applies for</label>
                <div class="course-options">
                    <label><input type="radio" name="course" value="BCA"> BCA</label>
                    <label><input type="radio" name="course" value="B.Com"> B.Com</label>
                    <label><input type="radio" name="course" value="B.Sc"> B.Sc</label>
                    <label><input type="radio" name="course" value="B.A"> B.A</label>
                </div>
            </div>

            <div class="button-group">
                <button type="submit" class="submit-btn">Submit</button>
                <button type="reset" class="reset-btn">Reset</button>
            </div>
        </form>
    </div>

</div>

</body>
</html>