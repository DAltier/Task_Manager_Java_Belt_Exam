package com.codingdojo.danaaltier.beltExam.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.danaaltier.beltExam.models.Task;
import com.codingdojo.danaaltier.beltExam.models.User;
import com.codingdojo.danaaltier.beltExam.services.TaskService;
import com.codingdojo.danaaltier.beltExam.services.UserService;
import com.codingdojo.danaaltier.beltExam.validators.UserValidator;

@Controller
public class MainController {
	
	// Adding the User service, User validator, and Task service
	// as dependencies
	private final UserService userService;
	private final UserValidator userValidator;
	private final TaskService taskService;

	public MainController(UserService userService, UserValidator userValidator, TaskService taskService) {
		this.userService = userService;
		this.userValidator = userValidator;
		this.taskService = taskService;
	}
	
	
	// Redirect to index
	@RequestMapping("/")
	public String index(@ModelAttribute("user") User user) {
		return "index.jsp";
	}
		
	
	// Login & Registration
	@RequestMapping(value={"/login","/register"})
	public String login(Model model,@RequestParam(value="error",required=false) String error,@RequestParam(value="logout",required=false) String logout){
		if(error != null){model.addAttribute("errorMessage","Invalid Credentials.");}
		if(logout != null){model.addAttribute("logoutMessage","Logout Successful");}
		
		model.addAttribute("user",new User());
		return "index.jsp";
	}
	
	
	// POST route for registration
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "index.jsp";
		}
		User u = userService.registerUser(user);
		session.setAttribute("userId", u.getId());
		return "redirect:/tasks";
	}
	
	
	// POST route for login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession session, @ModelAttribute("user") User user) {
		boolean isAuthenticated = userService.authenticateUser(email, password);
		if (isAuthenticated) {
			User u = userService.findByEmail(email);
			session.setAttribute("userId", u.getId());
			return "redirect:/tasks";
		} else {
			model.addAttribute("error", "Invalid Credentials. Please try again.");
			return "index.jsp";
		}
	}
	
	
	// GET route for dashboard
	@RequestMapping("/tasks")
	public String homepage(HttpSession session, Model model) {
		// if current user is in session then proceed, if not redirect to login page
		if (session.getAttribute("userId") != null) {
			// get user from session, save them in the model and return the home page
			Long userId = (Long) session.getAttribute("userId");
			User u = userService.findUserById(userId);
			// pass user to jsp in order to display current user login name
			model.addAttribute("user", u);
			// get all tasks and list them on dashboard
			List<Task> tasklist = taskService.getAll();
			model.addAttribute("tasks", tasklist);
			return "dashboard.jsp";
		} else {
			return "redirect:/";
		}
	}

	
	// Logout
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	
	// GET route for task creation
	@RequestMapping("/tasks/new")
	public String displayTaskCreation(@ModelAttribute("task") Task myTask, Model model, HttpSession session) {
		// if current user is in session then proceed, if not redirect to login page
		if (session.getAttribute("userId") != null) {
			// find a list of all users, it will be used in dropdown menu
			List<User> allusers = userService.findAll();
			model.addAttribute("users", allusers);
			// get current user login info
			Long userId = (Long) session.getAttribute("userId");
			User u = userService.findUserById(userId);
			model.addAttribute("currentUser", u);
			return "newTask.jsp";
		} else {
			return "redirect:/";
		}
	}

	
	// POST route for task creation
	@RequestMapping(value = "/tasks/new", method = RequestMethod.POST)
	public String createNewTask(@Valid @ModelAttribute("task") Task myTask, BindingResult result, HttpSession session,
			Model model, RedirectAttributes limitError) {
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		model.addAttribute("user", u);
		if (result.hasErrors()) {
			List<User> allusers = userService.findAll();
			model.addAttribute("users", allusers);
			return "newTask.jsp";
		} else {
			// User cannot be assigned more than 3 tasks
			//--------------  Black Belt requirement ---------------
			// Get assignee id
			Long aId = (Long) myTask.getAssignee().getId();
			User myAssignee = userService.findUserById(aId);
			// List how many tasks that this user have been assigned to so far
			// Do not allow more than 3 tasks per user
			List<Task> myList = myAssignee.getAssigned_tasks();
			
			if (myList.size() >= 3) {
				List<User> allusers = userService.findAll();
				model.addAttribute("users", allusers);
				limitError.addFlashAttribute("errors", "User cannot be assigned more than 3 tasks");
				return "redirect:/tasks/new";
			//--------------  Black Belt requirement ---------------	
			} else {
				Task newTask = taskService.createTask(myTask);
				System.out.println("*** Current Login User *** : " + u.getName());
				// Add creator to a created task
				newTask.setCreator(u);
				// Save change through updateTask()
				taskService.updateTask(newTask);
				return "redirect:/tasks";
			}
		}
	}

	
	// GET route for viewing a task
	@RequestMapping("/tasks/{id}")
	public String displayTask(Model model, HttpSession session, @PathVariable("id") Long taskId) {
		// find a task by id
		Task thisTask = taskService.findTask(taskId);
		model.addAttribute("task", thisTask);

		// find a current login user id
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		model.addAttribute("currentUserId", u.getId());
		return "showTask.jsp";
	}

	
	// GET route for updating a task - only creator can see (black belt requirement)
	@RequestMapping("/tasks/{id}/edit")
	public String displayEditPage(Model model, @ModelAttribute("edittask") Task myTask, @PathVariable("id") Long taskId,
			HttpSession session) {
		//--------------  Black Belt requirement ---------------
		// get current login user id from session
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);

		// get a task by id
		Task editingTask = taskService.findTask(taskId);
		if (u.getId() == editingTask.getCreator().getId()) {

			// List all users
			List<User> allUsers = userService.findAll();

			// Getting a task's creator, pass info back to JSP
			model.addAttribute("creator", editingTask.getCreator());
			model.addAttribute("edittask", editingTask);
			model.addAttribute("users", allUsers);
			model.addAttribute("id", editingTask.getId());
			return "editTask.jsp";
		} else {
			return "redirect:/tasks";
		}

	}

	
	// POST route for updating a task
	@RequestMapping(value = "/tasks/{id}/edit", method = RequestMethod.POST)
	public String updateTask(Model model, @Valid @ModelAttribute("edittask") Task myTask, BindingResult result,
			@PathVariable("id") Long taskId) {
		if (result.hasErrors()) {
			List<User> allusers = userService.findAll();
			model.addAttribute("users", allusers);
			return "editTask.jsp";
		} else {
			taskService.createTask(myTask);
			return "redirect:/tasks";
		}
	}

	
	// Delete a task
	@RequestMapping("/tasks/{id}/delete")
	public String deleteTask(@PathVariable("id") Long myId) {
		Task deltask = taskService.findTask(myId);
		if (deltask != null) {
			taskService.deleteTask(myId);
			return "redirect:/tasks";
		} else {
			return "redirect:/tasks";
		}
	}

	
	//--------------  Black Belt requirement ---------------
	// List tasks from low to high priority
	@RequestMapping("/lowhigh")
	public String lowHigh(HttpSession session, Model model) {

		// get user from session, save them in the model and return the home page
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);

		// passes user to jsp in order to display current user login name
		model.addAttribute("user", u);

		// get all tasks and list them on dashboard
		List<Task> tasklist = taskService.lowToHigh();
		model.addAttribute("tasks", tasklist);
		return "dashboard.jsp";
	}

	
	//--------------  Black Belt requirement ---------------
	// List tasks from high to low priority
	@RequestMapping("/highlow")
	public String HighLow(HttpSession session, Model model) {

		// get user from session, save them in the model and return the home page
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);

		// passes user to jsp in order to display current user login name
		model.addAttribute("user", u);

		// get all tasks and list them on dashboard
		List<Task> tasklist = taskService.highToLow();
		model.addAttribute("tasks", tasklist);
		return "dashboard.jsp";
	}
}
