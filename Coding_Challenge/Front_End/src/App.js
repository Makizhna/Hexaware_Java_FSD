import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import TaskPage from './pages/TaskPage';
import HomePage from './pages/HomePage';


function App() {
  return (
    <Router>
     <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/tasks" element={<TaskPage />} />
      </Routes>

    </Router>
  );
}

export default App;
