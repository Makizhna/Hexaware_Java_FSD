import React, { useEffect, useState } from "react";
import axios from "axios";

const UserManagementSection = () => {
  const [users, setUsers] = useState([]);
  const [formData, setFormData] = useState({ name: "", email: "", password: "", role: "JOB_SEEKER" });
  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = () => {
    axios
      .get("http://localhost:8081/api/user", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => setUsers(res.data))
      .catch((err) => console.error("Failed to fetch users", err));
  };

  const handleDelete = (id, role) => {
    if (role === "ADMIN") return; // extra guard

    if (!window.confirm("Are you sure you want to delete this user?")) return;
    axios
      .delete(`http://localhost:8081/api/user/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(() => {
        setUsers((prev) => prev.filter((u) => u.id !== id));
      })
      .catch((err) => alert("Failed to delete user"));
  };

  const handleAddUser = (e) => {
    e.preventDefault();
    if (!formData.name || !formData.email || !formData.password) {
      alert("All fields are required.");
      return;
    }

    axios
      .post("http://localhost:8081/api/user", formData, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => {
        setUsers([...users, res.data]);
        setFormData({ name: "", email: "", password: "", role: "JOB_SEEKER" });
      })
      .catch((err) => {
        console.error("Failed to add user", err);
        alert("Could not add user.");
      });
  };

  return (
    <div className="container mt-4">
      <h3>User Management</h3>

      {/* Add User Form */}
      <form className="row g-3 mb-4" onSubmit={handleAddUser}>
        <div className="col-md-3">
          <input
            type="text"
            className="form-control"
            placeholder="Name"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
          />
        </div>
        <div className="col-md-3">
          <input
            type="email"
            className="form-control"
            placeholder="Email"
            value={formData.email}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
          />
        </div>
        <div className="col-md-2">
          <input
            type="password"
            className="form-control"
            placeholder="Password"
            value={formData.password}
            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
          />
        </div>
        <div className="col-md-2">
          <select
            className="form-select"
            value={formData.role}
            onChange={(e) => setFormData({ ...formData, role: e.target.value })}
          >
            <option value="JOB_SEEKER">Job Seeker</option>
            <option value="EMPLOYER">Employer</option>
            <option value="ADMIN">Admin</option>
          </select>
        </div>
        <div className="col-md-2">
          <button type="submit" className="btn btn-success w-100">Add User</button>
        </div>
      </form>

      {/* User Table */}
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th style={{ width: "150px" }}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>{user.role}</td>
              <td>
                {user.role === "ADMIN" ? (
                  <span className="text-muted fw-bold">Admin Account</span>
                ) : (
                  <button
                    className="btn btn-sm btn-danger"
                    onClick={() => handleDelete(user.id, user.role)}
                  >
                    Delete
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserManagementSection;
