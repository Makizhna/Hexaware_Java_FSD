// src/api/jobSeekerApi.js
import axios from "axios";

const BASE_URL = "http://localhost:8081/api/jobseeker";

export const getJobSeekerByUserId = async (userId, token) => {
  return await axios.get(`http://localhost:8081/api/jobseeker/by-user/${userId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};


export const getJobSeekerById = async (seekerId, token) => {
  return await axios.get(`${BASE_URL}/${seekerId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const createJobSeekerProfile = async (data, token) => {
  return await axios.post(BASE_URL, data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const updateJobSeekerProfile = async (id, data, token) => {
  return await axios.put(`${BASE_URL}/${id}`, data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
