import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Auth.css"; 
import { registerUser } from "../api/userApi";
import { FiEye, FiEyeOff } from "react-icons/fi";
import registrationImage from "../assets/registration-side.jpg";



const RegisterPage = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    role: ""
  });

  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const togglePassword = () => setShowPassword(!showPassword);

  const validateEmail = (email) => {
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return pattern.test(email);
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!validateEmail(formData.email)) {
      setError("Please enter a valid email address.");
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    try {
      await registerUser({
        name: formData.name,
        email: formData.email,
        password: formData.password,
        role: formData.role
      });
      alert("Registration successful!");
      navigate("/login");
    } catch (err) {
      setError(err.response?.data || "Something went wrong");
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card-wrapper">
        <div className="auth-left" style={{ backgroundImage: `url(${registrationImage})`, backgroundSize: 'cover', backgroundPosition: 'center' }} ></div>
        <div className="auth-right">
          <div className="auth-box">
            <h2>Create Account</h2>
            <p>Join CareerCrafter to shape your future</p>
            <form onSubmit={handleSubmit}>
              <input
                type="text"
                name="name"
                placeholder="Full Name"
                value={formData.name}
                onChange={handleChange}
                required
              />
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
                  placeholder="Create password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
                <button type="button" className="toggle-password" onClick={togglePassword}>
                  {showPassword ? <FiEyeOff /> : <FiEye />}
                </button>
              </div>
              <div className="input-group">
                <input
                  type={showPassword ? "text" : "password"}
                  name="confirmPassword"
                  placeholder="Confirm password"
                  value={formData.confirmPassword}
                  onChange={handleChange}
                  required
                />
                <button type="button" className="toggle-password" onClick={togglePassword}>
                  {showPassword ? <FiEyeOff /> : <FiEye />}
                </button>
              </div>
              <select name="role" value={formData.role} onChange={handleChange} required>
                <option value="" disabled hidden>Select Role</option>
                <option value="JOB_SEEKER">Job Seeker</option>
                <option value="EMPLOYER">Employer</option>
              </select>
              <button type="submit" className="auth-btn">Register</button>
            </form>
            {error && <p style={{ color: "red", marginTop: "10px" }}>{error}</p>}
            <p className="auth-switch">
              Already have an account? <span onClick={() => navigate("/login")}>Login</span>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
