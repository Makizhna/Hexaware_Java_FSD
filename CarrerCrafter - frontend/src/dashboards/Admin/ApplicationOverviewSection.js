// src/dashboards/Admin/ApplicationOverviewSection.jsx
import React, { useEffect, useState } from "react";
import { getAllApplications } from "../../api/applicationsApi";

const ApplicationOverviewSection = () => {
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    getAllApplications(token)
      .then((res) => setApplications(res.data))
      .catch((err) => console.error("Error fetching applications", err));
  }, []);

  return (
    <div>
      <h3>📄 All Applications</h3>
      <table className="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Job Title</th>
            <th>Seeker ID</th>
            <th>Status</th>
            <th>Applied On</th>
          </tr>
        </thead>
        <tbody>
          {applications.map((app) => (
            <tr key={app.id}>
              <td>{app.id}</td>
              <td>{app.jobTitle}</td>
              <td>{app.jobSeekerId}</td>
              <td>{app.status}</td>
              <td>{app.appliedDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ApplicationOverviewSection;
