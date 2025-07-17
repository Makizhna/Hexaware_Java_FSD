package com.example.taskmanager.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Task {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank(message = "Title is required")
	    private String title;

	    private String description;

	    @NotNull(message = "Due Date is required")
	    private LocalDate dueDate;

	    @Enumerated(EnumType.STRING)
	    private Priority priority;

	    @Enumerated(EnumType.STRING)
	    private Status status;

	    public enum Priority { LOW, MEDIUM, HIGH }
	    public enum Status { PENDING, IN_PROGRESS, COMPLETED }
	    
	    
	    public Task() {};
	    
	    
		public Task(Long id, String title, String description, LocalDate dueDate, Priority priority, Status status) {
			super();
			this.id = id;
			this.title = title;
			this.description = description;
			this.dueDate = dueDate;
			this.priority = priority;
			this.status = status;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}


		public LocalDate getDueDate() {
			return dueDate;
		}


		public void setDueDate(LocalDate dueDate) {
			this.dueDate = dueDate;
		}


		public Priority getPriority() {
			return priority;
		}

		public void setPriority(Priority priority) {
			this.priority = priority;
		}


		public Status getStatus() {
			return status;
		}


		public void setStatus(Status status) {
			this.status = status;
		}


		@Override
		public String toString() {
			return "Task [id=" + id + ", title=" + title + ", description=" + description + ", dueDate=" + dueDate
					+ ", priority=" + priority + ", status=" + status + "]";
		}

		
}
