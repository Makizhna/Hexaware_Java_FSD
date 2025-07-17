import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8081/api/auth",
});

// Login request
export const loginUser = (credentials) => API.post("/login", credentials);
