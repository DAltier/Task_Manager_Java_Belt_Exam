package com.codingdojo.danaaltier.beltExam.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.codingdojo.danaaltier.beltExam.models.User;
import com.codingdojo.danaaltier.beltExam.repositories.UserRepository;

@Service
public class UserService {

	// Adding the User repository as a dependency
    private final UserRepository userRepository;
    
    
    // Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
    // Register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
    }
    
    
    // Authenticate user
    public boolean authenticateUser(String email, String password) {
    	// Find user by email
    	User user = userRepository.findByEmail(email);
    	// If not found, return false
    	if(user == null) {
    		return false;
    	} else {
    		// If passwords match, return true, else, return false
    		if(BCrypt.checkpw(password, user.getPassword())) {
    			return true;
    		} else {
    			return false;
    		}
    	}
    }
    
    
    // Find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    
    // Find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    
    // Find all users
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	
	}
}