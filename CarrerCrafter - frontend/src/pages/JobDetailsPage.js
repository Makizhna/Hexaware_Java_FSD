import React, { useEffect, useState } from "react";
import { useParams, useNavigate, Link, useLocation } from "react-router-dom";
import { getJobById, applyToJob } from "../api/jobApi";
import { getJobSeekerByUserId } from "../api/jobSeekerApi";
import { getResumeBySeekerId } from "../api/resumeApi";
import { saveJob, getSavedJobsBySeekerId } from "../api/savedJobsApi";
import { toast, ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import { getApplicationsByJobSeekerId } from "../api/applicationsApi";
import { Briefcase, Globe, Linkedin, MapPin, Calendar, DollarSign, User, Clock } from 'lucide-react';
import "../styles/JobDetailsPage.css";



const JobDetailsPage = () => {
  
  const { jobId } = useParams();
  const navigate = useNavigate();
  

  const [job, setJob] = useState(null);
  const [jobSeekerId, setJobSeekerId] = useState(null);
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [coverLetter, setCoverLetter] = useState("");
  const [resumeFileName, setResumeFileName] = useState("");
  const [appliedJobIds, setAppliedJobIds] = useState([]);
  const [savedJobIds, setSavedJobIds] = useState([]);

  const location = useLocation();
  const fromPage = location.state?.from;

  


  const [answers, setAnswers] = useState({
    ugPercentage: "",
    pgPercentage: "",
    gradYear: "",
    priorExperience: "",
    recentProject: "",
    relocation: "",
    noticePeriod: "",
    portfolio: "",
    workAuth: "",
    nightShift: ""
  });

  const userId = localStorage.getItem("currentUserId");
  const token = localStorage.getItem("token");
  const [formErrors, setFormErrors] = useState({});

 useEffect(() => {
  if (!jobId) return;

  // Fetch job details
  getJobById(jobId)
    .then((res) => setJob(res.data))
    .catch(() => alert("Failed to load job details"));

  if (userId && token) {
    getJobSeekerByUserId(userId, token)
      .then((res) => {
        const actualSeekerId = res.data?.seekerId;
        if (!actualSeekerId) return;

        setJobSeekerId(actualSeekerId);

        // Fetch resume
        getResumeBySeekerId(actualSeekerId, token)
          .then((resumeRes) => {
            if (resumeRes?.data?.fileName) {
              setResumeFileName(resumeRes.data.fileName);
            }
          })
          .catch(() => console.warn("Resume not found"));

        // Fetch saved jobs
        getSavedJobsBySeekerId(actualSeekerId, token)
          .then((savedRes) => {
            const savedIds = savedRes.data.map(j => j.job?.jobId);
            setSavedJobIds(savedIds || []);
          })
          .catch(() => {
            console.warn("Failed to fetch saved jobs");
            setSavedJobIds([]);
          });
      })
      .catch(() => {
        setJobSeekerId(null);
      });
  }
}, [jobId, userId, token]);



  

 const checkIfAlreadyApplied = async () => {
  try {
    const res = await getApplicationsByJobSeekerId(jobSeekerId, token);
    const alreadyApplied = res.data.some(
      (app) => app.job?.jobId === job.jobId
    );

    if (alreadyApplied) {
      toast.warn("⚠ You have already applied for this job.");
      return; //  Don't show modal
    }

    setShowConfirmModal(true); //  Only open if not already applied
  } catch (error) {
    console.error("Error checking applications:", error);
    toast.error("Something went wrong while checking applications.");
  }
};



const handleApply = async () => {
  const errors = {};

  // Validate
  if (!answers.ugPercentage) errors.ugPercentage = "UG percentage is required";
  if (!answers.gradYear) errors.gradYear = "Graduation year is required";
  if (!answers.priorExperience) errors.priorExperience = "Experience is required";
  if (!answers.relocation) errors.relocation = "Relocation preference is required";
  if (!answers.noticePeriod) errors.noticePeriod = "Notice period is required";
  if (!answers.workAuth) errors.workAuth = "Work authorization is required";
  if (!answers.nightShift) errors.nightShift = "Night shift preference is required";
  if (answers.priorExperience === "Yes" && !answers.recentProject)
    errors.recentProject = "Project description is required";

  setFormErrors(errors);
  if (Object.keys(errors).length > 0) {
    toast.error("❗Please fill all required fields properly.");
    return;
  }

  const today = new Date().toISOString().split("T")[0];
  if (job.applicationDeadline < today) {
    toast.warning("Application deadline is over.");
    return;
  }

  try {
    const resumeRes = await getResumeBySeekerId(jobSeekerId, token);
    const resume = resumeRes?.data;
    if (!resume?.resumeId) {
      toast.error("📄 Upload a resume before applying.");
      return;
    }

    const applicationDto = {
      jobId: job.jobId,
      jobSeekerId,
      resumeId: resume.resumeId,
      coverLetter,
      answers
    };

    await applyToJob(applicationDto, token);
    toast.success(" Application submitted!");
    setShowConfirmModal(false);
    setTimeout(() => {
  navigate("/jobseeker/dashboard");
}, 2500); // wait 2.5 seconds
  
  } catch (error) {
    console.error("Error applying to job:", error);
    toast.error(" Failed to submit application.");
  }
};

   

  const handleSave = async () => {
    if (!job || !jobSeekerId || !token) return;

    if (savedJobIds.includes(job.jobId)) {
    toast.info("✅ Job already saved.");
    return;
  }
    try {
      const dto = { jobId: job.jobId, jobSeekerId };
      await saveJob(job.jobId, jobSeekerId, token);
      alert("💾 Job saved!");
      setSavedJobIds(prev => [...prev, job.jobId]);
      navigate("/jobs");
    } catch(error) {
      const backendMsg =
  error?.response?.data?.message || error?.response?.data?.error || "❌ Failed to save job.";
toast.error(backendMsg);

    }
  };

  if (!job) return <div className="text-center mt-5">Loading job details...</div>;
  const today = new Date().toISOString().split("T")[0];
  const isDeadlineOver = job.applicationDeadline < today;

  return (
    <div className="container job-details-container py-5 fade-in">
      {/* Breadcrumb */}
      <nav className="breadcrumb mb-4">
        <Link to="/" className="breadcrumb-item">Home</Link>
        <Link to="/jobs" className="breadcrumb-item">Jobs</Link>
        <span className="breadcrumb-item active">{job.title}</span>
      </nav>

      {/* Company Info */}
      <div className="company-header bg-white shadow-sm rounded p-4 mb-4">
        <h2 className="text-primary mb-2">{job.employer?.companyName}</h2>
        {job.employer?.companyWebsite && (
          <p><Globe size={16} /> <a href={job.employer.companyWebsite} target="_blank" rel="noreferrer">{job.employer.companyWebsite}</a></p>
        )}
        {job.employer?.linkedinUrl && (
          <p><Linkedin size={16} /> <a href={job.employer.linkedinUrl} target="_blank" rel="noreferrer">{job.employer.linkedinUrl}</a></p>
        )}
        {job.employer?.companyBio && (
          <p><strong>About Company:</strong> {job.employer.companyBio}</p>
        )}
      </div>

      {/* Job Highlights */}
      <div className="card p-4 shadow-sm mb-4">
        <h3 className="text-dark mb-3">{job.title}</h3>
        <div className="d-flex flex-wrap gap-3">
          <span className="badge bg-light text-dark"><MapPin size={14} /> {job.location}</span>
          <span className="badge bg-light text-dark"><Clock size={14} /> {job.jobType}</span>
          <span className="badge bg-light text-dark"><Briefcase size={14} /> {job.locationType}</span>
          <span className="badge bg-light text-dark"><User size={14} /> {job.jobExperience}</span>
          <span className="badge bg-light text-dark"><DollarSign size={14} /> {job.salary}</span>
          <span className="badge bg-light text-dark"><Calendar size={14} /> {job.applicationDeadline}</span>
        </div>

        <div className="mt-4">
          <h5 className="text-secondary">📋 Job Description</h5>
          <p>{job.description}</p>
        </div>

        <div className="mt-3">
          <h6>🧠 Skills Required</h6>
          <div className="skills-tags">
            {job.skillsRequired?.map(skill => (
              <span key={skill} className="skill-tag">{skill}</span>
            ))}
          </div>
        </div>

        <div className="d-flex flex-wrap gap-2 mt-4">
          <button
            className="btn btn-success"
            disabled={isDeadlineOver}
            onClick={checkIfAlreadyApplied} 
          >
            {isDeadlineOver ? "❌ Deadline Over" : "🚀 Apply Now"}
          </button>
          <button
  className="btn btn-outline-secondary"
  onClick={handleSave}
  disabled={savedJobIds.includes(job.jobId)}
>
  {savedJobIds.includes(job.jobId) ? "✅ Job Saved" : "💾 Save Job"}
</button>

          <button
  className="btn btn-outline-dark"
  onClick={() => {
    if (fromPage === "applied-jobs") {
      navigate("/jobseeker/applied-jobs");
    } else {
      navigate("/jobs");
    }
  }}
>
  ← Back
</button>
        </div>
      </div>

      {/* Application Modal */}
      {showConfirmModal && (
  <div className="modal show fade d-block" tabIndex="-1" role="dialog">
    <div className="modal-dialog modal-lg" role="document">
      <div className="modal-content">
        <div className="modal-header">
          <h5 className="modal-title">Complete Application</h5>
          <button type="button" className="btn-close" onClick={() => setShowConfirmModal(false)}></button>
        </div>
        <div className="modal-body">
          <div className="mb-3">
            <label>
              Cover Letter <span className="text-danger">*</span>
            </label>
            <textarea
              className={`form-control ${formErrors.coverLetter ? "is-invalid" : ""}`}
              value={coverLetter}
              onChange={(e) => setCoverLetter(e.target.value)}
              rows={4}
            />
            {formErrors.coverLetter && <div className="invalid-feedback">{formErrors.coverLetter}</div>}
          </div>

          <div className="row">
            <div className="col-md-6 mb-3">
              <label>
                UG Percentage <span className="text-danger">*</span>
              </label>
              <input
                type="number" className={`form-control ${formErrors.ugPercentage ? "is-invalid" : ""}`}value={answers.ugPercentage}
                onChange={(e) => setAnswers({ ...answers, ugPercentage: e.target.value })}/>
              {formErrors.ugPercentage && <div className="invalid-feedback">{formErrors.ugPercentage}</div>}
            </div>

            <div className="col-md-6 mb-3">
              <label>PG Percentage</label>
              <input
                type="number" className="form-control" value={answers.pgPercentage}
                onChange={(e) => setAnswers({ ...answers, pgPercentage: e.target.value })}/>
            </div>
          </div>

          <div className="row">
            <div className="col-md-6 mb-3">
              <label>
                Graduation Year <span className="text-danger">*</span>
              </label>
              <input
                type="number" className={`form-control ${formErrors.gradYear ? "is-invalid" : ""}`}
                value={answers.gradYear} onChange={(e) => setAnswers({ ...answers, gradYear: e.target.value })}/>
              {formErrors.gradYear && <div className="invalid-feedback">{formErrors.gradYear}</div>}
            </div>

            <div className="col-md-6 mb-3">
              <label>
                Prior Experience <span className="text-danger">*</span>
              </label>
              <select
                className={`form-control ${formErrors.priorExperience ? "is-invalid" : ""}`}
                value={answers.priorExperience}
                onChange={(e) => setAnswers({ ...answers, priorExperience: e.target.value })}>
                <option value="">Select</option>
                <option>Yes</option>
                <option>No</option>
              </select>
              {formErrors.priorExperience && <div className="invalid-feedback">{formErrors.priorExperience}</div>}
            </div>
          </div>

          {answers.priorExperience === "Yes" && (
            <div className="mb-3">
              <label>
                Describe Your Recent Project <span className="text-danger">*</span>
              </label>
              <textarea
                className={`form-control ${formErrors.recentProject ? "is-invalid" : ""}`}
                value={answers.recentProject}
                onChange={(e) => setAnswers({ ...answers, recentProject: e.target.value })}
                rows={4}
              />
              {formErrors.recentProject && <div className="invalid-feedback">{formErrors.recentProject}</div>}
            </div>
          )}

          <div className="row">
            <div className="col-md-6 mb-3">
              <label>
                Willing to Relocate? <span className="text-danger">*</span>
              </label>
              <select
                className={`form-control ${formErrors.relocation ? "is-invalid" : ""}`}
                value={answers.relocation}
                onChange={(e) => setAnswers({ ...answers, relocation: e.target.value })} >
                <option value="">Select</option>
                <option>Yes</option>
                <option>No</option>
              </select>
              {formErrors.relocation && <div className="invalid-feedback">{formErrors.relocation}</div>}
            </div>

            <div className="col-md-6 mb-3">
              <label>
                Notice Period <span className="text-danger">*</span>
              </label>
              <select
                className={`form-control ${formErrors.noticePeriod ? "is-invalid" : ""}`}
                value={answers.noticePeriod}
                onChange={(e) => setAnswers({ ...answers, noticePeriod: e.target.value })}>
                <option value="">Select Notice Period</option>
                       <option>Immediate</option>
                        <option>15 Days</option>
                        <option>30 Days</option>
                        <option>60+ Days</option>
                        <option>Not Applicable</option>
              </select>
              {formErrors.noticePeriod && <div className="invalid-feedback">{formErrors.noticePeriod}</div>}
            </div>
          

          <div className="col-md-6 mb-3">
             <label>
              Do you have legal work authorization in this country? <span className="text-danger">*</span>
            </label>
            <small className="text-muted">
                (Are you legally allowed to work without visa sponsorship?)
            </small>
            <select
              className={`form-control ${formErrors.workAuth ? "is-invalid" : ""}`}
              value={answers.workAuth}
              onChange={(e) => setAnswers({ ...answers, workAuth: e.target.value })}
            >
             <option value="">Select</option>
                 <option>Yes</option>
                  <option>No</option>
            </select>
           {formErrors.workAuth && (
               <div className="invalid-feedback">{formErrors.workAuth}</div>
          )}
          </div>


            <div className="col-md-6 mb-3">
              <label>
                Comfortable with Night Shifts? <span className="text-danger">*</span>
              </label>
              <select
                className={`form-control ${formErrors.workAuth ? "is-invalid" : ""}`}
                value={answers.nightShift} onChange={(e) => setAnswers({ ...answers, nightShift: e.target.value })}>
                <option value="">Select</option>
                <option>Yes</option>
                <option>No</option>
              </select>
              {formErrors.nightShift && <div className="invalid-feedback">{formErrors.nightShift}</div>}
            </div>
          </div>

          <div className="mb-3">
            <label>Portfolio / GitHub Link</label>
            <input className="form-control" value={answers.portfolio}
              onChange={(e) => setAnswers({ ...answers, portfolio: e.target.value })} />
          </div>
        </div>

       <div className="mb-3">
  <strong>📄 Resume:</strong>{" "}
  {resumeFileName ? (
    <a
      href={`http://localhost:8081/uploads/resumes/${resumeFileName}`}
      target="_blank"
      rel="noopener noreferrer"
      className="btn btn-sm btn-outline-primary ms-2"
    >
      View Resume
    </a>
  ) : (
    "Not uploaded"
  )}
</div>

     <div className="modal-footer">
          <button className="btn btn-secondary" onClick={() => setShowConfirmModal(false)}>
            Cancel
          </button>
          <button className="btn btn-primary" onClick={handleApply}>
            Submit Application
          </button>
        </div>
      </div>
    </div>
  </div>
)}
<ToastContainer position="top-center" autoClose={3000} />

    </div>
  );
};

export default JobDetailsPage;