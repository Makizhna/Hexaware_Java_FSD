import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createEmployerProfile } from "../../api/employerApi";
import "../../styles/EmployerProfileSection.css";

const EmployerCreateProfileForm = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    position: "",
    workEmail: "",
    linkedinUrl: "",
    companyName: "",
    location: "",
    companyWebsite: "",
    companyBio: ""
  });

  const userId = localStorage.getItem("currentUserId");
  const token = localStorage.getItem("token");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      ...formData,
      user: { id: parseInt(userId) }
    };

    try {
      await createEmployerProfile(payload, token);
      alert("Profile saved!");
      navigate("/employer/dashboard");
    } catch (err) {
      console.error("Failed to save profile:", err.response?.data || err.message);
      alert("Error saving profile");
    }
  };

  return (
    <div className="profile-form-wrapper">
      <form className="profile-form" onSubmit={handleSubmit}>
        <h2>Set Up Employer Profile</h2>

        <fieldset>
          <legend>Company Info</legend>
          <input name="companyName" onChange={handleChange} placeholder="Company Name" required />
          <input name="location" onChange={handleChange} placeholder="Location" required />
          <input name="companyWebsite" onChange={handleChange} placeholder="Company Website (optional)" />
          <textarea name="companyBio" onChange={handleChange} placeholder="About the company" />
        </fieldset>

        <fieldset>
          <legend>HR Details</legend>
          <input name="name" onChange={handleChange} placeholder="HR Name" required />
          <input name="position" onChange={handleChange} placeholder="Position" required />
          <input name="workEmail" onChange={handleChange} placeholder="Work Email" required />
          <input name="linkedinUrl" onChange={handleChange} placeholder="LinkedIn URL" />
        </fieldset>

        <button type="submit" className="btn-filled">Save & Proceed</button>
      </form>
    </div>
  );
};

export default EmployerCreateProfileForm;