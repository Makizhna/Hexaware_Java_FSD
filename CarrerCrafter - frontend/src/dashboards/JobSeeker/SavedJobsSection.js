import React, { useEffect, useState } from "react";
import { getSavedJobsBySeekerId, deleteSavedJob } from "../../api/savedJobsApi";
import { getJobSeekerByUserId } from "../../api/jobSeekerApi";
import { useNavigate } from "react-router-dom";
import { FaPaperPlane } from "react-icons/fa";
import "../../styles/SavedJobsSection.css"; 
 


const SavedJobsPage = () => {
  const [savedJobs, setSavedJobs] = useState([]);
  const [seekerId, setSeekerId] = useState(null);
  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("currentUserId");
  const navigate = useNavigate();

  useEffect(() => {
  if (userId && token) {
    getJobSeekerByUserId(userId, token)
      .then((res) => {
        const fetchedSeekerId = res.data.seekerId;
        console.log(" Retrieved seeker ID:", fetchedSeekerId);
        setSeekerId(fetchedSeekerId);
        return getSavedJobsBySeekerId(fetchedSeekerId, token);
      })
      .then((res) => {
        console.log(" Saved jobs fetched:", res.data);
        setSavedJobs(res.data);
      })
      .catch((err) => {
        console.error(" Error fetching saved jobs:", err);
      });
  }
}, [userId, token]);


  const handleDelete = async (id) => {
    try {
      await deleteSavedJob(id, token);
      setSavedJobs(savedJobs.filter((job) => job.id !== id));
    } catch (err) {
      console.error("Failed to delete saved job:", err);
    }
  };

  return (
    <div className="container mt-5">
      <h3 className="mb-4">📌 My Saved Jobs</h3>

      {savedJobs.length === 0 ? (
        <p>No saved jobs found.</p>
      ) : (
        savedJobs.map((saved) => (
          <div key={saved.id} className="card mb-3 shadow-sm rounded-3 p-3">
           <div className="mb-2">
             <h5 className="text-primary">{saved.jobTitle || "Untitled Job"}</h5>
                <small className="text-muted">Job ID: {saved.jobId}</small>
            </div>
            

            <div className="mb-2 d-flex align-items-center">
              <span className="me-2">📅</span>
              <span className="fw-semibold">Saved on:</span>&nbsp;
              <span>{new Date(saved.savedDate).toLocaleDateString()}</span>
            </div>

            <div className="mb-2 d-flex align-items-center">
              <span className="me-2">🏢</span>
              <span className="fw-semibold">Company:</span>&nbsp;
              <span>{saved.companyName}</span>
            </div>

            <div className="mb-3 d-flex align-items-center">
              <span className="me-2">⏰</span>
              <span className="fw-semibold">Deadline:</span>&nbsp;
              <span className="text-danger">
                {new Date(saved.applicationDeadline).toLocaleDateString()}
              </span>
            </div>

            <div className="d-flex justify-content-between">
             <button className="saved-btn-filled" onClick={() => navigate(`/jobs/${saved.jobId}`)}>
                  <FaPaperPlane className="me-2" /> Apply Now
            </button>

              <button
                className="btn btn-sm btn-outline-danger px-3"
                onClick={() => handleDelete(saved.id)}
              >
                Remove
              </button>
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default SavedJobsPage;
