<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="bootstrap_links.jsp" />
<body>
<jsp:include page="menu.jsp" />

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow p-4">
                <h3 class="text-center text-primary mb-4">Job Seeker Details</h3>

                <form action="saveJobSeeker" method="post">
                    <input type="hidden" name="userId" value="${userId}" />
                    

                    <div class="mb-3">
                        <label>Name</label>
                        <input type="text" name="name" class="form-control" required />
                    </div>

                    <div class="mb-3">
                        <label>Date of Birth</label>
                        <input type="date" name="dob" class="form-control" required />
                    </div>

                    <div class="mb-3">
                        <label>Phone</label>
                        <input type="text" name="phone" class="form-control" required />
                    </div>

                    <div class="mb-3">
                        <label>Education</label>
                        <input type="text" name="education" class="form-control" required />
                    </div>

                    <div class="mb-3">
                        <label>Experience</label>
                        <input type="text" name="experience" class="form-control" required />
                    </div>

                    <div class="mb-3">
                        <label>Skills</label>
                        <input type="text" name="skills" class="form-control" required />
                    </div>

                    <div class="mb-3">
                        <label>Resume URL</label>
                        <input type="text" name="resumeUrl" class="form-control" />
                    </div>

                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-success">Submit Details</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>