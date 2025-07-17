<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #1e293b;">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold text-success" href="/">CareerCrafter</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav me-3">
                <li class="nav-item">
                    <a class="nav-link <c:if test='${page=="home"}'>active</c:if>'" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/jobs">Recent Jobs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/categories">Categories</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/postjob">Post a Job</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/articles">Articles</a>
                </li>
            </ul>

            <div class="d-flex">
                <a href="/login" class="btn btn-outline-light me-2">Sign In</a>
                <a href="/register" class="btn btn-success">Register</a>
            </div>
        </div>
    </div>
</nav>
