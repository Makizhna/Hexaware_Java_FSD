import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Auth.css";
import { loginUser } from "../api/authApi";
import { getJobSeekerByUserId } from "../api/jobSeekerApi";
import { getEmployerByUserId } from "../api/employerApi";
import { FiEye, FiEyeOff } from "react-icons/fi";
import registrationImage from "../assets/registration-side.jpg";

const LoginPage = () => {
  const [formData, setFormData] = useState({ email: "", password: "" });
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const togglePassword = () => setShowPassword(!showPassword);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const res = await loginUser(formData);
      const data = res.data;

      localStorage.setItem("token", data.token);
      localStorage.setItem("role", data.role);
      localStorage.setItem("currentUserId", data.userId);

      if (data.role === "ADMIN") {
        navigate("/admin/dashboard");
      } else if (data.role === "EMPLOYER") {
        try {
          const res = await getEmployerByUserId(data.userId, data.token);
          if (res.data) {
            localStorage.setItem("employerId", res.data.employeeId);
            navigate("/employer/dashboard");
          } else {
            navigate("/employer/create-profile");
          }
        } catch {
          navigate("/employer/create-profile");
        }
      } else if (data.role === "JOB_SEEKER") {
        try {
          const res = await getJobSeekerByUserId(data.userId, data.token);
          if (res.data) {
            localStorage.setItem("jobSeekerId", res.data.seekerId);
            navigate("/jobseeker/dashboard");
          } else {
            navigate("/jobseeker/create-profile");
          }
        } catch {
          navigate("/jobseeker/create-profile");
        }
      }
    } catch (err) {
      setError(err.response?.data || "Login failed");
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card-wrapper">
        <div
          className="auth-left"
          style={{
            backgroundImage: `linear-gradient(to right, rgba(255,255,255,0.2), rgba(255,255,255,0.2)), url(${registrationImage})`,
            backgroundSize: "cover",
            backgroundPosition: "center",
            filter: "brightness(1.1)",
          }}
        ></div>

        <div className="auth-right">
          <div className="auth-box">
            <h2>Login</h2>
            <p>Login with your email & password</p>
            <form onSubmit={handleSubmit}>
              <input
                type="email"
                name="email"
                placeholder="Email address"
                value={formData.email}
                onChange={handleChange}
                required
              />

              <div className="input-group">
                <input
                  type={showPassword ? "text" : "password"}
                  name="password"
                  placeholder="Password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
                <button type="button" className="toggle-password" onClick={togglePassword}>
                  {showPassword ? <FiEyeOff /> : <FiEye />}
                </button>
              </div>

              <button type="submit" className="auth-btn">Login</button>
            </form>

            {error && <p style={{ color: "red", marginTop: "10px" }}>{error}</p>}

            <p className="auth-forgot">
              <a onClick={() => alert("Don't worry we will help u to remember")}>Forgot Password?</a>

            </p>
            <p className="auth-switch">
              Don’t have an account? <span onClick={() => navigate("/register")}>Register</span>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
