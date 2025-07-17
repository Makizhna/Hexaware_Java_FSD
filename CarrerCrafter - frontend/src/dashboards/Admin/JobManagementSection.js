// src/pages/admin/JobManagementSection.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";

const JobManagementSection = () => {
  const [jobs, setJobs] = useState([]);
  const token = localStorage.getItem("token");

  useEffect(() => {
    axios.get("http://localhost:8081/api/jobs", {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((res) => setJobs(res.data))
      .catch((err) => console.error("Failed to fetch jobs", err));
  }, [token]);

  const handleDelete = (id) => {
    if (!window.confirm("Delete this job posting?")) return;
    axios.delete(`http://localhost:8081/api/jobs/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then(() => setJobs(jobs.filter((j) => j.jobId !== id)))
      .catch(() => alert("Failed to delete job"));
  };

  return (
    <div className="container mt-4">
      <h3>Manage Jobs</h3>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Company</th>
            <th>Location</th>
            <th>Type</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {jobs.map((job) => (
            <tr key={job.jobId}>
              <td>{job.jobId}</td>
              <td>{job.title}</td>
              <td>{job.employer?.companyName}</td>
              <td>{job.location}</td>
              <td>{job.jobType}</td>
              <td>
                <button className="btn btn-danger btn-sm" onClick={() => handleDelete(job.jobId)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
export default JobManagementSection;