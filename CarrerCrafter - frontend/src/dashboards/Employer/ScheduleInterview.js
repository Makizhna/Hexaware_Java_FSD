import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "../../styles/ScheduleInterview.css";

const ScheduleInterview = () => {
  const { applicationId } = useParams();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    date: "",
    time: "",
    mode: "VIRTUAL_INTERVIEW",
    status: "SCHEDULED",
    meetingLink: ""
  });

  const [applicationDetails, setApplicationDetails] = useState(null);
  const [jobSeekerName, setJobSeekerName] = useState("");
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchApplicationDetails = async () => {
      try {
        const res = await axios.get(`http://localhost:8081/api/applications/${applicationId}`, {
          headers: { Authorization: `Bearer ${token}` }
        });
        setApplicationDetails(res.data);

        setJobSeekerName(res.data.jobSeekerName); 

      } catch (err) {
        console.error("Failed to fetch application or seeker", err);
      }
    };

    if (token) fetchApplicationDetails();
  }, [applicationId, token]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const getPlaceholder = () => {
    if (formData.mode === "IN_PERSON_INTERVIEW") return "Enter venue address (e.g., ABC Towers)";
    if (formData.mode === "TELEPHONIC_INTERVIEW") return "Enter phone number or instructions";
    return "Enter Zoom/Meet/Teams link";
  };

  const getLabel = () => {
    if (formData.mode === "IN_PERSON_INTERVIEW") return "Venue Address";
    if (formData.mode === "TELEPHONIC_INTERVIEW") return "Phone Instructions";
    return "Meeting Link";
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!token) return alert("Unauthorized");

    const { date, time, meetingLink } = formData;

    if (!date || !time || !meetingLink) {
      return alert("All fields are required.");
    }

    const interviewDate = `${date}T${time}`;

    try {
      await axios.post(
        `http://localhost:8081/api/interview`,
        {
          applicationId: parseInt(applicationId),
          interviewDate,
          mode: formData.mode,
          status: formData.status,
          meetingLink
        },
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );
      alert(" Interview scheduled successfully!");
      navigate("/employer/dashboard");
    } catch (err) {
      console.error("Error scheduling interview", err);
      alert(" Failed to schedule interview.");
    }
  };

  return (
    <div className="container mt-4 schedule-interview-form">
      <h3 className="mb-4">🗓️ Schedule Interview</h3>

      {applicationDetails && (
        <div className="mb-4 p-3 border rounded bg-light">
          <h5>Job: <strong>{applicationDetails.jobTitle}</strong></h5>
          <p><strong>Candidate Name:</strong> {jobSeekerName}</p>
          <p><strong>Candidate ID:</strong> {applicationDetails.jobSeekerId}</p>
          <p><strong>Application Status:</strong> {applicationDetails.status}</p>
        </div>
      )}

      <form onSubmit={handleSubmit} className="form-card p-4 shadow-sm">
        <div className="mb-3">
          <label>Date</label>
          <input
            type="date"
            name="date"
            className="form-control"
            required
            value={formData.date}
            onChange={handleChange}
          />
        </div>

        <div className="mb-3">
          <label>Time</label>
          <input
            type="time"
            name="time"
            className="form-control"
            required
            value={formData.time}
            onChange={handleChange}
          />
        </div>

        <div className="mb-3">
          <label>Mode</label>
          <select
            name="mode"
            className="form-select"
            value={formData.mode}
            onChange={handleChange}
          >
            <option value="VIRTUAL_INTERVIEW">Online</option>
            <option value="IN_PERSON_INTERVIEW">Offline</option>
            <option value="TELEPHONIC_INTERVIEW">Phone Call</option>
          </select>
        </div>

        <div className="mb-3">
          <label>{getLabel()}</label>
          <input
            type="text"
            name="meetingLink"
            className="form-control"
            placeholder={getPlaceholder()}
            value={formData.meetingLink}
            onChange={handleChange}
            required
          />
        </div>

        <button className="btn btn-primary" type="submit">
          Schedule Interview
        </button>
      </form>
    </div>
  );
};

export default ScheduleInterview;
