import axios from "axios";

const BASE_URL = "http://localhost:8081/api/jobs";
const token = localStorage.getItem("token");

const getAuthHeaders = () => ({
  headers: {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  },
});

export const postJob = async (jobData, token) => {
  return await axios.post("http://localhost:8081/api/jobs", jobData, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
};

// READ (get by employer)
export const getJobsByEmployer = async (employerId) => {
  return await axios.get(`${BASE_URL}/employer/${employerId}`, getAuthHeaders());
};

//  READ (get by job ID) – add this function
export const getJobById = async (jobId) => {
  return await axios.get(`${BASE_URL}/${jobId}`, getAuthHeaders());
};

//  UPDATE
export const updateJob = async (jobId, jobData) => {
  return await axios.put(`${BASE_URL}/${jobId}`, jobData, getAuthHeaders());
};

//  DELETE
export const deleteJob = async (jobId) => {
  return await axios.delete(`${BASE_URL}/${jobId}`, getAuthHeaders());
};

export const getAllJobs = async () => {
  return await axios.get("http://localhost:8081/api/jobs");
};


export const applyToJob = async (applicationData, token) => {
  return await axios.post(
    "http://localhost:8081/api/applications",
    applicationData,
    {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    }
  );
};

