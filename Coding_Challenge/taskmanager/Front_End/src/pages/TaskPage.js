import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/Task.css';

const TaskPage = () => {
  const [tasks, setTasks] = useState([]);
  const [newTask, setNewTask] = useState({
    title: '',
    description: '',
    dueDate: '',
    priority: 'LOW',
    status: 'PENDING',
  });
  const [editingTaskId, setEditingTaskId] = useState(null);

  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  useEffect(() => {
    if (!token) {
      navigate('/');
      return;
    }
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    const res = await axios.get('http://localhost:8080/tasks', {
      headers: { Authorization: `Bearer ${token}` },
    });
    setTasks(res.data);
  };

  const handleAddOrUpdateTask = async (e) => {
    e.preventDefault();
    if (editingTaskId) {
      const res = await axios.put(`http://localhost:8080/tasks/${editingTaskId}`, newTask, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setTasks(tasks.map((task) => (task.id === editingTaskId ? res.data : task)));
      setEditingTaskId(null);
    } else {
      
      const res = await axios.post('http://localhost:8080/tasks', newTask, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setTasks([...tasks, res.data]);
    }

    
    setNewTask({ title: '', description: '', dueDate: '', priority: 'LOW', status: 'PENDING' });
  };

  const handleEdit = (task) => {
    setNewTask({
      title: task.title,
      description: task.description,
      dueDate: task.dueDate,
      priority: task.priority,
      status: task.status,
    });
    setEditingTaskId(task.id);
  };

  const handleDelete = async (id) => {
    await axios.delete(`http://localhost:8080/tasks/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    setTasks(tasks.filter((t) => t.id !== id));
  };

  const logout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  return (
    <div className="task-wrapper">
      <div className="task-header">
        <h2>Task Dashboard</h2>
        <button className="logout-btn" onClick={logout}>Logout</button>
      </div>
      <form className="task-form" onSubmit={handleAddOrUpdateTask}>
  <input
    type="text"
    name="title"
    placeholder="Title"
    value={newTask.title}
    onChange={(e) => setNewTask({ ...newTask, title: e.target.value })}
    required
  />
  <input
    type="text"
    name="description"
    placeholder="Description"
    value={newTask.description}
    onChange={(e) => setNewTask({ ...newTask, description: e.target.value })}
  />
  <input
    type="date"
    name="dueDate"
    value={newTask.dueDate}
    onChange={(e) => setNewTask({ ...newTask, dueDate: e.target.value })}
    required
  />

  <select
    name="priority"
    value={newTask.priority}
    onChange={(e) => setNewTask({ ...newTask, priority: e.target.value })}
  >
    <option value="LOW">Low</option>
    <option value="MEDIUM">Medium</option>
    <option value="HIGH">High</option>
  </select>

  <select
    name="status"
    value={newTask.status}
    onChange={(e) => setNewTask({ ...newTask, status: e.target.value })}
  >
    <option value="PENDING">Pending</option>
    <option value="IN_PROGRESS">In Progress</option>
    <option value="COMPLETED">Completed</option>
  </select>

  <button type="submit" className="add-task-btn">
    {editingTaskId ? 'Update Task' : 'Add Task'}
  </button>

  {editingTaskId && (
    <button
      type="button"
      className="cancel-btn"
      onClick={() => {
        setEditingTaskId(null);
        setNewTask({ title: '', description: '', dueDate: '', priority: 'LOW', status: 'PENDING' });
      }}>
      Cancel
    </button>
  )}
</form>
      <ul className="task-list">
        {tasks.map((task) => (
          <li key={task.id} className="task-item">
            <div>
              <strong>{task.title}</strong>
              <p>{task.description}</p>
              <small>Due: {task.dueDate}</small><br />
              <span className={`badge ${task.priority.toLowerCase()}`}>{task.priority}</span>
              <span className={`badge ${task.status.toLowerCase().replace('_', '-')}`}>{task.status}</span>
            </div>
            <div>
              <button className="edit-btn" onClick={() => handleEdit(task)}>Edit</button>
              <button className="delete-btn" onClick={() => handleDelete(task.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TaskPage;
