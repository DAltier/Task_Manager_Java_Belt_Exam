package com.codingdojo.danaaltier.beltExam.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.codingdojo.danaaltier.beltExam.models.User;

@Component
public class UserValidator implements Validator{
	
	// Must match email regex
	private static final String email_regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;
	private Matcher matcher;

	
	// Email pattern 
	public UserValidator() {
		this.pattern = Pattern.compile(email_regex);
	}

	
	// Custom validator used to validate an instance of the User model
	@Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

	
	// Returns a boolean based on whether the email entered is correct
	public boolean validateEmail(String email) {
		this.matcher = this.pattern.matcher(email);
		return matcher.matches();
	}
    
	
	// Validates email format 
	// Validates password matching
	// Errors are added via .rejectValue(String, String)
    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirmation", "Match");
        } else if(!validateEmail(user.getEmail())){
			errors.rejectValue("email","Match");
		}    
    }
    // In the if statement, the first argument is the member variable 
    // of the model being validated. 
    // The second argument is a code used to set an error message.
}
