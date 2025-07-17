import axios from "axios";

const BASE_URL = "http://localhost:8081/api/employer";

// Get the token from localStorage (helper)
const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  };
};

// GET employer by user ID
export const getEmployerByUserId = async (userId, token) => {
  return await axios.get(`http://localhost:8081/api/employer/by-user/${userId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

// UPDATE employer profile
export const updateEmployerProfile = async (employerId, data) => {
  return await axios.put(`http://localhost:8081/api/employer/${employerId}`, data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
      "Content-Type": "application/json",
    },
  });
};




export const createEmployerProfile = async (data, token) => {
  return await axios.post(BASE_URL, data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
