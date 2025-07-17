import React, { useEffect, useState } from "react";
import "../../styles/EmployerProfileSection.css";
import {
  getEmployerByUserId,
  updateEmployerProfile
} from "../../api/employerApi";

const EmployerProfileSection = () => {
  const [formData, setFormData] = useState({
    name: "",
    position: "",
    workEmail: "",
    linkedinUrl: "",
    companyWebsite: "",
    companyBio: "",
    companyName: "",
    location: ""
  });

  const [isEditing, setIsEditing] = useState(true);
  const [loading, setLoading] = useState(true);
  const [employerId, setEmployerId] = useState(null);

  const userId = localStorage.getItem("currentUserId");
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!userId || !token) {
      alert("User not authenticated");
      return;
    }

    getEmployerByUserId(userId, token)
      .then((res) => {
        const data = res.data;
        console.log("Fetched employer data:", data);
        if (data) {
          setFormData(data);
          setEmployerId(data.employeeId);
          setIsEditing(false);
        } else {
          setIsEditing(true);
        }
        setLoading(false);
      })
      .catch((err) => {
        console.warn("No employer profile found:", err.message);
        setIsEditing(true);
        setLoading(false);
      });
  }, [userId, token]);

  const handleChange = (e) => {
    if (!isEditing) return;
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSave = () => {
    const payload = {
      ...formData,
      user: { id: parseInt(userId) }
    };

    updateEmployerProfile(employerId, payload, token)
      .then(() => {
        alert("Profile updated successfully!");
        setIsEditing(false);
      })
      .catch((err) => {
        console.error("Failed to save employer profile:", err.response?.data || err.message);
        alert("Failed to save profile. Try again.");
      });
  };

  if (loading) return <p>Loading profile...</p>;

  return (
    <div className="profile-form-wrapper">
      <form className="profile-form">
        <h2>Employer Profile</h2>

        <fieldset>
          <legend>Company Information</legend>
          <input
            name="companyName"
            value={formData.companyName}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Company Name"
          />
          <input
            name="location"
            value={formData.location}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Location"
          />
          <input
            name="companyWebsite"
            value={formData.companyWebsite}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Company Website"
          />
          <textarea
            name="companyBio"
            value={formData.companyBio}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Short company description"
          />
        </fieldset>

        <fieldset>
          <legend>HR Contact</legend>
          <input
            name="name"
            value={formData.name}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="HR Name"
          />
          <input
            name="position"
            value={formData.position}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Position"
          />
          <input
            name="workEmail"
            value={formData.workEmail}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Work Email"
          />
          <input
            name="linkedinUrl"
            value={formData.linkedinUrl}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="LinkedIn URL"
          />
        </fieldset>

        <div className="button-row">
          {!isEditing ? (
            <button type="button" className="btn-filled" onClick={() => setIsEditing(true)}>
              Edit
            </button>
          ) : (
            <button type="button" className="btn-filled" onClick={handleSave}>
              Save
            </button>
          )}
        </div>
      </form>
    </div>
  );
};

export default EmployerProfileSection;
