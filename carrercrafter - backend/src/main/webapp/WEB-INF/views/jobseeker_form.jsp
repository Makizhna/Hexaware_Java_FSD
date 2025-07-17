<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Job Seeker Form</title>
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
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
            max-width: 700px;
            margin: auto;
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
        <h3 class="form-heading">Job Seeker Profile</h3>
        <form action="/saveJobSeeker" method="post">
            <input type="hidden" name="userId" value="${userId}" />
            <c:if test="${jobSeeker.seekerId != null}">
                <input type="hidden" name="seekerId" value="${jobSeeker.seekerId}" />
            </c:if>

            <div class="mb-3">
                <label for="name" class="form-label">Full Name</label>
                <input type="text" name="name" class="form-control" placeholder="Your Name"
                       value="${jobSeeker.name}" required />
            </div>

            <div class="mb-3">
                <label for="dob" class="form-label">Date of Birth</label>
                <input type="date" name="dob" class="form-control"
                       value="${jobSeeker.dob}" required />
            </div>

            <div class="mb-3">
                <label for="phone" class="form-label">Phone Number</label>
                <input type="tel" name="phone" class="form-control"
                       value="${jobSeeker.phone}" required />
            </div>

            <div class="mb-3">
                <label for="education" class="form-label">Education</label>
                <textarea name="education" class="form-control" rows="2" required>${jobSeeker.education}</textarea>
            </div>

            <div class="mb-3">
                <label for="experience" class="form-label">Experience</label>
                <textarea name="experience" class="form-control" rows="2">${jobSeeker.experience}</textarea>
            </div>

            <div class="mb-3">
                <label for="skills" class="form-label">Skills</label>
                <input type="text" name="skills" class="form-control" value="${jobSeeker.skills}" />
            </div>

            <div class="mb-4">
                <label for="resumeUrl" class="form-label">Resume URL</label>
                <input type="text" name="resumeUrl" class="form-control" value="${jobSeeker.resumeUrl}" />
            </div>

            <div class="d-grid">
                <button class="btn btn-primary btn-custom">Save Details</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>
