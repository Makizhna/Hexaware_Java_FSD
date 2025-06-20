<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | HomePage </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #dbeafe, #f0fdf4);
            font-family: 'Segoe UI', sans-serif;
        }

        .navbar {
            background-color: #0d6efd;
        }

        .navbar-brand {
            color: white !important;
            font-weight: bold;
        }

        .hero-section {
            padding: 80px 20px;
            text-align: center;
            background: linear-gradient(135deg, #60a5fa, #34d399);
            color: white;
            border-radius: 0 0 60px 60px;
        }

        .hero-section h1 {
            font-size: 3rem;
            font-weight: bold;
        }

        .hero-section p {
            font-size: 1.2rem;
        }

        .action-cards {
            margin-top: -60px;
        }

        .card-custom {
            border-radius: 20px;
            transition: transform 0.3s;
            box-shadow: 0 6px 18px rgba(0,0,0,0.1);
        }

        .card-custom:hover {
            transform: scale(1.03);
        }

        .btn-custom {
            border-radius: 30px;
            padding: 10px 25px;
            font-size: 1.1rem;
        }

        .footer {
            margin-top: 60px;
            padding: 20px;
            text-align: center;
            color: #555;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="hero-section">
    <h1>Welcome to CareerCrafter</h1>
    <p>Your career starts here. Join us to explore jobs or hire talent effortlessly.</p>
</div>

<div class="container action-cards">
    <div class="row justify-content-center g-4">
        <div class="col-md-4">
            <div class="card card-custom text-center p-4 border-primary">
                <h3 class="text-primary mb-3">🔐 Login</h3>
                <p>Access your dashboard as a Job Seeker or Employer.</p>
                <a href="/login" class="btn btn-outline-primary btn-custom">Login Now</a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card card-custom text-center p-4 border-success">
                <h3 class="text-success mb-3">📝 Register</h3>
                <p>Create a new account to start your journey with us.</p>
                <a href="/register" class="btn btn-outline-success btn-custom">Register Now</a>
            </div>
        </div>
    </div>

    <div class="row mt-5 text-center">
        <div class="col-md-6 mx-auto">
            <h4>💼 Employers</h4>
            <p>Post jobs, review applications, and find top candidates quickly.</p>
        </div>
        <div class="col-md-6 mx-auto">
            <h4>🎓 Job Seekers</h4>
            <p>Search jobs, apply with your resume, and track interview schedules.</p>
        </div>
    </div>
</div>

<div class="footer">
    &copy; 2025 CareerCrafter. All rights reserved.
</div>

</body>
</html>
