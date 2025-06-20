<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<jsp:include page="menu.jsp"/>
<jsp:include page="bootstrap_links.jsp"/>

<head>
    <title>List of Employers</title>
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Registered Employers</h2>

    <table class="table table-bordered table-striped">
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
                        <a href="editEmployer/${employer.user.id}" class="btn btn-warning btn-sm">Edit</a>
                        <a href="deleteEmployer/${employer.user.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
