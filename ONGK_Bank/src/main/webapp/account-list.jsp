<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/28/2025
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <script>
        function toggleInputs() {
            const criteria = document.getElementById("criteria").value;
            document.getElementById("amountInput").style.display = criteria === "amount" ? "block" : "none";
            document.getElementById("cityInput").style.display = criteria === "city" ? "block" : "none";
        }
    </script>

</head>
<body>
<div class="container-fluid">
    <div>
        <form action="${pageContext.request.contextPath}/bank" method="get">
            <div>
                <label>Criteria: </label>
                <select id="criteria" name="criteria" class="form-select" onchange="toggleInputs()">
                    <option value="amount">Amount</option>
                    <option value="city">City</option>
                </select>
            </div>

            <div id="amountInput" class="m-3">
                <label>Min:</label>
                <input type="number" id="min" name="min" step="0.01" class="mt-3">
                <br>

                <label>Max:</label>
                <input type="number" id="max" name="max" step="0.01">
                <br>

            </div>

            <div id="cityInput" class="m-3" style="display: none">
                <label>City: </label>
                <input type="text" id="city" name="city">
            </div>

            <button type="submit" class="btn btn-outline-success">Search</button>


        </form>

    </div>

    <a href="account-form.jsp">Add account</a>
    <table class="table">
        <tr>
            <th>Account Number</th>
            <th>Account Name</th>
            <th>Card Number</th>
            <th>Owner Address</th>
            <th>Amount</th>
        </tr>
        <c:forEach var="item" items="${accounts}">
            <tr>
                <td>${item.accountNumber}</td>
                <td>${item.ownerName}</td>
                <td>${item.cardNumber}</td>
                <td>${item.ownerAddress}</td>
                <td>${item.amount}</td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
