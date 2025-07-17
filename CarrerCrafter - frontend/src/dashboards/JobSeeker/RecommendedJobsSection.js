// src/components/RecommendedJobsSection.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";
import { FaMapMarkerAlt, FaUserTie, FaClock, FaPaperPlane, FaRegBookmark } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import "../../styles/BrowseJobsSection.css"


const RecommendedJobsSection = ({ seekerId }) => {
  const [recommendedJobs, setRecommendedJobs] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchRecommendedJobs = async () => {
      try {
        const res = await axios.get(`http://localhost:8081/api/jobs/recommend/${seekerId}`);
        setRecommendedJobs(res.data);
      } catch (error) {
        console.error("Failed to fetch recommended jobs", error);
      }
    };

    if (seekerId) fetchRecommendedJobs();
  }, [seekerId]);

  const handleApply = (jobId) => {
    navigate(`/jobs/${jobId}`);
  };

  const handleSave = (jobId) => {
    alert(`Job ${jobId} saved for later!`);
  };

  if (recommendedJobs.length === 0) return null;

  return (
    <>
      <h3 className="browsejobs-heading">🎯 Recommended Jobs for You</h3>
      <div className="browsejobs-grid">
        {recommendedJobs.map((job) => (
          <div className="browsejobs-card" key={job.jobId}>
            <h3 className="browsejobs-title">{job.title}</h3>
            <div className="browsejobs-info">
              <p><FaMapMarkerAlt /> {job.location}</p>
              <p><FaClock /> {job.jobType}</p>
              <p><FaUserTie /> {job.jobExperience}</p>
            </div>
            <div className="browsejobs-actions">
              <button className="browsejobs-btn apply" onClick={() => handleApply(job.jobId)}>
                <FaPaperPlane /> Apply
              </button>
              <button className="browsejobs-btn save" onClick={() => handleSave(job.jobId)}>
                <FaRegBookmark /> Save
              </button>
            </div>
          </div>
        ))}
      </div>
    </>
  );
};

export default RecommendedJobsSection;
