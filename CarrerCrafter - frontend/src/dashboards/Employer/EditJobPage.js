import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { updateJob, getJobById } from "../../api/jobApi";
import "../../styles/EmployerProfileSection.css"; 

const EditJobPage = () => {
  const { jobId } = useParams();
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

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
    postedDate: "",
    applicationDeadline: "",
    applicationEmail: "",
  });

  useEffect(() => {
    getJobById(jobId, token)
      .then((res) => {
        const job = res.data;
        setFormData({
          ...job,
          skillsRequired: job.skillsRequired.join(", "),
        });
      })
      .catch(() => {
        alert("Failed to load job.");
      });
  }, [jobId, token]);

  const handleChange = (e) =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    const updated = {
      ...formData,
      skillsRequired: formData.skillsRequired.split(",").map((s) => s.trim()),
    };

    try {
      await updateJob(jobId, updated, token);
      alert("Job updated Successfully!");
      navigate("/employer/dashboard");
    } catch (err) {
      alert("Failed to update job.");
    }
  };

  return (
    <div className="employer-profile">
      <form className="employer-form-section" onSubmit={handleSubmit}>
        <h2>Edit Job Posting</h2>

        <input
          name="title"
          placeholder="Job Title"
          value={formData.title}
          onChange={handleChange}
          required
        />

        <textarea
          name="description"
          placeholder="Job Description"
          value={formData.description}
          onChange={handleChange}
          required
          style={{ minHeight: "100px", resize: "vertical" }}
        />

        <input
          name="location"
          placeholder="Job Location"
          value={formData.location}
          onChange={handleChange}
          required
        />

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

        <input
          name="jobExperience"
          placeholder="Experience Required"
          value={formData.jobExperience}
          onChange={handleChange}
        />

        <input
          name="qualifications"
          placeholder="Qualifications"
          value={formData.qualifications}
          onChange={handleChange}
        />

        <input
          name="skillsRequired"
          placeholder="Skills (comma-separated)"
          value={formData.skillsRequired}
          onChange={handleChange}
        />

        <input
          name="salary"
          placeholder="Salary (e.g. ₹6L - ₹12L)"
          value={formData.salary}
          onChange={handleChange}
        />

        <input
          type="date"
          name="applicationDeadline"
          value={formData.applicationDeadline}
          onChange={handleChange}
        />

        <input
          type="email"
          name="applicationEmail"
          placeholder="Application Email"
          value={formData.applicationEmail}
          onChange={handleChange}
        />

        <div className="employer-actions">
          <button type="submit" className="btn-filled">
            Save Changes
          </button>
          <button
            type="button"
            className="btn-outline"
            onClick={() => navigate("/employer-dashboard")}
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default EditJobPage;
