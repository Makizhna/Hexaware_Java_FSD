import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import AOS from "aos";
import "aos/dist/aos.css";
import { getAllJobs } from "../api/jobApi";
import "../styles/LandingPage.css";
import backgroundImage from "../assets/background.jpg";

const LandingPage = () => {
  const [keyword, setKeyword] = useState("");
  const [location, setLocation] = useState("");
  const [category, setCategory] = useState("");
  const [jobs, setJobs] = useState([]);

  useEffect(() => {
    AOS.init({ duration: 800 });
    fetchJobs();
  }, []);

  const fetchJobs = async () => {
    try {
      const res = await getAllJobs();
      setJobs(res.data.slice(0, 6));
    } catch (err) {
      console.error("Failed to fetch jobs", err);
    }
  };

  return (
    <div className="landing" id="top">
      {/* Navbar */}
      <nav className="navbar">
        <div className="container">
          <h2 className="logo">CareerCrafter</h2>
          <ul className="nav-links">
            <li><a href="#top">Home</a></li>
            <li><Link to="/jobs">Jobs</Link></li>
            <li><a href="#company-highlight">About</a></li>
            <li><Link to="/contact">Contact</Link></li>
          </ul>
          <div className="auth-buttons">
            <Link to="/login" className="btn-outline">Login</Link>
            <Link to="/register" className="btn-filled">Sign Up</Link>
          </div>
        </div>
      </nav>

      {/* Hero with background image and gradient */}
      <header
  className="hero"
  style={{
    backgroundImage: `linear-gradient(to right, #0a2640cc, #2c5364cc), url(${backgroundImage})`,
    backgroundPosition: 'center center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    color: 'white',
    padding: '140px 20px',
    minHeight: '100vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    textAlign: 'center',
  }}
>
  <div className="hero-content">
    <h1>Find Your Dream Job Today!</h1>
    <p>Connecting talent with opportunity, one career at a time.</p>

    {/* Get Started Button */}
    <div style={{ marginTop: "30px", marginBottom: "40px" }}>
      <Link
        to="/register"
        className="btn-filled"
        style={{
          padding: "12px 28px",
          backgroundColor: "#00c9a7",
          color: "#fff",
          borderRadius: "8px",
          fontSize: "1rem",
          textDecoration: "none",
          boxShadow: "0 4px 12px rgba(0,0,0,0.2)",
          transition: "all 0.3s ease",
        }}
        onMouseOver={(e) => (e.target.style.backgroundColor = "#00b296")}
        onMouseOut={(e) => (e.target.style.backgroundColor = "#00c9a7")}
      >
        🚀 Get Started
      </Link>
    </div>

    {/* Hero Stats */}
    <div className="hero-stats">
      <div><h4>25,850</h4><p>Jobs</p></div>
      <div><h4>10,250</h4><p>Candidates</p></div>
      <div><h4>18,400</h4><p>Companies</p></div>
    </div>
  </div>
</header>


      {/* Recent Jobs */}
      <section className="recent-jobs container" data-aos="fade-up">
        <div className="section-header">
          <h2>Recent Jobs Available</h2>
          <p>Fresh listings tailored for you. Start applying now!</p>
        </div>
        <div className="job-list">
          {jobs.map((job, index) => (
            <div className="job-card" key={index}>
              <div className="job-meta">
                <span className="job-time">{job.locationType}</span>
              </div>
              <h4>{job.title}</h4>
              <p className="company-name">{job.employer?.companyName}</p>
              <div className="job-info">
                <span>📍 {job.location}</span>
                <span>💼 {job.jobType}</span>
                <span>🧠 {job.jobExperience}</span>
                <span>💰 {job.salary}</span>
              </div>
              <Link to={`/jobs/${job.jobId}`} className="btn-job-details">Job Details</Link>
            </div>
          ))}
        </div>
      </section>

      

      {/* Company Highlight */}
      <section id="company-highlight" className="company-highlight container" data-aos="fade-up">
        <div className="highlight-content">
          <div className="highlight-box">
            <div className="highlight-item">🌟 <h5>Top Rated</h5><p>Companies</p></div>
            <div className="highlight-item">🎁 <h5>Best Benefits</h5><p>Packages</p></div>
            <div className="highlight-item">🚀 <h5>Career Growth</h5><p>Opportunities</p></div>
            <div className="highlight-item">🌐 <h5>Global Reach</h5><p>Worldwide</p></div>
          </div>
          <div className="highlight-text ms-4">
            <h2>Good Life Begins With A <span className="text-primary">Great Company</span></h2>
            <p>
              We connect ambitious professionals with top-tier organizations that value growth, innovation, and talent.
              Join thousands who found their dream careers through our platform.
            </p>
            <div className="highlight-buttons">
              <Link to="/jobs" className="btn btn-filled">🔍Search Jobs</Link>
              <button className="btn-outline">Learn More</button>
            </div>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="footer bg-dark text-light mt-5">
        <div className="container py-4 d-flex flex-wrap justify-content-between">
          <div>
            <h5 className="text-light">CareerCrafter</h5>
            <p>Your trusted partner in career transformation. We connect talent with opportunity.</p>
            <div className="social-icons d-flex gap-2">
              <span>🌐</span><span>🐦</span><span>📘</span>
            </div>
          </div>
          <div>
            <h6>Quick Links</h6>
            <ul>
              <li><Link to="/jobs">Find Jobs</Link></li>
            
            </ul>
          </div>
          <div>
            <h6>Support</h6>
            <ul className="list-unstyled">
              
              <li><Link to="/contact">Contact Us</Link></li>
              
            </ul>
          </div>
        </div>
        <div className="text-center py-3 border-top">© 2025 CareerCrafter. Built with passion for career success.</div>
      </footer>
    </div>
  );
};

export default LandingPage;
