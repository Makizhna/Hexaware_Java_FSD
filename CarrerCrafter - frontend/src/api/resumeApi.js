// src/api/resumeApi.js
import axios from "axios";

/*export const uploadResume = async (file, jobSeekerId, token) => {
  const formData = new FormData();
  formData.append("file", file);
  formData.append("jobSeekerId", jobSeekerId);

 return axios.post("http://localhost:8081/api/jobseeker-resume/upload", formData, {
  headers: {
    Authorization: `Bearer ${token}`,
    "Content-Type": "multipart/form-data",
  },
});
};*/


// resumeApi.js
export const uploadResume = (file, jobSeekerId, token) => {
  const formData = new FormData();
  formData.append("file", file); // file part
  formData.append("jobSeekerId", jobSeekerId); // param part

  return fetch(`http://localhost:8081/api/jobseeker-resume/upload`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
    },
    body: formData, // Do NOT set Content-Type manually
  }).then((res) => {
    if (!res.ok) throw new Error("Upload failed");
    return res.json();
  });
};



const BASE_URL = "http://localhost:8081/api/jobseeker-resume";


export const deleteResume = async (jobSeekerId, token) => {
  return axios.delete(`${BASE_URL}/${jobSeekerId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const getResumeBySeekerId = (seekerId, token) => {
  return axios.get(`http://localhost:8081/api/jobseeker-resume/${seekerId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};


const getResumeLink = (url) =>
  url?.startsWith("http") ? url : `http://localhost:8081${url}`;





