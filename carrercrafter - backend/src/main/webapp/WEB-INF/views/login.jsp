<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
        }

        .gradient-bg {
            background: linear-gradient(135deg, #60a5fa, #34d399);
            min-height: 100vh;
            padding-top: 80px;
            padding-bottom: 40px;
        }

        .login-card {
            background-color: white;
            border-radius: 20px;
            padding: 40px;
            max-width: 450px;
            margin: auto;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
        }

        .form-heading {
            font-weight: bold;
            color: #0d6efd;
            text-align: center;
            margin-bottom: 25px;
        }

        .error-text {
            color: red;
            font-size: 0.9rem;
        }

        .btn-custom {
            border-radius: 30px;
        }

        .form-footer {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="gradient-bg">
    <div class="login-card">
        <h3 class="form-heading">Login to CareerCrafter</h3>

        <form action="/login" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" name="email" id="email" class="form-control" required placeholder="you@example.com" />
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" name="password" id="password" class="form-control" required placeholder="••••••••" />
            </div>

            <c:if test="${not empty error}">
                <div class="error-text">${error}</div>
            </c:if>

            <div class="d-grid mt-4">
                <button type="submit" class="btn btn-primary btn-custom">Login</button>
            </div>
        </form>

        <div class="form-footer">
            <p>Don't have an account? <a href="/register">Register here</a></p>
        </div>
    </div>
</div>

</body>
</html>
