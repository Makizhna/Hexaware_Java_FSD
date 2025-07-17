import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import DashboardStatsSection from "./DashboardStatsSection";
import UserManagementSection from "./UserManagementSection";
import JobManagementSection from "./JobManagementSection";
import ApplicationOverviewSection from "./ApplicationOverviewSection"; 
import "../../styles/AdminDashboard.css";

const AdminDashboard = () => {
  const [activeTab, setActiveTab] = useState("dashboard");
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  const renderSection = () => {
    switch (activeTab) {
      case "users":
        return <UserManagementSection />;
      case "jobs":
        return <JobManagementSection />;
      case "applications":
        return <ApplicationOverviewSection />;
      case "dashboard":
      default:
        return <DashboardStatsSection />;
    }
  };

  return (
    <div className="admin-dashboard">
      <aside className="sidebar">
        <Link to="/" className="brand"><h2>Admin Panel</h2></Link>
        <ul className="sidebar-menu">
          <li onClick={() => setActiveTab("dashboard")}
              className={activeTab === "dashboard" ? "active" : ""}>
            <i className="fas fa-chart-line"></i> Dashboard
          </li>
          <li onClick={() => setActiveTab("users")}
              className={activeTab === "users" ? "active" : ""}>
            <i className="fas fa-users-cog"></i> Manage Users
          </li>
          <li onClick={() => setActiveTab("jobs")}
              className={activeTab === "jobs" ? "active" : ""}>
            <i className="fas fa-briefcase"></i> Manage Jobs
          </li>
          <li onClick={() => setActiveTab("applications")}
              className={activeTab === "applications" ? "active" : ""}>
            <i className="fas fa-file-alt"></i> View Applications
          </li>
          <li onClick={handleLogout}>
            <i className="fas fa-sign-out-alt"></i> Logout
          </li>
        </ul>
      </aside>

      <main className="dashboard-main">
        <h1>Welcome Admin!</h1>
        {renderSection()}
      </main>
    </div>
  );
};

export default AdminDashboard;
