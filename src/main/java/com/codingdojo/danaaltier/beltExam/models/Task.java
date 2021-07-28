package com.codingdojo.danaaltier.beltExam.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tasks")
public class Task {
	
	// Attributes
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(min=1, message="Task Name must not be empty")
	private String taskName;
	@Column(nullable=false)
	@Min(1)
	@Max(3)
	@NotNull(message="Task priority cannot be empty")
	private Integer priority;
	@Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    
    // Relationships
    // Many to one : many tasks can be created by a user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="creator_id")
    private User creator;
    
    // Many to one : many tasks can be assigned to same assignee (a users can have many tasks)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="assignee_id")
    @NotNull(message="Assignee cannot be empty")
    private User assignee;
      
    
    // Constructor
    public Task() {
    	
    }


    // Getters
	public Long getId() {
		return id;
	}
	public String getTaskName() {
		return taskName;
	}
	public Integer getPriority() {
		return priority;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public User getCreator() {
		return creator;
	}
	public User getAssignee() {
		return assignee;
	}


	// Setters
	public void setId(Long id) {
		this.id = id;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}
    
    
    // Methods
	@PrePersist
    protected void onCreate(){
    this.setCreatedAt(new Date());
    }

    @PreUpdate
    protected void onUpdate(){
    this.setUpdatedAt(new Date());
    }
}