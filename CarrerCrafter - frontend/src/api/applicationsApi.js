import axios from "axios";

const BASE_URL = "http://localhost:8081/api/applications";

export const getApplicationsByEmployerId = (employerId, token) =>
  axios.get(`${BASE_URL}/employer/${employerId}`, {
    headers: { Authorization: `Bearer ${token}` },
  });

  export const getApplicationsByJobSeekerId = (seekerId, token) =>
  axios.get(`${BASE_URL}/jobseeker/${seekerId}`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });

  export const updateApplicationStatus = (applicationId, status, token) =>
  axios.put(`${BASE_URL}/${applicationId}/status?status=${status}`, {}, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });


  export const deleteApplication = (applicationId, token) =>
  axios.delete(`http://localhost:8081/api/applications/${applicationId}`, {
    headers: { Authorization: `Bearer ${token}` },
  });


  export const getAllApplications = (token) =>
  axios.get("http://localhost:8081/api/applications", {
    headers: { Authorization: `Bearer ${token}` },
  });
