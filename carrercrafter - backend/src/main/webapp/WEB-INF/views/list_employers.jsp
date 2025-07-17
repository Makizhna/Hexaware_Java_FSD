<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Employers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #60a5fa, #34d399);
            min-height: 100vh;
        }

        .table-container {
            background: white;
            margin: 80px auto;
            padding: 40px;
            max-width: 1100px;
            border-radius: 20px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
        }

        .table-title {
            font-weight: bold;
            color: #0d6efd;
            margin-bottom: 30px;
            text-align: center;
        }

        .btn-sm {
            padding: 5px 10px;
            border-radius: 20px;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="table-container">
    <h2 class="table-title">Registered Employers</h2>
    <table class="table table-bordered table-hover align-middle text-center">
        <thead class="table-dark">
            <tr>
                <th>User ID</th>
                <th>Name</th>
                <th>Company</th>
                <th>Position</th>
                <th>Location</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="employer" items="${employers}">
                <tr>
                    <td>${employer.user.id}</td>
                    <td>${employer.name}</td>
                    <td>${employer.companyName}</td>
                    <td>${employer.position}</td>
                    <td>${employer.location}</td>
                    <td>
                        <a href="editEmployer/${employer.employeeId}" class="btn btn-warning btn-sm">Edit</a>
                        <a href="<c:url value='/deleteEmployer/${employer.employeeId}'/>" class="btn btn-danger btn-sm"
                           onclick="return confirm('Are you sure you want to delete this employer?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
