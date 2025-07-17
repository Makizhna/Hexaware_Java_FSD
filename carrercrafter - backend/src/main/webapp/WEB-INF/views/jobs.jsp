<%@ include file="header.jsp" %>
<div class="container mt-5">
  <h2 class="text-primary mb-4">Available Jobs</h2>
  <div class="row gy-4">
    <c:forEach var="job" items="${jobs}">
      <div class="col-md-6">
        <div class="card shadow-sm h-100">
          <div class="card-body">
            <h5 class="card-title">${job.title}</h5>
            <p class="card-text text-muted">${job.companyName} • ${job.location}</p>
            <p><strong>₹${job.salary}</strong></p>
            <p class="card-text small">${job.qualifications}</p>
            <a href="/jobs/apply/${job.jobId}" class="btn btn-primary">Apply Now</a>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>
</div>
<%@ include file="footer.jsp" %>
