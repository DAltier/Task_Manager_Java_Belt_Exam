package com.codingdojo.danaaltier.beltExam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.danaaltier.beltExam.models.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
	
	List<Task> findAll();
	
	// Lists the tasks based on priority ASC
	@Query(value="SELECT * FROM tasks ORDER BY Priority ASC", nativeQuery=true)
	List<Task> findAllPriorityLowToHigh(); 
	
	// Lists the tasks based on priority DESC
	@Query(value="SELECT * FROM tasks ORDER BY Priority DESC", nativeQuery=true)
	List<Task> findAllPriorityHighToLow(); 
}
