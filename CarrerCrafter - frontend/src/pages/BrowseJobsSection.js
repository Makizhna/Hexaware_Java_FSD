import React, { useEffect, useState } from "react";
import { getAllJobs } from "../api/jobApi";
import { useNavigate } from "react-router-dom";
import {
  FaMapMarkerAlt, FaUserTie, FaClock, FaRegBookmark, FaBookmark, FaPaperPlane,
} from "react-icons/fa";
import "../styles/BrowseJobsSection.css";
import { saveJob, getSavedJobsBySeekerId } from "../api/savedJobsApi";
import { getJobSeekerByUserId } from "../api/jobSeekerApi";

const BrowseJobsSection = () => {
  const [jobs, setJobs] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [locationFilter, setLocationFilter] = useState("");
  const [skillFilter, setSkillFilter] = useState("");
  const [jobSeekerId, setJobSeekerId] = useState(null);
  const [savedJobIds, setSavedJobIds] = useState([]);

  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("currentUserId");
  const navigate = useNavigate();

  useEffect(() => {
    getAllJobs()
      .then((res) => setJobs(res.data))
      .catch((err) => {
        console.error("Failed to fetch jobs", err);
        alert("Unable to load jobs");
      });
  }, []);

  useEffect(() => {
    if (userId && token) {
      getJobSeekerByUserId(userId, token)
        .then((res) => {
          const seekerId = res.data.seekerId;
          setJobSeekerId(seekerId);

          return getSavedJobsBySeekerId(seekerId, token);
        })
        .then((res) => {
          const savedIds = res.data.map((job) => job.jobId);
          setSavedJobIds(savedIds);
        })
        .catch(() => console.error("Error fetching saved jobs"));
    }
  }, [userId, token]);

  const handleApply = (jobId) => {
    navigate(`/jobs/${jobId}`, { state: { from: "browse-jobs" } });
  };

  const handleSave = async (jobId) => {
    if (!jobSeekerId || !token) {
      alert("Please log in to save jobs.");
      return;
    }

    if (savedJobIds.includes(jobId)) {
      alert("⚠️ You have already saved this job.");
      return;
    }

    try {
      await saveJob(jobId, jobSeekerId, token);
      setSavedJobIds((prev) => [...prev, jobId]);
      alert("💾 Job saved successfully!");
    } catch (error) {
      console.error("Failed to save job:", error);
      alert("❌ Failed to save job.");
    }
  };

  const filteredJobs = jobs.filter((job) => {
    const titleMatch = job.title?.toLowerCase().includes(searchTerm.toLowerCase());
    const companyMatch = job.employer?.companyName?.toLowerCase().includes(searchTerm.toLowerCase());
    const locationMatch = job.location?.toLowerCase().includes(locationFilter.toLowerCase());
    const skillMatch = job.skillsRequired?.some(skill =>
      skill.toLowerCase().includes(skillFilter.toLowerCase())
    );

    return (titleMatch || companyMatch) && locationMatch && skillMatch;
  });

  return (
    <div className="browsejobs-container">
      <h2 className="browsejobs-heading">Explore Jobs</h2>

      <div className="row mb-4 g-2">
        <div className="col-md-4">
          <input
            type="text"
            className="form-control"
            placeholder="Search by title or company"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <div className="col-md-3">
          <input
            type="text"
            className="form-control"
            placeholder="Filter by location"
            value={locationFilter}
            onChange={(e) => setLocationFilter(e.target.value)}
          />
        </div>
        <div className="col-md-3">
          <input
            type="text"
            className="form-control"
            placeholder="Filter by skill"
            value={skillFilter}
            onChange={(e) => setSkillFilter(e.target.value)}
          />
        </div>
        <div className="col-md-2">
          <button className="btn btn-secondary w-100" onClick={() => navigate("/jobseeker/dashboard")}>
            ← Back
          </button>
        </div>
      </div>

      <div className="browsejobs-grid">
        {filteredJobs.length === 0 ? (
          <p>No jobs match your search.</p>
        ) : (
          filteredJobs.map((job) => (
            <div className="browsejobs-card" key={job.jobId}>
              <h3 className="browsejobs-title">{job.title}</h3>
              <div className="browsejobs-info">
                <p><FaMapMarkerAlt className="fa-map-marker-alt" /> {job.location}</p>
                <p><FaClock className="fa-clock" /> {job.jobType?.replace("_", " ")}</p>
                <p><FaUserTie className="fa-user-tie" /> {job.jobExperience}</p>
              </div>
              <div className="browsejobs-actions">
                <button className="browsejobs-btn apply" onClick={() => handleApply(job.jobId)}>
                  <FaPaperPlane /> Job details
                </button>
                <button
                  className={`browsejobs-btn save ${savedJobIds.includes(job.jobId) ? "disabled" : ""}`}
                  onClick={() => handleSave(job.jobId)}
                  disabled={savedJobIds.includes(job.jobId)}
                >
                  {savedJobIds.includes(job.jobId) ? (
                    <>
                      <FaBookmark /> Saved
                    </>
                  ) : (
                    <>
                      <FaRegBookmark /> Save
                    </>
                  )}
                </button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default BrowseJobsSection;
