<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #60a5fa, #34d399);
            font-family: 'Segoe UI', sans-serif;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .login-card {
            background-color: white;
            padding: 30px;
            border-radius: 20px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
            width: 100%;
            max-width: 450px;
        }

        .form-heading {
            font-weight: bold;
            color: #0d6efd;
            margin-bottom: 25px;
            text-align: center;
        }

        .form-label {
            font-weight: 500;
        }

        .btn-custom {
            border-radius: 30px;
            padding: 10px 20px;
            font-size: 1.1rem;
        }

        .error-text {
            color: red;
            font-size: 0.9rem;
        }

        .form-footer {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp" />


<div class="login-card">
    <h3 class="form-heading">Login to CareerCrafter</h3>

    <form action="/login" method="post">
        <div class="mb-3">
            <label for="email" class="form-label">Email address</label>
            <input type="email" class="form-control" id="email" name="email" required placeholder="you@example.com">
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password" required placeholder="••••••••">
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

</body>
</html>
