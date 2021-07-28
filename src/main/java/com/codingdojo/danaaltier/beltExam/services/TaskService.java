package com.codingdojo.danaaltier.beltExam.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.danaaltier.beltExam.models.Task;
import com.codingdojo.danaaltier.beltExam.repositories.TaskRepository;

@Service
public class TaskService {
	
	// Adding the Task repository as a dependency
	private final TaskRepository taskRepository;
	
	
	// Constructor
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	
	// Create a task
	public Task createTask(Task myTask) {
		return taskRepository.save(myTask);
	}
	
	
	// Get all tasks
	public List<Task> getAll() {
		return (List<Task>) taskRepository.findAll();
	}
	
	
	// Find a task by id
	public Task findTask(Long id) {
		Optional<Task> mytask = taskRepository.findById(id);
		if (mytask.isPresent()) {
			return mytask.get();
		}else {
			System.out.println("no task you are looking for");
			return null;
		}
	}
	
	
	// Update task
	public void updateTask(Task myTask) {
		taskRepository.save(myTask);
	}
	
	
	// Delete task
	public void deleteTask(Long myId) {
		taskRepository.deleteById(myId);
	}
	
	
	// Lists the tasks based on priority ASC
	public List<Task> lowToHigh(){
		return taskRepository.findAllPriorityLowToHigh();
	}
	
	
	// Lists the tasks based on priority DESC
	public List<Task> highToLow(){
		return taskRepository.findAllPriorityHighToLow();
	}
}
