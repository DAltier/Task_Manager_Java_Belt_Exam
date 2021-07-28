package com.codingdojo.danaaltier.beltExam.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.danaaltier.beltExam.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

}
