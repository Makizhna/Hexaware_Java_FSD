import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import AOS from "aos";
import "aos/dist/aos.css";

import ProfileSection from "./ProfileSection";
import AppliedJobsSection from "./AppliedJobsSection";
import SavedJobsSection from "./SavedJobsSection";
import InterviewScheduleSection from "./Interviews";
import ResumeSection from "../ResumeSection";
import BrowseJobsSection from "../../pages/BrowseJobsSection";
import RecommendedJobsSection from "./RecommendedJobsSection";

import { getApplicationsByJobSeekerId } from "../../api/applicationsApi";
import { getSavedJobs } from "../../api/savedJobsApi";
import { getInterviewsByJobSeeker } from "../../api/interviewApi";
import { getJobSeekerByUserId } from "../../api/jobSeekerApi";

import "../../styles/JobSeekerDashboard.css";

const JobSeekerDashboard = () => {
  const [activeTab, setActiveTab] = useState("dashboard");
  const [jobSeekerId, setJobSeekerId] = useState(null);
  const [appliedCount, setAppliedCount] = useState(0);
  const [savedCount, setSavedCount] = useState(0);
  const [interviewCount, setInterviewCount] = useState(0);
  const [jobSeekerName, setJobSeekerName] = useState("Job Seeker");


  const navigate = useNavigate();

  useEffect(() => {
    AOS.init({ duration: 800 });

    const userId = localStorage.getItem("currentUserId");
    const token = localStorage.getItem("token");

    if (userId && token) {
      getJobSeekerByUserId(userId, token)
        .then((res) => {
          const seekerId = res.data.seekerId;
          setJobSeekerName(res.data.name || "Job Seeker");
          setJobSeekerId(seekerId);

          //  Get applied job count
          getApplicationsByJobSeekerId(seekerId, token)
            .then((res) => setAppliedCount(res.data.length))
            .catch(() => setAppliedCount(0));

          //  Get saved job count
          getSavedJobs(token)
            .then((res) => {
              const savedBySeeker = res.data.filter(job => job.jobSeekerId === seekerId);
              setSavedCount(savedBySeeker.length);
            })
            .catch(() => setSavedCount(0));

          //  Get upcoming interviews
          getInterviewsByJobSeeker(seekerId, token)
            .then((res) => {
              const upcoming = res.data.filter(i =>
                new Date(i.interviewDate) >= new Date()
              );
              setInterviewCount(upcoming.length);
            })
            .catch(() => setInterviewCount(0));
        })
        .catch(() => console.error("Failed to get job seeker"));
    }
  }, []);

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  const renderSection = () => {
    switch (activeTab) {
      case "profile": return <ProfileSection />;
      case "applied": return <AppliedJobsSection />;
      case "saved": return <SavedJobsSection />;
      case "interviews": return <InterviewScheduleSection />;
      case "resume": return <ResumeSection />;
      case "browse": return <BrowseJobsSection />;
      case "dashboard":
      default:
        return (
          <>
            {/* Dashboard Summary Cards */}
            <div className="dashboard-cards">
              <div className="card">
                <h3>Jobs Applied</h3>
                <p>{appliedCount}</p>
              </div>
              <div className="card">
                <h3>Saved Jobs</h3>
                <p>{savedCount}</p>
              </div>
              <div className="card">
                <h3>Upcoming Interviews</h3>
                <p>{interviewCount}</p>
              </div>
            </div>

           


            {/* Recommended Jobs */}
            {jobSeekerId && (
              <div className="mt-4">
                <RecommendedJobsSection seekerId={jobSeekerId} />
              </div>
            )}
          </>
        );
    }
  };

  return (
    <div className="jobseeker-dashboard">
      <aside className="sidebar">
        <Link to="/" className="brand"><h2>CareerCrafter</h2></Link>
        <ul>
          <li onClick={() => setActiveTab("dashboard")}>
            <i className="fas fa-home" style={{ color: "#4CAF50", marginRight: "10px" }}></i>
            Dashboard
          </li>
          <li className={activeTab === "profile" ? "active" : ""} onClick={() => setActiveTab("profile")}>
            <i className="fas fa-user-circle" style={{ color: "#4CAF50", marginRight: "10px" }}></i>
            My Profile
          </li>
          <li className={activeTab === "applied" ? "active" : ""} onClick={() => setActiveTab("applied")}>
            <i className="fas fa-briefcase" style={{ color: "#2196F3", marginRight: "10px" }}></i>
            Applied Jobs
          </li>
          <li className={activeTab === "saved" ? "active" : ""} onClick={() => setActiveTab("saved")}>
            <i className="fas fa-bookmark" style={{ color: "#FF9800", marginRight: "10px" }}></i>
            Saved Jobs
          </li>
          <li className={activeTab === "interviews" ? "active" : ""} onClick={() => setActiveTab("interviews")}>
            <i className="fas fa-calendar-check" style={{ color: "#9C27B0", marginRight: "10px" }}></i>
            Interviews
          </li>
          
          <li className={activeTab === "browse" ? "active" : ""} onClick={() => setActiveTab("browse")}>
            <i className="fas fa-search" style={{ color: "#3F51B5", marginRight: "10px" }}></i>
            Browse Jobs
          </li>
          <li onClick={handleLogout}>
            <i className="fas fa-sign-out-alt" style={{ color: "#f44336", marginRight: "10px" }}></i>
            Logout
          </li>
        </ul>
      </aside>

      <main className="dashboard-main">
        <h1>Welcome Back, {jobSeekerName}!</h1>
        {renderSection()}
      </main>
    </div>
  );
};

export default JobSeekerDashboard;