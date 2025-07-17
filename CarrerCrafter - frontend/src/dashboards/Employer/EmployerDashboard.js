import React, { useEffect, useState } from "react";
import "../../styles/EmployerDashboard.css";
import EmployerProfileSection from "./EmployerProfileSection";
import PostJobPage from "./PostJobPage";
import ApplicationsSection from "./ApplicationsSection";
import ManageJobsSection from "./ManageJobsSection";
import ScheduleInterview from "./ScheduleInterview";
import { Link, useNavigate } from "react-router-dom";
import { getJobsByEmployer } from "../../api/jobApi";
import { getApplicationsByEmployerId } from "../../api/applicationsApi";
import { getEmployerByUserId } from "../../api/employerApi";

const EmployerDashboard = () => {
  const [activeTab, setActiveTab] = useState("dashboard");
  const [jobs, setJobs] = useState([]);
  const [applications, setApplications] = useState([]);
  const [employerName, setEmployerName] = useState("Employer");

  const employerId = localStorage.getItem("employerId");
  const userId = localStorage.getItem("currentUserId");
  const token = localStorage.getItem("token");

  const navigate = useNavigate();

  useEffect(() => {
    if (!employerId || !token) return;

    getJobsByEmployer(employerId, token)
      .then((res) => setJobs(res.data))
      .catch(() => {});

    getApplicationsByEmployerId(employerId, token)
      .then((res) => setApplications(res.data))
      .catch(() => {});

    getEmployerByUserId(userId, token)
      .then((res) => setEmployerName(res.data.name || "Employer"))
      .catch(() => {});
  }, [employerId, userId, token]);

  const handleLogout = () => {
    localStorage.removeItem("currentUser");
    window.location.href = "/login";
  };

  const renderSection = () => {
    switch (activeTab) {
      case "profile":
        return <EmployerProfileSection />;
      case "post":
        return <PostJobPage />;
      case "applications":
        return <ApplicationsSection />;
      case "jobs":
        return <ManageJobsSection />;
      case "schedule":
        return <ScheduleInterview />;
      case "dashboard":
      default:
        const activeJobs = jobs.filter((job) => job.active).length;
        const recentApps = applications.slice(-3).reverse(); 
        return (
          <>
            <div className="dashboard-cards">
              <div className="card">
                <h3>Total Jobs Posted</h3>
                <p>{jobs.length}</p>
              </div>
              <div className="card">
                <h3>Applications Received</h3>
                <p>{applications.length}</p>
              </div>
              <div className="card">
                <h3>Active Listings</h3>
                <p>{activeJobs}</p>
              </div>
            </div>

            <section className="recent-activity">
              <h2>Recent Applications</h2>
              <ul>
                {recentApps.length === 0 ? (
                  <li>No recent applications</li>
                ) : (
                  recentApps.map((app) => (
                    <li key={app.id}>
                      {app.jobSeekerName} applied for {app.jobTitle}
                    </li>
                  ))
                )}
              </ul>
            </section>
          </>
        );
    }
  };

  return (
    <div className="employer-dashboard">
      {/* Sidebar */}
      <aside className="sidebar">
        <Link to="/" className="brand"><h2>CareerCrafter</h2></Link>
        <ul className="sidebar-menu">
          <li onClick={() => setActiveTab("dashboard")}>
            <i className="fas fa-home" style={{ color: "#4CAF50", marginRight: "10px" }}></i>
            Dashboard
          </li>
          <li onClick={() => setActiveTab("jobs")}>
            <i className="fas fa-clipboard-list" style={{ color: "#2196F3", marginRight: "10px" }}></i>
            Manage Jobs
          </li>
          <li onClick={() => setActiveTab("applications")}>
            <i className="fas fa-users" style={{ color: "#FF9800", marginRight: "10px" }}></i>
            Applications
          </li>
          <li onClick={() => setActiveTab("post")}>
            <i className="fas fa-plus-circle" style={{ color: "#009688", marginRight: "10px" }}></i>
            Post Job
          </li>
          <li onClick={() => setActiveTab("profile")}>
            <i className="fas fa-user-cog" style={{ color: "#3F51B5", marginRight: "10px" }}></i>
            Profile
          </li>
          <li onClick={handleLogout}>
            <i className="fas fa-sign-out-alt" style={{ color: "#f44336", marginRight: "10px" }}></i>
            Logout
          </li>
        </ul>
      </aside>

      {/* Main Content */}
      <main className="dashboard-main">
        <h1>Welcome Back, {employerName}!</h1>
        {renderSection()}
      </main>
    </div>
  );
};

export default EmployerDashboard;
