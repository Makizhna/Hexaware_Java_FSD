import axios from "axios";

const BASE_URL = "http://localhost:8081/api/interview";

export const getInterviewsByJobSeeker = (jobSeekerId, token) => {
  return axios.get(`${BASE_URL}/jobseeker/${jobSeekerId}/interviews`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};

export const createInterview = (data, token) =>
  axios.post(BASE_URL, data, {
    headers: { Authorization: `Bearer ${token}` }
  });

export const getAllInterviews = (token) =>
  axios.get(BASE_URL, {
    headers: { Authorization: `Bearer ${token}` }
  });

export const getInterviewById = (id, token) =>
  axios.get(`${BASE_URL}/${id}`, {
    headers: { Authorization: `Bearer ${token}` }
  });

export const updateInterview = (id, data, token) =>
  axios.put(`${BASE_URL}/${id}`, data, {
    headers: { Authorization: `Bearer ${token}` }
  });

export const deleteInterview = (id, token) =>
  axios.delete(`${BASE_URL}/${id}`, {
    headers: { Authorization: `Bearer ${token}` }
  });


export const cancelInterview = (id, reason, token) => {
  return axios.post(`http://localhost:8081/api/interview/${id}/cancel`, { reason }, {
    headers: { Authorization: `Bearer ${token}` },
  });
};


/*export const requestReschedule = (id, message, token) => {
  return axios.post(
    `http://localhost:8081/api/interview/${id}/reschedule-request`,
    null, 
    {
      params: { message },
      headers: { Authorization: `Bearer ${token}` },
    }
  );
};*/


export const requestReschedule = (id, message, token) => {
  return axios.post(
    `http://localhost:8081/api/interview/${id}/reschedule-request`,
    { message }, 
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  );
};



export const approveReschedule = (id, token) =>
  axios.put(`http://localhost:8081/api/interview/${id}/approve-reschedule`, {}, {
    headers: { Authorization: `Bearer ${token}` }
  });

export const rejectReschedule = (id, token) =>
  axios.put(`http://localhost:8081/api/interview/${id}/reject-reschedule`, {}, {
    headers: { Authorization: `Bearer ${token}` }
  });



export const getInterviewByApplicationId = (applicationId, token) => {
  return axios.get(`${BASE_URL}/application/${applicationId}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
