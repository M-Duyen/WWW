<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/15/2025
  Time: 5:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Checkout</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            margin-top: 50px;
            max-width: 600px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Checkout</h2>
    <form action="checkout" method="post">
        <div class="mb-3 row">
            <label class="col-sm-3 col-form-label">Fullname:</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="fullname" required>
            </div>
        </div>

        <div class="mb-3 row">
            <label class="col-sm-3 col-form-label">Shipping address:</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="address" required>
            </div>
        </div>

        <div class="mb-3 row">
            <label class="col-sm-3 col-form-label">Total price:</label>
            <div class="col-sm-9">
<%--                <c:if test="${cart == null}">--%>
<%--                    <input type="text" class="form-control" name="total" value="0" readonly>--%>
<%--                </c:if>--%>
                <input type="text" class="form-control" name="total" value="${cart.total}" readonly>
            </div>
        </div>

        <div class="mb-3 row">
            <label class="col-sm-3 col-form-label">Payment method:</label>
            <div class="col-sm-9 d-flex align-items-center">
                <div class="form-check me-3">
                    <input class="form-check-input" type="radio" name="payment" value="Paypal" required>
                    <label class="form-check-label">Paypal</label>
                </div>
                <div class="form-check me-3">
                    <input class="form-check-input" type="radio" name="payment" value="ATM Debit">
                    <label class="form-check-label">ATM Debit</label>
                </div>
                <div class="form-check me-3">
                    <input class="form-check-input" type="radio" name="payment" value="Visa/Master card">
                    <label class="form-check-label">Visa/Master card</label>
                </div>
            </div>
        </div>

        <div class="text-center">
            <button type="submit" class="btn btn-success me-2">Save</button>
            <a href="cart" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
</html>
