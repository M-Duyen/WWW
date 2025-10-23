<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/28/2025
  Time: 2:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <script>
        function toggleInputs() {
            const criteria = document.getElementById("criteria").value;
            document.getElementById("amountInputs").style.display = criteria === "amount" ? "block" : "none";
            document.getElementById("cityInput").style.display = criteria === "city" ? "block" : "none";
        }
    </script>
</head>
<body>
<div class="container mt-4">
    <h3 class="mb-3">Tìm kiếm tài khoản</h3>
    <form action="${pageContext.request.contextPath}/bank" method="get" class="border p-3 rounded bg-light">
        <div class="mb-3">
            <label for="criteria" class="form-label">Tiêu chí:</label>
            <select id="criteria" name="criteria" class="form-select" onchange="toggleInputs()">
                <option value="amount">Số tiền (khoảng)</option>
                <option value="city">Tỉnh/Thành phố</option>
            </select>
        </div>

        <!-- Inputs cho khoảng số tiền -->
        <div id="amountInputs" class="row mb-3">
            <div class="col">
                <label for="minAmount" class="form-label">Min Amount:</label>
                <input type="number" step="0.01" name="minAmount" id="minAmount" class="form-control">
            </div>
            <div class="col">
                <label for="maxAmount" class="form-label">Max Amount:</label>
                <input type="number" step="0.01" name="maxAmount" id="maxAmount" class="form-control">
            </div>
        </div>

        <!-- Input cho thành phố -->
        <div id="cityInput" class="mb-3" style="display:none;">
            <label for="city" class="form-label">Tỉnh/Thành phố:</label>
            <input type="text" name="city" id="city" class="form-control">
        </div>

        <button type="submit" class="btn btn-primary">Search</button>
    </form>
</div>

</body>
</html>
