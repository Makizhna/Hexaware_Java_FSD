import React, { useEffect, useState } from "react";
import {
  getJobsByEmployer,
  deleteJob,
  updateJob
} from "../../api/jobApi";
import { getEmployerByUserId } from "../../api/employerApi";
import { useNavigate } from "react-router-dom";
import "../../styles/ManageJobsSection.css";


  const ManageJobsSection = () => {
  const [jobs, setJobs] = useState([]);
  const [employerId, setEmployerId] = useState(null);

  const userId = localStorage.getItem("currentUserId");
  const token = localStorage.getItem("token");

  const navigate = useNavigate();

  const fetchJobs = async (empId) => {
    try {
      const res = await getJobsByEmployer(empId);
      setJobs(res.data);
    } catch (err) {
      console.error("Failed to load jobs:", err.message);
    }
  };

  useEffect(() => {
    if (!userId || !token) return;

    getEmployerByUserId(userId, token)
      .then((res) => {
        if (res.data && res.data.employeeId) {
          setEmployerId(res.data.employeeId);
          fetchJobs(res.data.employeeId);
        } else {
          alert("Employer profile not found.");
        }
      })
      .catch((err) => {
        console.error("Failed to fetch employer:", err.response?.data || err.message);
      });
  }, [userId, token]);

  const toggleStatus = async (job) => {
    const action = job.active ? "deactivate" : "activate";

    const confirmToggle = window.confirm(
      `Are you sure you want to ${action} this job?\n\n${
        job.active
          ? "It will be hidden from job seekers temporarily."
          : "It will become visible to job seekers again."
      }`
    );

    if (!confirmToggle) return;

    const updatedJob = { ...job, active: !job.active };
    try {
      await updateJob(job.jobId, updatedJob);
      fetchJobs(employerId);
    } catch (err) {
      alert("Failed to update job status.");
    }
  };

  const deleteHandler = async (id) => {
    if (window.confirm("Are you sure you want to delete this job? This action cannot be undone.")) {
      try {
        await deleteJob(id);
        fetchJobs(employerId);
      } catch (err) {
        alert("Failed to delete job.");
      }
    }
  };

  return (
    <div className="manage-jobs-section">
      <h2>Manage Posted Jobs</h2>
      <table className="jobs-table">
        <thead>
          <tr>
            <th>Title</th>
            <th>Location</th>
            <th>Type</th>
            <th>Work Mode</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {jobs.map((job) => (
            <tr key={job.jobId}>
              <td>{job.title}</td>
              <td>{job.location}</td>
              <td>{job.jobType}</td>
              <td>{job.locationType}</td>
              <td>
                <span className={`status-badge ${job.active ? "active" : "inactive"}`}>
                  {job.active ? "Active" : "Inactive"}
                </span>
              </td>
              <td>
                <div className="action-btn-group">
                <button
                  className={`action-btn toggle-btn ${job.active ? "active" : "inactive"}`}
                  onClick={() => toggleStatus(job)}
                  title={
                    job.active
                      ? "Deactivate this job to temporarily hide it from job seekers."
                      : "Activate this job to make it visible to job seekers again."
                  }
                >
                  {job.active ? "Deactivate" : "Activate"}
                </button>

                <button
                  className="action-btn delete-btn"
                  onClick={() => deleteHandler(job.jobId)}
                  title="Permanently delete this job. This action cannot be undone."
                >
                  Delete
                </button>

                <button
                  className="action-btn edit-btn"
                  onClick={() => navigate(`/edit-job/${job.jobId}`)}
                  title="Edit this job's details"
                >
                  Edit
                </button>
              </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ManageJobsSection;
