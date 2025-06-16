<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Job Seekers List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h2>Registered Job Seekers</h2>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>DOB</th>
                    <th>Phone</th>
                    <th>Education</th>
                    <th>Experience</th>
                    <th>Skills</th>
                    <th>Resume</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${seekers}" var="seeker">
                    <tr>
                        <td>${seeker.user.id}</td>
                        <td>${seeker.name}</td>
                        <td>${seeker.dob}</td>
                        <td>${seeker.phone}</td>
                        <td>${seeker.education}</td>
                        <td>${seeker.experience}</td>
                        <td>${seeker.skills}</td>
                        <td><a href="${seeker.resumeUrl}" target="_blank">Download</a></td>
                        <td>
                            <a href="editJobSeeker/${seeker.user.id}" class="btn btn-warning btn-sm">Edit</a>
                            <a href="deleteJobSeeker/${seeker.user.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
