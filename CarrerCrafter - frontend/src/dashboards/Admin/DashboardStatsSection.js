import React, { useEffect, useState } from "react";
import axios from "axios";

const DashboardStatsSection = () => {
  const [stats, setStats] = useState({
    totalUsers: 0,
    totalJobs: 0,
    totalApplications: 0,
  });

  useEffect(() => {
    const token = localStorage.getItem("token");
    const fetchStats = async () => {
      try {
        const [usersRes, jobsRes, appsRes] = await Promise.all([
          axios.get("http://localhost:8081/api/user", { headers: { Authorization: `Bearer ${token}` } }),
          axios.get("http://localhost:8081/api/jobs"),
          axios.get("http://localhost:8081/api/applications", { headers: { Authorization: `Bearer ${token}` } }),
        ]);
        setStats({
          totalUsers: usersRes.data.length,
          totalJobs: jobsRes.data.length,
          totalApplications: appsRes.data.length,
        });
      } catch (err) {
        console.error("Error loading stats", err);
      }
    };
    fetchStats();
  }, []);

  return (
    <div className="admin-dashboard-cards">
      <div className="card"><h3>Total Users</h3><p>{stats.totalUsers}</p></div>
      <div className="card"><h3>Total Jobs</h3><p>{stats.totalJobs}</p></div>
      <div className="card"><h3>Total Applications</h3><p>{stats.totalApplications}</p></div>
    </div>
  );
};

export default DashboardStatsSection;
