import React, { useEffect, useState } from "react";
import "../../styles/ProfileSection.css";
import {
  getJobSeekerByUserId,
  createJobSeekerProfile,
  updateJobSeekerProfile,
} from "../../api/jobSeekerApi";
import {
  uploadResume,
  getResumeBySeekerId,
  deleteResume,
} from "../../api/resumeApi";

const ProfileSection = () => {
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

  const [isEditing, setIsEditing] = useState(true);
  const [loading, setLoading] = useState(true);
  const [jobSeekerId, setJobSeekerId] = useState(null);
  const [resume, setResume] = useState(null);
  const [selectedFile, setSelectedFile] = useState(null);

  const userId = localStorage.getItem("currentUserId");
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!userId || !token) {
      alert("User not authenticated.");
      return;
    }

    getJobSeekerByUserId(userId, token)
      .then((res) => {
        const data = res.data;
        if (data) {
          setFormData(data);
          setJobSeekerId(data.seekerId);
          localStorage.setItem("jobSeekerId", data.seekerId);

          // Fetch resume if profile exists
          getResumeBySeekerId(data.seekerId, token)
            .then((res) => setResume(res.data))
            .catch(() => setResume(null));
        }
      })
      .catch((err) => {
        console.warn("No profile found:", err.message);
        setIsEditing(true);
      })
      .finally(() => setLoading(false));
  }, [userId, token]);

  const handleChange = (e) => {
    if (!isEditing) return;
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSave = () => {
    if (!userId || !token) return;

    const payload = {
      ...formData,
      user: { id: parseInt(userId) },
    };

    const apiCall = jobSeekerId
      ? updateJobSeekerProfile(jobSeekerId, payload, token)
      : createJobSeekerProfile(payload, token);

    apiCall
      .then(() => {
        alert("Profile saved successfully!");
        setIsEditing(false);
      })
      .catch((err) => {
        console.error("Failed to save profile:", err.response?.data || err.message);
        alert("Failed to save profile. Try again.");
      });
  };

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const handleUploadResume = () => {
  if (!selectedFile || !jobSeekerId) {
    alert("Please select a file.");
    return;
  }

  uploadResume(selectedFile, jobSeekerId, token) 
    .then((res) => {
      alert("Resume uploaded successfully!");
      setResume(res);
      setSelectedFile(null);
    })
    .catch(() => alert("Resume upload failed"));
};

  const handleDeleteResume = () => {
    if (!jobSeekerId) return;

    deleteResume(jobSeekerId, token)
      .then(() => {
        alert("Resume deleted");
        setResume(null);
        setSelectedFile(null);
      })
      .catch(() => alert("Failed to delete resume"));
  };

  if (loading) return <p>Loading your profile...</p>;

  return (
    <div className="profile-form-wrapper">
      <form className="profile-form">
        <h2>Job Seeker Profile</h2>

        <fieldset>
          <legend>Personal Information</legend>
          <input
            name="name"
            value={formData.name}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Name"
          />
          <input
            name="dob"
            type="date"
            value={formData.dob}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="DOB"
          />
          <input
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Phone"
          />
          <input
            name="location"
            value={formData.location}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Location"
          />
        </fieldset>

        <fieldset>
          <legend>Professional Background</legend>
          <input
            name="education"
            value={formData.education}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Education"
          />
          <input
            name="skills"
            value={formData.skills}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Skills"
          />
          <input
            name="experience"
            value={formData.experience}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="Experience"
          />
        </fieldset>

        <fieldset>
          <legend>Social Profiles</legend>
          <input
            name="linkedinUrl"
            value={formData.linkedinUrl}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="LinkedIn URL"
          />
          <input
            name="githubUrl"
            value={formData.githubUrl}
            onChange={handleChange}
            disabled={!isEditing}
            placeholder="GitHub URL"
          />
        </fieldset>

        <fieldset>
          <legend>Resume</legend>
          {resume ? (
  <div>
    <p>
      <strong>Uploaded:</strong>{" "}
      <a
        href={
          resume.fileUrl.startsWith("http")
            ? resume.fileUrl
            : `http://localhost:8081/${resume.fileUrl}`
        }
        target="_blank"
        rel="noreferrer"
      >
        {resume.fileName}
      </a>
    </p>
    {isEditing && (
      <button
        type="button"
        onClick={handleDeleteResume}
        className="btn-danger"
      >
        Delete Resume
      </button>
    )}
  </div>
) : (
  <p>No resume uploaded yet.</p>
)}


          {isEditing && (
            <div>
              <input type="file" accept=".pdf,.doc,.docx" onChange={handleFileChange} />
              <button type="button" onClick={handleUploadResume} className="btn-filled">
                Upload Resume
              </button>
            </div>
          )}
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

export default ProfileSection;
