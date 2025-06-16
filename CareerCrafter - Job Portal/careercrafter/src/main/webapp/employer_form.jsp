<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employer Details</title>
    <jsp:include page="bootstrap_links.jsp"/>
</head>
<body>
<jsp:include page="menu.jsp"/>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow p-4">
                <h3 class="text-primary mb-4 text-center">Employer Details Form</h3>

                
                <c:if test="${not empty userId}">
                    <p class="text-muted text-center">Session userId: ${userId}</p>
                </c:if>

                <form action="saveEmployer" method="post">

                    
                    <input type="hidden" name="user.id" value="${userId}" />

                    <div class="mb-3">
                        <label for="name" class="form-label">Full Name:</label>
                        <input type="text" name="name" class="form-control" value="${employer.name}" required />
                    </div>

                    <div class="mb-3">
                        <label for="position" class="form-label">Position:</label>
                        <input type="text" name="position" class="form-control" value="${employer.position}" />
                    </div>

                    <div class="mb-3">
                        <label for="companyName" class="form-label">Company Name:</label>
                        <input type="text" name="companyName" class="form-control" value="${employer.companyName}" />
                    </div>

                    <div class="mb-3">
                        <label for="location" class="form-label">Location:</label>
                        <input type="text" name="location" class="form-control" value="${employer.location}" />
                    </div>

                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-success">Submit</button>
                        <button type="reset" class="btn btn-secondary">Reset</button>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>

</body>
</html>
