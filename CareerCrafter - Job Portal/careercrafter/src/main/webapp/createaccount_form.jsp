<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Register</title>
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

        .wrapper {
            max-width: 900px;
            width: 100%;
            background-color: white;
            border-radius: 20px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
            overflow: hidden;
        }

        .form-section {
            padding: 30px;
        }

        .bg-image {
            background: url('https://source.unsplash.com/featured/?career,office') no-repeat center center;
            
            background-size: cover;
            min-height: 100%;
        }

        .form-label {
            font-weight: 500;
        }

        .btn-custom {
            border-radius: 30px;
            padding: 10px 20px;
            font-size: 1.1rem;
        }

        .form-heading {
            font-weight: bold;
            color: #0d6efd;
        }

        .error-text {
            color: red;
            font-size: 0.9rem;
        }

        @media (max-width: 768px) {
            .bg-image {
                display: none;
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


<div class="wrapper row g-0">
    <!-- Left Image -->
    <div class="col-md-5 bg-image d-none d-md-block"></div>

    
    <div class="col-md-7 form-section">
        <h3 class="text-center form-heading mb-4">Create Your Account</h3>

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

        <div class="text-center mt-3">
            Already have an account? <a href="/login">Login here</a>
        </div>
    </div>
</div>

</body>
</html>
