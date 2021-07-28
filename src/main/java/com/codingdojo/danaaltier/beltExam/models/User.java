package com.codingdojo.danaaltier.beltExam.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "users")
public class User {
    
	// Attributes
    @Id
    @GeneratedValue
    private Long id;
    
    @Size(min=1, max=64)
    private String name;
    
    @Size(min=8)
    private String email;
    
    @Size(min=10)
    @Type(type="text")
    private String password;
    
    @Transient
    @Size(min=10)
    private String passwordConfirmation;
    
    private Date createdAt;
    private Date updatedAt;
    
    // Relationships
    // One to many : a user can create many tasks
    @OneToMany(mappedBy="creator", fetch = FetchType.LAZY)
    private List<Task> tasks;
    
    // One to many : assignee can have many tasks
    // max = 3 is black belt requirement
    @Size(max=3)
    @OneToMany(mappedBy="assignee", fetch = FetchType.LAZY)
    private List<Task> assigned_tasks;
    
        
    // Constructors
    public User() {
    }
    
    
    // Getters
    public Long getId() {
        return id;
    }
    public String getEmail() {
    	return email;
    }
    public String getName() {
    	return name;
    }
    public String getPassword() {
    	return password;
    }
    public String getPasswordConfirmation() {
    	return passwordConfirmation;
    }
    public Date getCreatedAt() {
    	return createdAt;
    }
    public Date getUpdatedAt() {
    	return updatedAt;
    }
    public List<Task> getTasks() {
		return tasks;
	}
    public List<Task> getAssigned_tasks() {
		return assigned_tasks;
	}
    
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setEmail(String email) {
    	this.email = email;
    }
    public void setName(String name) {
    	this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
    public void setAssigned_tasks(List<Task> assigned_tasks) {
		this.assigned_tasks = assigned_tasks;
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
