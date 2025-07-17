import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createJobSeekerProfile } from "../../api/jobSeekerApi";
import "../../styles/ProfileSection.css";


const JobSeekerCreateProfile = () => {
  const [formData, setFormData] = useState({
    name: "",
    dob: "",
    phone: "",
    location: "",
    education: "",
    skills: "",
    experience: "",
    linkedinUrl: "",
    githubUrl: "",
  });

  const [error, setError] = useState("");
  const navigate = useNavigate();

  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("currentUserId");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const payload = {
        ...formData,
        user: { id: parseInt(userId) },
      };
      await createJobSeekerProfile(payload, token);
      alert("Profile created successfully!");
      navigate("/jobseeker/dashboard");
    } catch (err) {
      setError(err.response?.data || "Something went wrong");
    }
  };

  return (
    <div className="profile-form-wrapper">
      <form className="profile-form" onSubmit={handleSubmit}>
        <h2>Complete Your Profile</h2>

        <fieldset>
          <legend>Personal Information</legend>
          <input name="name" value={formData.name} onChange={handleChange} placeholder="Name" required />
          <input name="dob" type="date" value={formData.dob} onChange={handleChange} placeholder="DOB" required />
          <input name="phone" value={formData.phone} onChange={handleChange} placeholder="Phone" required />
          <input name="location" value={formData.location} onChange={handleChange} placeholder="Location" required />
        </fieldset>

        <fieldset>
          <legend>Professional Background</legend>
          <input name="education" value={formData.education} onChange={handleChange} placeholder="Education" required />
          <input name="skills" value={formData.skills} onChange={handleChange} placeholder="Skills" required />
          <input name="experience" value={formData.experience} onChange={handleChange} placeholder="Experience" required />
        </fieldset>

        <fieldset>
          <legend>Social Profiles</legend>
          <input name="linkedinUrl" value={formData.linkedinUrl} onChange={handleChange} placeholder="LinkedIn URL" />
          <input name="githubUrl" value={formData.githubUrl} onChange={handleChange} placeholder="GitHub URL" />
        </fieldset>

        <div className="button-row">
          <button type="submit" className="btn-filled">Save Profile</button>
        </div>

        {error && <p className="error-text">{error}</p>}
      </form>
    </div>
  );
};

export default JobSeekerCreateProfile;
