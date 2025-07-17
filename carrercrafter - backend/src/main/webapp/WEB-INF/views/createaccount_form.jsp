<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #60a5fa, #34d399);
            min-height: 100vh;
        }

        .register-container {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 60px 20px;
        }

        .form-card {
            background: white;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
        }

        .form-card h3 {
            color: #0d6efd;
            font-weight: bold;
            margin-bottom: 30px;
            text-align: center;
        }

        .form-label {
            font-weight: 600;
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

        .text-link {
            text-align: center;
            margin-top: 20px;
        }

        @media (max-width: 768px) {
            .form-card {
                padding: 30px 20px;
            }
        }
    </style>

    <script>
        function validateForm() {
            let pwd = document.getElementById("password").value;
            let cpwd = document.getElementById("confirmPassword").value;
            if (pwd !== cpwd) {
                document.getElementById("pwdError").innerText = "Passwords do not match!";
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="register-container">
    <div class="form-card">
        <h3>Create Your Account</h3>

        <form action="/register" method="post" onsubmit="return validateForm()">
            <div class="mb-3">
                <label for="name" class="form-label">Full Name</label>
                <input type="text" class="form-control" id="name" name="name" required placeholder="Enter your full name">
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email" required placeholder="you@example.com">
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required placeholder="••••••••">
            </div>

            <div class="mb-3">
                <label for="confirmPassword" class="form-label">Confirm Password</label>
                <input type="password" class="form-control" id="confirmPassword" required placeholder="••••••••">
                <div id="pwdError" class="error-text mt-1"></div>
            </div>

            <div class="mb-4">
                <label class="form-label">Select Role</label>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="role" id="jobSeeker" value="job_seeker" required>
                    <label class="form-check-label" for="jobSeeker">🎓 Job Seeker</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="role" id="employer" value="employer" required>
                    <label class="form-check-label" for="employer">🏢 Employer</label>
                </div>
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-primary btn-custom">Register</button>
            </div>
        </form>

        <div class="text-link">
            Already have an account? <a href="/login">Login here</a>
        </div>
    </div>
</div>

</body>
</html>
