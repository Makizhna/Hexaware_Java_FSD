<%@ include file="header.jsp" %>
<div class="container my-5">
  <div class="row gy-4">
    <div class="col-md-4">
      <div class="card shadow p-4 text-center border-primary">
        <h5 class="text-primary">💼 Your Jobs</h5>
        <h2>${jobsCount}</h2>
        <a href="/jobs" class="btn btn-outline-primary mt-3">View Jobs</a>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card shadow p-4 text-center border-secondary">
        <h5 class="text-secondary">📥 My Applications</h5>
        <h2>${appCount}</h2>
        <a href="/applications" class="btn btn-outline-secondary mt-3">View Applications</a>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card shadow p-4 text-center border-success">
        <h5 class="text-success">⭐ Saved Jobs</h5>
        <h2>${savedCount}</h2>
        <a href="/saved" class="btn btn-outline-success mt-3">View Saved</a>
      </div>
    </div>
  </div>
</div>
<%@ include file="footer.jsp" %>
