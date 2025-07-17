<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Employer Details</title>
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

        .form-card {
            background-color: white;
            border-radius: 20px;
            padding: 40px;
            max-width: 700px;
            margin: auto;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
        }

        .form-heading {
            font-weight: bold;
            color: #0d6efd;
            text-align: center;
            margin-bottom: 30px;
        }

        .btn-custom {
            border-radius: 30px;
        }

        @media (max-width: 768px) {
            .form-card {
                padding: 25px;
            }
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="gradient-bg">
    <div class="form-card">
        <h3 class="form-heading">Employer Profile</h3>
        <form action="/saveEmployer" method="post">
            <p>User ID: ${userId}</p>
            <input type="hidden" name="userId" value="${userId}" />

            <div class="mb-3">
                <label for="name" class="form-label">Employer Name</label>
                <input type="text" name="name" class="form-control" required value="${employer.name}" placeholder="Enter your Name" />
            </div>

            <div class="mb-3">
                <label for="position" class="form-label">Position</label>
                <input type="text" name="position" class="form-control" required value="${employer.position}" placeholder="Enter your Position" />
            </div>

            <div class="mb-3">
                <label for="companyName" class="form-label">Company Name</label>
                <input type="text" name="companyName" class="form-control" required value="${employer.companyName}" placeholder="Enter your company name" />
            </div>

            <div class="mb-4">
                <label for="location" class="form-label">Location</label>
                <input type="text" name="location" class="form-control" required value="${employer.location}" placeholder="Enter your Location" />
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-primary btn-custom">Save Details</button>
            </div>
        </form>

        <div class="text-center mt-4">
            <a href="<c:url value='/listEmployers' />" class="btn btn-outline-primary">View Employers</a>
        </div>
    </div>
</div>

</body>
</html>
