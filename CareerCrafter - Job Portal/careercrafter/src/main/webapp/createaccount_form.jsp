<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="bootstrap_links.jsp" />
<body>
<jsp:include page="menu.jsp" />

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-lg p-4">
                <h3 class="text-center mb-4 text-primary">Create Your CareerCrafter Account</h3>

                
                <form action="register" method="post">

                    
                    <div class="mb-3">
                        <input type="text" name="name" class="form-control" placeholder="Enter your Name" required />
                    </div>

                    <div class="mb-3">
                        <input type="email" name="email" class="form-control" placeholder="Email" required />
                    </div>

                    <div class="mb-3">
                        <input type="password" name="password" class="form-control" placeholder="Password" required />
                    </div>

                    <div class="mb-3">
                        <input type="password" name="confirmPassword" class="form-control" placeholder="Confirm Password" required />
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Register as:</label><br />
                        <input type="radio" name="role" value="job_seeker" required /> Job Seeker &nbsp;
                        <input type="radio" name="role" value="employer" /> Employer
                    </div>

                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary">Continue</button>
                    </div>
                </form>

                <div class="text-center mt-3">
                    Already have an account? <a href="login">Login here</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
