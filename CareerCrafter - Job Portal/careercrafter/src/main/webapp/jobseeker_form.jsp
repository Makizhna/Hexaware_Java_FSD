<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Job Seeker Details</title>
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
    <h3 class="text-center form-heading">Job Seeker Profile</h3>

    <form action="/saveJobSeeker" method="post">
        <h6 style="color: red;">DEBUG: userId = ${userId}</h6>
        <input type="hidden" name="userId" value="${userId != null ? userId : 0}" />
        

        <div class="mb-3">
            <label for="name" class="form-label">Full Name</label>
            <input type="text" class="form-control" id="name" name="name" required placeholder="Enter your Nmae here">
        </div>

        <div class="mb-3">
            <label for="dob" class="form-label">Date of Birth</label>
            <input type="date" class="form-control" id="dob" name="dob" required>
        </div>

        <div class="mb-3">
            <label for="phone" class="form-label">Phone Number</label>
            <input type="tel" class="form-control" id="phone" name="phone" required placeholder="">
        </div>

        <div class="mb-3">
            <label for="education" class="form-label">Education</label>
            <textarea class="form-control" id="education" name="education" rows="2" required placeholder=""></textarea>
        </div>

        <div class="mb-3">
            <label for="experience" class="form-label">Experience</label>
            <textarea class="form-control" id="experience" name="experience" rows="2" placeholder="Enter your experience"></textarea>
        </div>

        <div class="mb-3">
            <label for="skills" class="form-label">Skills</label>
            <input type="text" class="form-control" id="skills" name="skills" placeholder="">
        </div>

        <div class="mb-4">
            <label for="resumeUrl" class="form-label">Resume URL</label>
            <input type="text" class="form-control" id="resumeUrl" name="resumeUrl" placeholder="e.g. resumes/abc_resume.pdf">
        </div>

        <div class="d-grid">
            <button type="submit" class="btn btn-primary btn-custom">Save Details</button>
        </div>
    </form>
</div>

</body>
</html>
