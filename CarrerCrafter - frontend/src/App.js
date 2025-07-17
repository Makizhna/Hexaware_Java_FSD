import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ProtectedRoute from './pages/ProtectedRoute';
import LandingPage from "./pages/LandingPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import JobSeekerDashboard from "./dashboards/JobSeeker/JobSeekerDashboard";
import EmployerDashboard from "./dashboards/Employer/EmployerDashboard";
import JobSeekerCreateProfile from './dashboards/JobSeeker/JobSeekerCreateProfile';
import EmployerCreateProfileForm from './dashboards/Employer/EmployerCreateProfileForm';
import EmployerProfileSection from './dashboards/Employer/EmployerProfileSection';
import PostJobPage from './dashboards/Employer/PostJobPage';
import EditJobPage from './dashboards/Employer/EditJobPage';
import JobDetailsPage from "./pages/JobDetailsPage";
import SavedJobsPage from './dashboards/JobSeeker/SavedJobsSection';
import ApplicationsSections from './dashboards/Employer/ApplicationsSection';
import ScheduleInterview from './dashboards/Employer/ScheduleInterview';
import AdminDashboard from './dashboards/Admin/AdminDashboard';
import RescheduleInterview from './dashboards/Employer/RescheduleInterview';
import ContactUs from "./pages/ContactUs";
import BrowseJobsSection from './pages/BrowseJobsSection';
import ManageJobsSection from './dashboards/Employer/ManageJobsSection';



function App() {
  return (
    
   <Router>
  <Routes>
    {/* Public Routes */}
    <Route path="/" element={<LandingPage />} />
    <Route path="/login" element={<LoginPage />} />
    <Route path="/register" element={<RegisterPage />} />

    {/* Admin Protected Route */}
    <Route
      path="/admin/dashboard"
      element={
        <ProtectedRoute allowedRoles={["ADMIN"]}>
          <AdminDashboard />
        </ProtectedRoute>
      }
    />

    {/* Employer Protected Routes */}
    <Route
      path="/employer/dashboard"
      element={
        <ProtectedRoute allowedRoles={["EMPLOYER"]}>
          <EmployerDashboard />
        </ProtectedRoute>
      }
    />
    <Route
      path="/employer/create-profile"
      element={
        <ProtectedRoute allowedRoles={["EMPLOYER"]}>
          <EmployerCreateProfileForm />
        </ProtectedRoute>
      }
    />
    <Route
      path="/employer/profile"
      element={
        <ProtectedRoute allowedRoles={["EMPLOYER"]}>
          <EmployerProfileSection />
        </ProtectedRoute>
      }
    />
    <Route
      path="/employer/post-job"
      element={
        <ProtectedRoute allowedRoles={["EMPLOYER"]}>
          <PostJobPage />
        </ProtectedRoute>
      }
    />
    <Route
      path="/edit-job/:jobId"
      element={
        <ProtectedRoute allowedRoles={["EMPLOYER"]}>
          <EditJobPage />
        </ProtectedRoute>
      }
    />
    <Route
      path="/schedule-interview/:applicationId"
      element={
        <ProtectedRoute allowedRoles={["EMPLOYER"]}>
          <ScheduleInterview />
        </ProtectedRoute>
      }
    />
    <Route
      path="/reschedule-interview/:interviewId"
      element={
        <ProtectedRoute allowedRoles={["EMPLOYER"]}>
          <RescheduleInterview />
        </ProtectedRoute>
      } 
    />


    <Route
  path="/employer/manage-jobs"
  element={
    <ProtectedRoute allowedRoles={["EMPLOYER"]}>
      <ManageJobsSection />
    </ProtectedRoute>
  }
/>
    {/* Job Seeker Protected Routes */}
    <Route
      path="/jobseeker/dashboard"
      element={
        <ProtectedRoute allowedRoles={["JOB_SEEKER"]}>
          <JobSeekerDashboard />
        </ProtectedRoute>
      }
    />
    <Route
      path="/jobseeker/create-profile"
      element={
        <ProtectedRoute allowedRoles={["JOB_SEEKER"]}>
          <JobSeekerCreateProfile />
        </ProtectedRoute>
      }
    />
    <Route
      path="/saved-jobs"
      element={
        <ProtectedRoute allowedRoles={["JOB_SEEKER"]}>
          <SavedJobsPage />
        </ProtectedRoute>
      }
    />

    {/* Shared Routes */}
    <Route path="/jobs" element={<BrowseJobsSection />} />
    <Route path="/jobs/:jobId" element={<JobDetailsPage />} />
    <Route path="/contact" element={<ContactUs />} />
    
  </Routes>

  
</Router>


  );
}

export default App;
