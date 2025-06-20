<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Employer Details</title>
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

        .form-card {
            background-color: white;
            padding: 30px;
            border-radius: 20px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
            width: 100%;
            max-width: 700px;
        }

        .form-heading {
            font-weight: bold;
            color: #0d6efd;
            margin-bottom: 25px;
        }

        .form-label {
            font-weight: 500;
        }

        .btn-custom {
            border-radius: 30px;
            padding: 10px 20px;
            font-size: 1.1rem;
        }

        @media (max-width: 576px) {
            .form-card {
                padding: 20px;
            }
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />


<div class="form-card">
    <h3 class="text-center form-heading">Employer Profile</h3>

    <form action="/saveEmployer" method="post">
        <p>User ID: ${userId}</p>
    
        <input type="hidden" name="userId" value="${userId}" />

        <div class="mb-3">
            <label for="name" class="form-label">Employer Name</label>
            <input type="text" class="form-control" id="name" name="name" required placeholder="Enter your Name">
        </div>

        <div class="mb-3">
            <label for="position" class="form-label">Position</label>
            <input type="text" class="form-control" id="position" name="position" required placeholder="Enter your Position">
        </div>

        <div class="mb-3">
            <label for="companyName" class="form-label">Company Name</label>
            <input type="text" class="form-control" id="companyName" name="companyName" required placeholder="Enter your company name">
        </div>

        <div class="mb-4">
            <label for="location" class="form-label">Location</label>
            <input type="text" class="form-control" id="location" name="location" required placeholder="Enter your Location">
        </div>

        <div class="d-grid">
            <button type="submit" class="btn btn-primary btn-custom">Save Details</button>
        </div>
    </form>
</div>

</body>
</html>
