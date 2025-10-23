<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 8/18/2025
  Time: 1:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <style>
        /* CSS to style the form */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #1c2a38;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }

        .register-container {
            background: #2b3e50;
            padding: 2.5rem 3rem;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
            width: 450px;
            color: #f4f4f4;
            position: relative;
            overflow: hidden;
        }

        .register-container::before {
            content: '';
            position: absolute;
            top: -50px;
            left: -50px;
            width: 200px;
            height: 200px;
            background: rgba(255, 255, 255, 0.05);
            transform: rotate(45deg);
        }

        h2 {
            text-align: left;
            font-size: 2.2rem;
            color: #e0e0e0;
            margin-bottom: 2rem;
            font-weight: 300;
            letter-spacing: 1px;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        label {
            display: block;
            font-size: 0.9rem;
            color: #a0a0a0;
            margin-bottom: 0.5rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .required {
            color: #f7a399;
        }

        input[type="text"],
        input[type="email"],
        input[type="url"],
        input[type="password"],
        textarea {
            width: 100%;
            padding: 0.9rem 1.2rem;
            border: 1px solid #4a657c;
            background: #3c526a;
            border-radius: 8px;
            color: #f4f4f4;
            font-size: 1rem;
            transition: all 0.3s ease;
            box-sizing: border-box;
        }

        input:focus,
        textarea:focus {
            outline: none;
            border-color: #5bb3ec;
            background: #4a657c;
            box-shadow: 0 0 0 3px rgba(91, 179, 236, 0.3);
        }

        input::placeholder,
        textarea::placeholder {
            color: #7b8e9b;
        }

        textarea {
            resize: vertical;
            min-height: 100px;
        }

        small {
            display: block;
            font-size: 0.8rem;
            color: #8da2b5;
            margin-top: 0.5rem;
            font-style: italic;
        }

        .name-fields {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }

        .field-labels {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
            margin-top: 0.5rem;
            color: #8da2b5;
            font-size: 0.8rem;
        }

        .password-field {
            position: relative;
        }

        .password-field input {
            padding-right: 2.5rem;
        }

        .password-icon {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #8da2b5;
            transition: color 0.2s ease;
        }

        .password-icon:hover {
            color: #f4f4f4;
        }

        button {
            width: 100%;
            padding: 1rem;
            background: #5bb3ec;
            color: #1c2a38;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1.1rem;
            font-weight: 600;
            margin-top: 1.5rem;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        button:hover {
            background: #4a91c8;
            box-shadow: 0 5px 15px rgba(91, 179, 236, 0.4);
            transform: translateY(-3px);
        }
    </style>
</head>
<body>
<div class="register-container">
    <form action="register-servlet" method="post">
        <h2>Register</h2>

        <div class="form-group">
            <label>Name <span class="required">*</span></label>
            <div class="name-fields">
                <input type="text" id="firstName" name="firstName" required>
                <input type="text" id="lastName" name="lastName">
            </div>
            <div class="field-labels">
                <span>First</span>
                <span>Last</span>
            </div>
        </div>

        <div class="form-group">
            <label for="username">Username <span class="required">*</span></label>
            <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
            <label for="email">E-mail</label>
            <input type="text" id="email" name="email">
        </div>

        <div class="form-group">
            <label for="password">Password <span class="required">*</span></label>
            <div class="password-field">
                <input type="password" id="password" name="password" required>
                <span class="password-icon">👁️</span>
            </div>
        </div>

        <div class="form-group">
            <label for="facebook">Facebook</label>
            <input type="text" id="facebook" name="facebook">
            <small>Enter your Facebook profile URL</small>
        </div>

        <div class="form-group">
            <label for="bio">Short Bio</label>
            <textarea id="bio" name="bio" rows="4"></textarea>
            <small>Share a little information about yourself.</small>
        </div>

        <button type="submit">Submit</button>
    </form>
</div>
</body>
</html>