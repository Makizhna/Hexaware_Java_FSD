<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="bootstrap_links.jsp" />
<jsp:include page="menu.jsp" />
<body>

<div class="container mt-5">
    <div class="card shadow p-4">
        <h2 class="text-primary text-center">Welcome, Employer!</h2>
        <p class="text-center">You have successfully logged in as an employer.</p>
        <div class="text-center mt-3">
            <a href="list_employers" class="btn btn-outline-primary">View/Edit Your Profile</a>
            <a href="logout" class="btn btn-danger ms-2">Logout</a>
        </div>
    </div>
</div>

</body>
</html>
