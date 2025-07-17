

// src/pages/RescheduleInterviewPage.jsx
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import dayjs from "dayjs";
import { getInterviewById, updateInterview } from "../../api/interviewApi";

const RescheduleInterview = () => {
    const [originalData, setOriginalData] = useState({});

  const { interviewId } = useParams();
  const [formData, setFormData] = useState({
    interviewDate: "",
    mode: "ONLINE",
    meetingLink: "",
  });
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!interviewId || !token) return;
    getInterviewById(interviewId, token)
  .then((res) => {
    const { interviewDate, mode, meetingLink, applicationId } = res.data;
    setFormData({
      interviewDate: dayjs(interviewDate).format("YYYY-MM-DDTHH:mm"),
      mode,
      meetingLink: meetingLink || "",
    });
    setOriginalData({ applicationId }); 
    setLoading(false);
  })

      .catch((err) => {
        console.error("Error fetching interview details:", err);
        alert("Failed to load interview details");
      });
  }, [interviewId, token]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

 const handleSubmit = async (e) => {
  e.preventDefault();

  const payload = {
    ...formData,
    status: "RESCHEDULED",
    applicationId: originalData.applicationId, 
  };

  try {
    await updateInterview(interviewId, payload, token);
    alert("Interview rescheduled successfully.");
    navigate("/employer/dashboard");
  } catch (err) {
    console.error("Update failed:", err);
    alert("Failed to update interview.");
  }
};



  if (loading) return <p>Loading interview...</p>;

  return (
    <div className="container mt-4">
      <h3>📅 Reschedule Interview</h3>
      <form onSubmit={handleSubmit} className="mt-4">
        <div className="mb-3">
          <label>Date & Time</label>
          <input
            type="datetime-local"
            name="interviewDate"
            value={formData.interviewDate}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <div className="mb-3">
          <label>Mode</label>
          <select
            name="mode"
            value={formData.mode}
            onChange={handleChange}
            className="form-select"
            required
          >
            <option value="ONLINE">Online</option>
            <option value="OFFLINE">Offline</option>
            <option value="PHONE">Phone</option>
          </select>
        </div>
        <div className="mb-3">
          <label>Meeting Link / Address</label>
          <input
            type="text"
            name="meetingLink"
            value={formData.meetingLink}
            onChange={handleChange}
            className="form-control"
          />
        </div>

        <button className="btn btn-primary" type="submit">
          Submit Reschedule
        </button>
      </form>
    </div>
  );
};

export default RescheduleInterview;