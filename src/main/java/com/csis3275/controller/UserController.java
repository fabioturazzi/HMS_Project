package com.csis3275.controller;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.csis3275.dao.CustomerDAOImpl;
import com.csis3275.dao.StaffDAOImpl;
import com.csis3275.model.Customer;
import com.csis3275.model.Staff;

import com.csis3275.model.User;

/*
 * @author: Hackermen
 * This Controller is responsible to deal requests and responses regarding Login tasks.
 */


@Controller
public class UserController {

	@Autowired
	CustomerDAOImpl customerDAOImp;
	
	@Autowired
	StaffDAOImpl staffDAOImp;

	@ModelAttribute("user")
	public Customer setupAddForm() {
		return new Customer();
	}

	@GetMapping("/profile")
	public String showProfile(HttpSession session, Model model) {

//		//create object to get session data
//		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		HttpSession mySession = attr.getRequest().getSession(false);
//		//checking if user has a valid session hash
//		if (mySession.getAttribute("sessionHash") != mySession)
//			return "login";\
		
		// Get customer by id form the session
		Customer user = customerDAOImp.getCustomerById(1);
		// put this when profile is created
		// user.putRegistrationDate();
		// user.putProfileUpdated();
		model.addAttribute("user", user);
		return "profileView";
	}
	
	@PostMapping("/profile")
	public String showEditProfile(@ModelAttribute("user") Customer updatedUser, Model model) {

//		//create object to get session data
//		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		HttpSession mySession = attr.getRequest().getSession(false);
//		//checking if user has a valid session hash
//		if (mySession.getAttribute("sessionHash") != mySession)
//			return "login";

		updatedUser.putProfileUpdated();
		customerDAOImp.updateCustomer(updatedUser);
		Customer customer = customerDAOImp.getCustomerById(updatedUser.getId());
		model.addAttribute("user", customer);
		
		return "profileView";
	}
	
	@GetMapping("/deleteProfile")
	public String deleteProfile(HttpSession session, Model model) {

//		//create object to get session data
//		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		HttpSession mySession = attr.getRequest().getSession(false);
//		//checking if user has a valid session hash
//		if (mySession.getAttribute("sessionHash") != mySession)
//			return "login";
//		Customer user = new Customer("denngall", "user123", "Daniil", "Volovik", "customer", "0000000000", "!23 ABC", "abc@gmail.com");
//		user.setId(999);
		
		Customer userToDelete = customerDAOImp.getCustomerById(1);
		model.addAttribute("user", userToDelete);
		
		return "deleteProfile";
	}
	
	@GetMapping("/deleteProfileCompletely")
	public String deleteProfile(@RequestParam(required = true) int id, Model model) {

//		//create object to get session data
//		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		HttpSession mySession = attr.getRequest().getSession(false);
//		//checking if user has a valid session hash
//		if (mySession.getAttribute("sessionHash") != mySession)
//			return "login";
		
		customerDAOImp.deleteCustomer(id);
		
		return "login";
	}

	@ModelAttribute("staff")
	public Staff setupAddFormStaff() {
		return new Staff();
	}

	@ModelAttribute("customer")
	public Customer setupAddFormCustomer() {
		return new Customer();
	}

	/*
	 * This method will serve GET handler to "/login" requests
	 */
	@GetMapping("/")
	public String login(ModelMap model) {

		User user = new User();
		model.addAttribute("user", user);

		return "login";
	}

	/*
	 * This method will serve POST handler to "/login" requests
	 */
	@PostMapping("/")
	public String checkCredentials(HttpSession session, User user, ModelMap model) {

		session.removeAttribute("sessionHash");

		List<Customer> authCustomer = customerDAOImp.getUsernamePassword(user.getUsernameForm());
		List<Staff> authStaff = staffDAOImp.getUsernamePassword(user.getUsernameForm());
		
		/*
		 * Checking and searching for a user in the database (Customer and Staff)
		 */
		
		if (authCustomer.size() > 0) {
			if (isAuthenticated(authCustomer.get(0).getUsername(), authCustomer.get(0).getPassword()
					, user.getUsernameForm(), user.getPasswordForm())) {
				
				model.addAttribute("message", "Hello " + authCustomer.get(0).getfName());
				session.setAttribute("sessionHash", session);
				
				//Check if user has management access
				hasManageAccess(session, authCustomer.get(0).getUserType());
				
				return "roomsearch";
			} else {
				model.addAttribute("message", "Username and/or Password does not match");
				session.removeAttribute("sessionHash");
				return "login";
			}
		}
		
		if (authStaff.size() > 0) {
			if (isAuthenticated(authStaff.get(0).getUsername(), authStaff.get(0).getPassword()
					, user.getUsernameForm(), user.getPasswordForm())) {
				
				model.addAttribute("message", "Hello " + authStaff.get(0).getfName());
				session.setAttribute("sessionHash", session);
				
				//Check if user has management access
				hasManageAccess(session, authStaff.get(0).getUserType());
				
				return "roomsearch";
			} else {
				model.addAttribute("message", "Username and/or Password does not match");
				session.removeAttribute("sessionHash");
				return "login";
			}
		}
		return "login";
	}
	
	/*
	 * This method check if the username and password provided matches the one stored inside the DB
	 */
	private boolean isAuthenticated(String userDb, String passDb, String username, String password) {
		boolean isAuth = false;
		
		if (userDb.equals(username) && passDb.equals(password)) {
			isAuth = true;
		} else {
			isAuth = false;
		}
		return isAuth;
	}
	
	/*
	 * This method check if the session is valid based on User Authentication.
	 */
	public boolean hasValidSession(HttpSession session) {
		boolean isValid = false;
		
		if (session.getAttribute("sessionHash") == session) {
			isValid = true;
		} else {
			isValid = false;
		}
		
		return isValid;
	}
	
	/*
	 * Check management Access
	 */
	public void hasManageAccess(HttpSession session, String userType) {
		if (userType.equals("Staff")) {
			session.setAttribute("manage", "yes");
		} else {
			session.setAttribute("manage", "no");
		}
	}
}
