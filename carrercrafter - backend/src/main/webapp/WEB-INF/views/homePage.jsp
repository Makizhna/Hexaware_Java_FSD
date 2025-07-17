<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCrafter | Find Your Dream Job</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f9f9f9;
        }

        .navbar-brand {
            font-weight: bold;
            color: white !important;
        }

        .hero {
            background: linear-gradient(135deg, #60a5fa, #34d399);
            padding: 100px 20px;
            color: white;
            text-align: center;
        }

        .hero h1 {
            font-size: 3.5rem;
            font-weight: bold;
        }

        .hero p {
            font-size: 1.2rem;
        }

        .category-box {
            background: #fff;
            border-radius: 10px;
            padding: 15px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0,0,0,0.05);
            transition: 0.3s ease;
        }

        .category-box:hover {
            transform: scale(1.05);
            box-shadow: 0 6px 16px rgba(0,0,0,0.1);
        }

        .job-card {
            background: white;
            border: 1px solid #eee;
            border-radius: 10px;
            padding: 20px;
            transition: 0.3s;
        }

        .job-card:hover {
            box-shadow: 0 8px 24px rgba(0,0,0,0.1);
        }

        .testimonial {
            background: #f1f5f9;
            padding: 50px 20px;
            text-align: center;
        }

        .footer {
            background-color: #0d6efd;
            color: white;
            padding: 20px 0;
            text-align: center;
        }

        .btn-main {
            border-radius: 25px;
            padding: 10px 30px;
            font-weight: 500;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />

<!-- HERO -->
<section class="hero">
    <div class="container">
        <h1>All latest Dream Jobs<br>are waiting for you</h1>
        <p>Search your desired job by categories or apply to top companies hiring now!</p>
        <a href="/register" class="btn btn-light btn-main mt-4">Get Started</a>
    </div>
</section>

<!-- SEARCH BY CATEGORY -->
<section class="container py-5">
    <h3 class="text-center mb-4">Search Desired Job by Categories</h3>
    <div class="row g-4 text-center">
        <c:forEach begin="1" end="6">
            <div class="col-6 col-md-2">
                <div class="category-box">
                    <img src="https://img.icons8.com/color/48/briefcase.png" />
                    <p class="mt-2">IT Jobs</p>
                </div>
            </div>
        </c:forEach>
    </div>
</section>

<!-- FEATURED JOBS -->
<section class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4>Explore Featured Jobs</h4>
        <a href="#" class="btn btn-outline-primary btn-sm">View All</a>
    </div>
    <div class="row g-4">
        <c:forEach begin="1" end="3">
            <div class="col-md-4">
                <div class="job-card">
                    <h5>Frontend Developer</h5>
                    <p><strong>Company:</strong> Google</p>
                    <p><strong>Location:</strong> Bangalore</p>
                    <p><strong>Salary:</strong> ₹12-15 LPA</p>
                    <a href="#" class="btn btn-primary btn-sm">Apply Now</a>
                </div>
            </div>
        </c:forEach>
    </div>
</section>

<!-- CLIENT TESTIMONIAL -->
<section class="testimonial">
    <div class="container">
        <h4 class="mb-4">What Our Clients Say</h4>
        <blockquote class="blockquote">
            <p>"CareerCrafter helped me land my dream job in just 3 weeks!"</p>
            <footer class="blockquote-footer">Rohit Sharma, placed at Amazon</footer>
        </blockquote>
    </div>
</section>

<!-- FOOTER -->
<div class="footer">
    &copy; 2025 CareerCrafter. Designed with 💙 by YourTeam.
</div>

</body>
</html>
