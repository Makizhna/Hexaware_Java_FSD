import React, { useEffect, useState } from "react";
import { getApplicationsByEmployerId, updateApplicationStatus } from "../../api/applicationsApi";
import { getAllInterviews, rejectReschedule } from "../../api/interviewApi";
import { getJobSeekerById } from "../../api/jobSeekerApi";
import { useNavigate } from "react-router-dom";
import "../../styles/ApplicationsSection.css";
import { getResumeBySeekerId } from "../../api/resumeApi";



const ApplicationsSection = () => {
  const [applications, setApplications] = useState([]);
  const [interviews, setInterviews] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState("ALL");
  const [sortOption, setSortOption] = useState("newest");
  const [expandedProfileId, setExpandedProfileId] = useState(null);
  const [jobSeekerProfiles, setJobSeekerProfiles] = useState({});
  const [expandedInterviewId, setExpandedInterviewId] = useState(null);

  
  const navigate = useNavigate();
  const employerId = localStorage.getItem("employerId");
  const token = localStorage.getItem("token");

  useEffect(() => {
  const fetchApplicationsWithResumes = async () => {
    const appResponse = await getApplicationsByEmployerId(employerId, token);
    const applications = await Promise.all(
      appResponse.data.map(async (app) => {
        try {
          const resumeRes = await getResumeBySeekerId(app.jobSeeker.seekerId, token);
          return { ...app, resume: resumeRes.data };
        } catch {
          return { ...app, resume: null };
        }
      })
    );
    setApplications(applications);
  };

  fetchApplicationsWithResumes();
}, []);

useEffect(() => {
  const fetchInterviews = async () => {
    try {
      const res = await getAllInterviews(token);
      setInterviews(res.data || []);
    } catch (err) {
      console.error("Failed to fetch interviews", err);
    }
  };
  fetchInterviews();
}, []);



  const handleReject = async (applicationId) => {
    if (!window.confirm("Reject this application?")) return;
    try {
      await updateApplicationStatus(applicationId, "REJECTED", token);
      setApplications((prev) => prev.filter((app) => app.id !== applicationId));
    } catch (err) {
      alert("Failed to reject application.");
    }
  };

  const handleApproveReschedule = (interviewId) => {
    navigate(`/reschedule-interview/${interviewId}`);
  };

  const handleRejectReschedule = async (interviewId) => {
    try {
      await rejectReschedule(interviewId, token);
      alert("Reschedule rejected");
      setInterviews((prev) =>
        prev.map((intv) =>
          intv.id === interviewId ? { ...intv, rescheduleRequest: null } : intv
        )
      );
    } catch (err) {
      alert("Failed to reject reschedule");
    }
  };

  const getInterviewDetails = (applicationId) =>
    interviews.find((intv) => intv?.applicationId === applicationId);

  const handleViewProfile = async (app) => {
    const seekerId = app.jobSeekerId;
    if (expandedProfileId === seekerId) {
      setExpandedProfileId(null);
    } else {
      if (!jobSeekerProfiles[seekerId]) {
        try {
          const res = await getJobSeekerById(seekerId, token);
          setJobSeekerProfiles((prev) => ({
            ...prev,
            [seekerId]: res.data,
          }));
        } catch (err) {
          console.error("Failed to fetch job seeker profile", err);
        }
      }
      setExpandedProfileId(seekerId);
    }
  };

  const filteredAndSortedApps = () => {
    let result = [...applications];
    if (searchTerm.trim()) {
      const query = searchTerm.toLowerCase();
      result = result.filter(
        (app) =>
          app.jobTitle.toLowerCase().includes(query) ||
          app.jobSeekerName.toLowerCase().includes(query)
      );
    }
    if (statusFilter !== "ALL") {
      result = result.filter((app) => app.status === statusFilter);
    }
    if (sortOption === "status") {
      result.sort((a, b) => a.status.localeCompare(b.status));
    } else {
      result.sort((a, b) =>
        sortOption === "newest"
          ? new Date(b.appliedDate) - new Date(a.appliedDate)
          : new Date(a.appliedDate) - new Date(b.appliedDate)
      );
    }
    return result;
  };

  const getStatusBadgeClass = (status) => {
    switch (status) {
      case "REJECTED":
        return "badge bg-danger";
      case "UNDER_REVIEW":
        return "badge bg-warning text-dark";
      case "SELECTED":
        return "badge bg-success";
      case "INTERVIEW_SCHEDULED":
        return "badge bg-info";
      default:
        return "badge bg-secondary";
    }
  };

  return (
    <div className="applications-section container mt-4">
      <h2 className="text-primary mb-4">📬 Applications Received</h2>

      {/* Filters */}
      <div className="filters d-flex flex-wrap gap-2 mb-4">
        <input
          type="text"
          className="form-control flex-grow-1"
          placeholder="🔍 Search by candidate or job title"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <select className="form-select" value={statusFilter} onChange={(e) => setStatusFilter(e.target.value)}>
          <option value="ALL">All Statuses</option>
          <option value="APPLIED">Applied</option>
          <option value="UNDER_REVIEW">Under Review</option>
          <option value="INTERVIEW_SCHEDULED">Interview Scheduled</option>
          <option value="SELECTED">Selected</option>
          <option value="REJECTED">Rejected</option>
        </select>
        <select className="form-select" value={sortOption} onChange={(e) => setSortOption(e.target.value)}>
          <option value="newest">🕒 Newest First</option>
          <option value="oldest">📅 Oldest First</option>
          <option value="status">🔖 Sort by Status</option>
        </select>
      </div>

      {/* Application Cards */}
      {filteredAndSortedApps().length === 0 ? (
        <p className="text-muted">No applications found.</p>
      ) : (
        filteredAndSortedApps().map((app) => {
          const interview = getInterviewDetails(app.id);
          const profile = jobSeekerProfiles[app.jobSeekerId];
          return (
            <div key={app.id} className="application-card card shadow-sm mb-4">
              <div className="card-header d-flex justify-content-between align-items-center bg-light">
                <div>
                  <h5 className="mb-0">{app.jobTitle}</h5>
                  <small className="text-muted">{app.companyName}</small>
                </div>
                <span className={getStatusBadgeClass(app.status)}>
                  {app.status.replace("_", " ")}
                </span>
              </div>

              <div className="card-body">
                <p>👤 Candidate Name: <strong>{app.jobSeekerName}</strong></p>
                

             {expandedProfileId === app.jobSeekerId && profile && (
   <div className="modal show fade d-block" tabIndex="-1">
    <div className="modal-dialog modal-lg modal-dialog-centered">
      <div className="modal-content">
        <div className="modal-header">
          <h5 className="modal-title">👤 Candidate Profile – {profile.name}</h5>
          <button
            type="button"
            className="btn-close"
            onClick={() => setExpandedProfileId(null)}
          ></button>
        </div>
        <div className="modal-body">
          <div className="row g-3">
            <div className="col-md-6">
              <label className="form-label fw-bold">Full Name</label>
              <div className="form-control bg-light">{profile.name || "-"}</div>
            </div>
            <div className="col-md-6">
              <label className="form-label fw-bold">Date of Birth</label>
              <div className="form-control bg-light">{profile.dob || "-"}</div>
            </div>
            <div className="col-md-6">
              <label className="form-label fw-bold">Phone</label>
              <div className="form-control bg-light">{profile.phone || "-"}</div>
            </div>
            <div className="col-md-6">
              <label className="form-label fw-bold">Location</label>
              <div className="form-control bg-light">{profile.location || "-"}</div>
            </div>
            <div className="col-md-12">
              <label className="form-label fw-bold">Education</label>
              <div className="form-control bg-light">{profile.education || "-"}</div>
            </div>
            <div className="col-md-12">
              <label className="form-label fw-bold">Skills</label>
              <div className="form-control bg-light">{profile.skills || "-"}</div>
            </div>
            <div className="col-md-12">
              <label className="form-label fw-bold">Experience</label>
              <div className="form-control bg-light">{profile.experience || "-"}</div>
            </div>
            <div className="col-md-6">
              <label className="form-label fw-bold">LinkedIn</label>
              <div className="form-control bg-light">
                {profile.linkedinUrl ? (
                  <a href={profile.linkedinUrl} target="_blank" rel="noreferrer">
                    {profile.linkedinUrl}
                  </a>
                ) : (
                  "-"
                )}
              </div>
            </div>
            <div className="col-md-6">
              <label className="form-label fw-bold">GitHub</label>
              <div className="form-control bg-light">
                {profile.githubUrl ? (
                  <a href={profile.githubUrl} target="_blank" rel="noreferrer">
                    {profile.githubUrl}
                  </a>
                ) : (
                  "-"
                )}
              </div>
            </div>
            <div className="col-md-12">
              <label className="form-label fw-bold">Resume</label>
              <div className="form-control bg-light">
                {app.resumeUrl ? (
                  <a
  href={
    app.resumeUrl?.startsWith("http")
      ? app.resumeUrl
      : `http://localhost:8081/${app.resumeUrl}`
  }
  target="_blank"
  rel="noreferrer"
>
  📄 {app.resumeUrl?.split("/").pop()}
</a>
              ) : (
                  "Not uploaded"
                )}
              </div>
            </div>
          </div>
        </div>
        <div className="modal-footer">
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => setExpandedProfileId(null)}
          >
            Close
          </button>
        </div>
      </div>
    </div>
                  </div>
                )}


{app.showResponses && app.coverLetter && (
  <div className="mt-2">
    <h6>📝 Application Form Responses</h6>
    <table className="table table-bordered table-sm mt-2">
      <tbody>
        {app.coverLetter.split("\n").map((line, index) => {
          if (!line.includes(":")) return null;
          const [label, value] = line.split(":").map((s) => s.trim());

          return (
            <tr key={index}>
              <th style={{ width: "40%", background: "#f8f9fa" }}>{label}</th>
              <td>{value || "-"}</td>
            </tr>
          );
        })}


        
      </tbody>
    </table>
  </div>
)}

            {/* Interview Section */}
              {interview && (
                 <>
                  {interview.cancelReason && (
                      <div className="alert alert-danger p-2 mt-2">
                        ❌ Cancel Request: <strong>{interview.cancelReason}</strong>
                      </div>
                    )}
                    {interview.rescheduleRequest && (
                      <div className="alert alert-warning p-2 mt-2">
                        🔁 Reschedule Request: <strong>{interview.rescheduleRequest}</strong>
                        <div className="d-flex gap-2 mt-2">
                          <button
                            className="btn btn-sm btn-outline-success"
                            onClick={() => handleApproveReschedule(interview.id)}
                          >
                            ✅ Approve
                          </button>
                          <button
                            className="btn btn-sm btn-outline-danger"
                            onClick={() => handleRejectReschedule(interview.id)}
                          >
                            ❌ Reject
                          </button>
                        </div>
                      </div>
                    )}

                    

{/* Collapsible Interview Details Form */}
{expandedInterviewId === interview.id && (
  <div className="mt-3 p-3 border rounded bg-light">
    <h6 className="text-primary">📋 Scheduled Interview Form</h6>
    <div className="row g-3">
      <div className="col-md-6">
        <label className="form-label fw-bold">Date & Time</label>
        <div className="form-control bg-white">
          {new Date(interview.interviewDate).toLocaleString()}
        </div>
      </div>
      <div className="col-md-6">
        <label className="form-label fw-bold">Mode</label>
        <div className="form-control bg-white">{interview.mode}</div>
      </div>
      <div className="col-md-12">
        <label className="form-label fw-bold">Meeting Link</label>
        <div className="form-control bg-white">
          {interview.meetingLink ? (
            <a href={interview.meetingLink} target="_blank" rel="noreferrer">
              {interview.meetingLink}
            </a>
          ) : (
            "-"
          )}
        </div>
      </div>
    </div>
  </div>
)}

        </>
          )}


  {/*Important buttons*/}
  <div className="profile-actions-group">
  <button
    className="btn btn-sm btn-outline-primary"
    onClick={() => handleViewProfile(app)}
  >
    {expandedProfileId === app.jobSeekerId ? "🔽 Hide Profile" : "👁 View Full Profile"}
  </button>

  <button
    className="btn btn-sm btn-outline-secondary"
    onClick={() =>
      setApplications((prev) =>
        prev.map((a) =>
          a.id === app.id ? { ...a, showResponses: !a.showResponses } : a
        )
      )
    }
  >
    {app.showResponses ? "🔽 Hide Application Form" : "📋 View Application Form Responses"}
  </button>

  {interview && app.status !== "REJECTED" &&(
    <button
      className="btn btn-outline-info btn-sm"
      onClick={() =>
        setExpandedInterviewId(
          expandedInterviewId === interview.id ? null : interview.id
        )
      }
    >
      {expandedInterviewId === interview.id
        ? "🔽 Hide Interview Details"
        : "📅 View Interview Details"}
    </button>
  )}
</div>


                {/* Action Buttons */}
                <div className="d-flex flex-wrap gap-2 mt-3">
                  {app.status !== "REJECTED" && app.status !== "INTERVIEW_SCHEDULED" && (
                    <button
                      className="btn btn-primary btn-sm"
                      onClick={() => navigate(`/schedule-interview/${app.id}`)}
                    >
                      📅 Schedule Interview
                    </button>
                  )}
                  {app.status !== "REJECTED" && (
                    <button
                      className="btn btn-outline-danger btn-sm"
                      onClick={() => handleReject(app.id)}
                    >
                      ❌ Reject
                    </button>
                  )}
                </div>
              </div>
            </div>
          );
        })
      )}
    </div>
  );
};

export default ApplicationsSection;
