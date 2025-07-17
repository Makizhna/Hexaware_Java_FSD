<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Employer Home</title>
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

        .home-card {
            background-color: white;
            border-radius: 20px;
            padding: 40px;
            max-width: 600px;
            margin: auto;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
            text-align: center;
        }

        .home-card h2 {
            color: #0d6efd;
            font-weight: bold;
        }

        .btn-custom {
            border-radius: 30px;
            padding: 10px 25px;
        }

        .btn-outline-primary, .btn-danger {
            margin: 0 5px;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="gradient-bg">
    <div class="home-card">
        <h2>Welcome, Employer!</h2>
        <p>You have successfully logged in as an employer.</p>
        <div class="mt-4">
            <a href="listEmployers" class="btn btn-outline-primary btn-custom">View/Edit Your Profile</a>
            <a href="logout" class="btn btn-danger btn-custom">Logout</a>
        </div>
    </div>
</div>

</body>
</html>
