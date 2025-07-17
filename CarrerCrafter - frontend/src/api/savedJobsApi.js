import axios from "axios";

const BASE_URL = "http://localhost:8081/api/savedjobs";


  
export const saveJob = (jobId, jobSeekerId, token) => {
  return axios.post(
    `${BASE_URL}`,
    {
      jobId,
      jobSeekerId
    },
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  );
};


export const getSavedJobs = (token) =>
  axios.get(BASE_URL, {
    headers: { Authorization: `Bearer ${token}` },
  });

export const deleteSavedJob = (id, token) =>
  axios.delete(`${BASE_URL}/${id}`, {
    headers: { Authorization: `Bearer ${token}` },
  });


  export const getSavedJobsBySeekerId = (seekerId, token) =>
  axios.get(`${BASE_URL}/seeker/${seekerId}`, {
    headers: { Authorization: `Bearer ${token}` },
  });

