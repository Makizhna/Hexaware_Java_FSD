import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Home.css';

const HomePage = () => {
  return (
    <div className="home-container">
      <nav className="navbar">
        <h1 className="logo">Task Manager</h1>
        <div className="nav-links">
          <Link to="/login" className="nav-link">Login</Link>
          <Link to="/register" className="nav-link">Register</Link>
        </div>
      </nav>

      <div className="hero">
        <h2>Welcome to Task Manager</h2>
        <p>Organize, track, and complete your tasks efficiently.</p>
      </div>
    </div>
  );
};

export default HomePage;
