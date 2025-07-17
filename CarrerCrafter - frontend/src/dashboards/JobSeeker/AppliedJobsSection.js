// AppliedJobsSection.jsx - With status icons, pagination, and interview preview
import React, { useEffect, useState } from "react";
import { getApplicationsByJobSeekerId,deleteApplication,} from "../../api/applicationsApi";
import { getInterviewByApplicationId } from "../../api/interviewApi";
import { useNavigate } from "react-router-dom";
import { FaBriefcase,FaCalendarAlt,FaEye,FaTrashAlt, FaClock,FaCalendarCheck,FaTimesCircle,} from "react-icons/fa";
import "../../styles/AppliedJobsSection.css";

const AppliedJobsSection = () => {
  const [applications, setApplications] = useState([]);
  const [filter, setFilter] = useState("");
  const [search, setSearch] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [interviewDates, setInterviewDates] = useState({});
  const navigate = useNavigate();

  const seekerId = localStorage.getItem("jobSeekerId");
  const token = localStorage.getItem("token");
  const itemsPerPage = 5;

  useEffect(() => {
    if (!seekerId || !token) return;
    getApplicationsByJobSeekerId(seekerId, token)
      .then((res) => {
        setApplications(res.data);
        res.data.forEach((app) => {
          if (app.status === "INTERVIEW_SCHEDULED") {
            getInterviewByApplicationId(app.id, token).then((res) => {
              setInterviewDates((prev) => ({
                ...prev,
                [app.id]: res.data.interviewDate,
              }));
            });
          }
        });
      })
      .catch((err) => console.error("Error fetching applied jobs", err));
  }, [seekerId, token]);

  const handleWithdraw = async (id) => {
    if (!window.confirm("Are you sure you want to withdraw this application?")) return;
    try {
      await deleteApplication(id, token);
      setApplications(applications.filter((a) => a.id !== id));
    } catch (err) {
      console.error("Error withdrawing application:", err);
      alert("Failed to withdraw. Try again.");
    }
  };

  const filteredApplications = applications.filter((app) => {
    const match = `${app.jobTitle || ""} ${app.companyName || ""}`.toLowerCase();
    return match.includes(search.toLowerCase()) && (!filter || app.status === filter);
  });

  const totalPages = Math.ceil(filteredApplications.length / itemsPerPage);
  const paginatedApps = filteredApplications.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  return (
    <div className="container mt-5 applied-jobs-section">
      <div className="applied-header">
        <h4 className="text-primary">📄 My Applied Jobs ({applications.length})</h4>
        <select
          className="form-select"
          value={filter}
          onChange={(e) => setFilter(e.target.value)}
        >
          <option value="">All Statuses</option>
          <option value="APPLIED">Applied</option>
          <option value="INTERVIEW_SCHEDULED">Interview Scheduled</option>
          <option value="REJECTED">Rejected</option>
        </select>
      </div>

      <input
        type="text"
        className="form-control mb-3"
        placeholder="Search job title or company..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      {paginatedApps.length === 0 ? (
        <div className="empty-state">
          <img src="/images/empty-state.svg" alt="No applications" />
          <p>You haven’t applied to any jobs yet.</p>
        </div>
      ) : (
        paginatedApps.map((app) => (
          <div className="applied-job-card" key={app.id}>
            <div className="d-flex justify-content-between align-items-center">
              <div>
                <div className="job-title">{app.jobTitle}</div>
                <div className="job-meta">
                  <span className="me-3">🏢 {app.companyName}</span>
                  <span className="me-3">
                    <FaBriefcase className="icon" /> Job ID: {app.jobId}
                  </span>
                  <span className="me-3">
                    <FaCalendarAlt className="icon" /> {app.appliedDate}
                  </span>
                  {app.status === "INTERVIEW_SCHEDULED" && interviewDates[app.id] && (
                    <span className="me-3">
                      <FaCalendarCheck className="icon" /> Interview: {new Date(interviewDates[app.id]).toLocaleString()}
                    </span>
                  )}
                </div>
                <div className={`status-badge status-${app.status.toLowerCase()}`}>
                  {app.status === "APPLIED" && <FaClock className="me-1" />} 
                  {app.status === "INTERVIEW_SCHEDULED" && <FaCalendarCheck className="me-1" />} 
                  {app.status === "REJECTED" && <FaTimesCircle className="me-1" />} 
                  {app.status.replaceAll("_", " ")}
                </div>
              </div>

              <div className="job-actions">
                <button
                  className="btn btn-view"
                  onClick={() => navigate(`/jobs/${app.jobId}`,  { state: { from: "applied-jobs" } })}
                >
                  <FaEye className="me-1" /> View Job
                </button>
                <button
                  className="btn btn-withdraw"
                  onClick={() => handleWithdraw(app.id)}
                >
                  <FaTrashAlt className="me-1" /> Withdraw
                </button>
              </div>
            </div>
          </div>
        ))
      )}

      {/* Pagination Controls */}
      {totalPages > 1 && (
        <div className="pagination-controls d-flex justify-content-center mt-4">
          {[...Array(totalPages)].map((_, idx) => (
            <button
              key={idx}
              className={`btn mx-1 ${currentPage === idx + 1 ? "btn-primary" : "btn-outline-secondary"}`}
              onClick={() => setCurrentPage(idx + 1)}
            >
              {idx + 1}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

export default AppliedJobsSection;
