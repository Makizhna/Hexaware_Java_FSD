import React, { useEffect, useState } from "react";
import dayjs from "dayjs";
import "../../styles/Interview.css";
import {
  getInterviewsByJobSeeker,
  cancelInterview,
  requestReschedule
} from "../../api/interviewApi";

const Interviews = () => {
  const [interviews, setInterviews] = useState([]);
  const [selectedInterview, setSelectedInterview] = useState(null);
  const [loading, setLoading] = useState(true);
  const [rescheduleMessage, setRescheduleMessage] = useState("");
  const [cancelReason, setCancelReason] = useState("");
  const [showRescheduleForm, setShowRescheduleForm] = useState(false);
  const [showCancelForm, setShowCancelForm] = useState(false);

  const token = localStorage.getItem("token");
  const jobSeekerId = localStorage.getItem("jobSeekerId");

  useEffect(() => {
    if (token && jobSeekerId) {
      getInterviewsByJobSeeker(jobSeekerId, token)
        .then((res) => {
  const filtered = res.data.filter(
    (interview) => interview.applicationStatus !== "REJECTED"
  );
  setInterviews(filtered);
  setLoading(false);
})

        .catch((err) => {
          console.error("Failed to fetch interviews:", err);
          setLoading(false);
        });
    }
  }, [token, jobSeekerId]);

  const handleViewDetails = (interview) => {
    setSelectedInterview(interview);
    setShowRescheduleForm(false);
    setShowCancelForm(false);
    setRescheduleMessage("");
    setCancelReason("");
  };

  const closeModal = () => {
    setSelectedInterview(null);
  };

  const handleReschedule = async () => {
    if (!rescheduleMessage.trim()) return alert("Enter reschedule reason.");
    try {
      await requestReschedule(selectedInterview.id, rescheduleMessage, token);
      alert("Reschedule request sent.");
      setShowRescheduleForm(false);
      closeModal();
    } catch (err) {
      console.error("Reschedule failed", err);
      alert("Failed to request reschedule.");
    }
  };

  const handleCancelReason = async () => {
    if (!cancelReason.trim()) return alert("Enter cancel reason.");
    try {
      await cancelInterview(selectedInterview.id, cancelReason, token);
      setInterviews(interviews.filter(i => i.id !== selectedInterview.id));
      alert("Interview cancelled.");
      closeModal();
    } catch (err) {
      console.error("Cancel failed", err);
      alert("Failed to cancel interview.");
    }
  };

  const isToday = (date) => dayjs(date).isSame(dayjs(), "day");
  const isTomorrow = (date) => dayjs(date).isSame(dayjs().add(1, "day"), "day");
  const isWithin30Minutes = (date) => dayjs(date).diff(dayjs(), "minute") <= 30;

  return (
    <div className="interview-section">
      <h2>Scheduled Interviews</h2>

      {loading ? (
        <p>🔄 Loading interviews...</p>
      ) : interviews.length === 0 ? (
        <p>No interviews scheduled yet.</p>
      ) : (
        <table className="interview-table">
          <thead>
            <tr>
              <th>Job Title</th>
              <th>Company</th>
              <th>Date</th>
              <th>Time</th>
              <th>Mode</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {interviews.map((item) => {
              const dateObj = dayjs(item.interviewDate);
              const highlightClass = isToday(item.interviewDate)
                ? "highlight-today"
                : isTomorrow(item.interviewDate)
                ? "highlight-tomorrow"
                : "";

              return (
                <tr key={item.id} className={highlightClass}>
                  <td>{item.jobTitle}</td>
                  <td>{item.companyName}</td>
                  <td>{dateObj.format("YYYY-MM-DD")}</td>
                  <td>{dateObj.format("hh:mm A")}</td>
                  <td>{item.mode.replace("_", " ")}</td>
                  <td>
                    <span className={`status-badge ${item.status.toLowerCase()}`}>
                      {item.status}
                    </span>
                  </td>
                  <td>
                    <button className="view-btn" onClick={() => handleViewDetails(item)}>
                      View
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}

      {selectedInterview && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h3>Interview Details</h3>
            <p><strong>Job Title:</strong> {selectedInterview.jobTitle}</p>
            <p><strong>Company:</strong> {selectedInterview.companyName}</p>
            <p><strong>Date:</strong> {dayjs(selectedInterview.interviewDate).format("YYYY-MM-DD")}</p>
            <p><strong>Time:</strong> {dayjs(selectedInterview.interviewDate).format("hh:mm A")}</p>
            <p><strong>Mode:</strong> {selectedInterview.mode.replace("_", " ")}</p>
            <p><strong>Status:</strong> {selectedInterview.status}</p>
            <p><strong>Link / Address:</strong> {selectedInterview.meetingLink}</p>
            {selectedInterview.rescheduleRequest && (
              <p><strong>Reschedule Request:</strong> {selectedInterview.rescheduleRequest}</p>
            )}
            {selectedInterview.cancelReason && (
              <p><strong>Cancel Reason:</strong> {selectedInterview.cancelReason}</p>
            )}

            <div className="d-flex flex-column gap-2 mt-3">
             {!isWithin30Minutes(selectedInterview.interviewDate) && (
  <>
    {selectedInterview.status === "RESCHEDULE_REJECTED" ? (
  <p className="text-danger fw-semibold">
    ❌ Your reschedule request was rejected. You can't send another.
  </p>
) : selectedInterview.status === "RESCHEDULE_REQUESTED" ? (
  <p className="text-warning fw-semibold">
    ⏳ Reschedule request is already sent. Awaiting employer response.
  </p>
) : (
  <button className="btn btn-warning" onClick={() => setShowRescheduleForm(true)}>
    Request Reschedule
  </button>
)}

    <button className="btn btn-danger" onClick={() => setShowCancelForm(true)}>
      Cancel Interview
    </button>
  </>
)}

              <button className="btn btn-secondary" onClick={closeModal}>Close</button>

              {showRescheduleForm && (
                <div className="mt-2">
                  <textarea
                    placeholder="Enter your reschedule reason..."
                    className="form-control"
                    value={rescheduleMessage}
                    onChange={(e) => setRescheduleMessage(e.target.value)}
                  />
                  <button className="btn btn-success mt-2" onClick={handleReschedule}>
                    Submit Reschedule
                  </button>
                </div>
              )}

              {showCancelForm && (
                <div className="mt-2">
                  <textarea
                    placeholder="Enter your reason for cancellation..."
                    className="form-control"
                    value={cancelReason}
                    onChange={(e) => setCancelReason(e.target.value)}
                  />
                  <button className="btn btn-danger mt-2" onClick={handleCancelReason}>
                    Confirm Cancel
                  </button>
                </div>
              )}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Interviews;