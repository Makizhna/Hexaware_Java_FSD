import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/ContactUs.css";

const ContactUs = () => {
  const [formData, setFormData] = useState({ name: "", email: "", message: "" });
  const navigate = useNavigate();

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = (e) => {
    e.preventDefault();
    alert(" Message sent successfully!");

    setFormData({ name: "", email: "", message: "" });
    navigate("/");
  };

  return (
    <div className="container py-5 contact-page">
      <h2 className="text-center mb-4">📩 Contact Us</h2>
      <p className="text-center text-muted mb-5">
        Have questions or feedback? We'd love to hear from you!
      </p>

      <form onSubmit={handleSubmit} className="contact-form mx-auto">
        <input
          type="text"
          name="name"
          placeholder="Your Name"
          required
          value={formData.name}
          onChange={handleChange}
        />
        <input
          type="email"
          name="email"
          placeholder="Your Email"
          required
          value={formData.email}
          onChange={handleChange}
        />
        <textarea
          name="message"
          rows="5"
          placeholder="Your Message"
          required
          value={formData.message}
          onChange={handleChange}
        />
        <button type="submit" className="btn btn-primary">Send Message</button>
      </form>
    </div>
  );
};

export default ContactUs;
