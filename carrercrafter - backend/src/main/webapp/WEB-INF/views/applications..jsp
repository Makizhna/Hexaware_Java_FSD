<%@ include file="header.jsp" %>
<div class="container mt-5">
  <h2 class="text-secondary mb-4">Applications</h2>
  <ul class="list-group">
    <c:forEach var="app" items="${applications}">
      <li class="list-group-item d-flex justify-content-between align-items-center">
        <div>
          <strong>${app.job.title}</strong><br/>
          Status: <span class="${app.status eq 'Scheduled' ? 'text-success' : 'text-warning'}">${app.status}</span>
          <c:if test="${not empty app.interviewDate}">
            • Interview: ${app.interviewDate}
          </c:if>
        </div>
        <div>
          <a href="/applications/view/${app.id}" class="btn btn-outline-primary btn-sm">View</a>
        </div>
      </li>
    </c:forEach>
  </ul>
</div>
<%@ include file="footer.jsp" %>
