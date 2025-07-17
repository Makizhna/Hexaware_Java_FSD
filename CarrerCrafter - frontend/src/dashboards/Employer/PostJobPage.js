import React, { useEffect, useState } from "react";
import "../../styles/PostJobPage.css";
import { postJob } from "../../api/jobApi";
import { getEmployerByUserId } from "../../api/employerApi";

const PostJobPage = () => {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    location: "",
    locationType: "ON_SITE",
    jobType: "FULL_TIME",
    jobExperience: "",
    qualifications: "",
    skillsRequired: "",
    salary: "",
    postedDate: new Date().toISOString().split("T")[0],
    applicationDeadline: "",
    applicationEmail: "",
    applicationInstructions: "" // New Field
  });

  const [employerId, setEmployerId] = useState(null);
  const userId = localStorage.getItem("currentUserId");
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!userId || !token) return;

    getEmployerByUserId(userId, token)
      .then((res) => {
        if (res.data && res.data.employeeId) {
          setEmployerId(res.data.employeeId);
        } else {
          alert("Employer profile not found. Please complete your profile.");
        }
      })
      .catch((err) => {
        console.error("Failed to fetch employer:", err.response?.data || err.message);
        alert("Please complete your employer profile first.");
      });
  }, [userId, token]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!employerId || employerId === 0) {
      alert("Invalid employer ID. Please complete your profile first.");
      return;
    }

    const payload = {
      ...formData,
      skillsRequired: formData.skillsRequired.split(",").map((s) => s.trim()),
      employer: { employeeId: employerId },
      active: true,
    };

    try {
      await postJob(payload, token);
      alert("Job posted successfully!");
      setFormData({
        ...formData,
        title: "",
        description: "",
        location: "",
        jobExperience: "",
        qualifications: "",
        skillsRequired: "",
        salary: "",
        applicationDeadline: "",
        applicationEmail: "",
        applicationInstructions: ""
      });
    } catch (err) {
      console.error("Error posting job:", err.response?.data || err.message);
      alert("Failed to post job.");
    }
  };

  return (
    <div className="post-job-container">
      <h2>Post a New Job</h2>
      <form onSubmit={handleSubmit} className="post-job-form">
        <input name="title" placeholder="Job Title" value={formData.title} onChange={handleChange} required />
        <textarea name="description" placeholder="Job Description" value={formData.description} onChange={handleChange} required />
        <input name="location" placeholder="Location (e.g. Bangalore)" value={formData.location} onChange={handleChange} required />

        <select name="locationType" value={formData.locationType} onChange={handleChange}>
          <option value="ON_SITE">On Site</option>
          <option value="REMOTE">Remote</option>
          <option value="HYBRID">Hybrid</option>
        </select>

        <select name="jobType" value={formData.jobType} onChange={handleChange}>
          <option value="FULL_TIME">Full Time</option>
          <option value="PART_TIME">Part Time</option>
          <option value="INTERNSHIP">Internship</option>
        </select>

        <input name="jobExperience" placeholder="Experience (e.g. 2+ years)" value={formData.jobExperience} onChange={handleChange} required />
        <input name="qualifications" placeholder="Qualifications (e.g. B.E, MBA)" value={formData.qualifications} onChange={handleChange} required />
        <input name="skillsRequired" placeholder="Skills Required (comma separated)" value={formData.skillsRequired} onChange={handleChange} required />
        <input name="salary" placeholder="Salary (e.g. ₹6L–₹10L)" value={formData.salary} onChange={handleChange} required />

        <input type="date" name="postedDate" value={formData.postedDate} disabled />
        <input type="date" name="applicationDeadline" value={formData.applicationDeadline} onChange={handleChange} required />
        <input type="email" name="applicationEmail" placeholder="Application Email" value={formData.applicationEmail} onChange={handleChange} required />

        <textarea
          name="applicationInstructions"
          placeholder="Optional: Add custom instructions like a link, or email to send resume"
          value={formData.applicationInstructions}
          onChange={handleChange}
        />

        <button type="submit" className="btn-filled">Post Job</button>
      </form>
    </div>
  );
};

export default PostJobPage;
