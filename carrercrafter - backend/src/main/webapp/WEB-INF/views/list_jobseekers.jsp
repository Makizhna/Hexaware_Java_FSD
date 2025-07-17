<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Job Seeker List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Job Seeker Details</h2>

    <c:choose>
        <c:when test="${not empty seekers}">
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Name</th>
                        <th>Date of Birth</th>
                        <th>Phone</th>
                        <th>Education</th>
                        <th>Experience</th>
                        <th>Skills</th>
                        <th>Resume URL</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${seekers}">
                        <tr>
                            <td>${s.name}</td>
                            <td>${s.dob}</td>
                            <td>${s.phone}</td>
                            <td>${s.education}</td>
                            <td>${s.experience}</td>
                            <td>${s.skills}</td>
                            <td><a href="${s.resumeUrl}" target="_blank">View Resume</a></td>
                            <td>
                                <a href="/editJobSeeker/${s.user.id}" class="btn btn-sm btn-warning">Edit</a>
                                <a href="/deleteJobSeeker/${s.user.id}" class="btn btn-sm btn-danger"
                                   onclick="return confirm('Are you sure you want to delete this job seeker?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="alert alert-info">No job seeker data available.</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
