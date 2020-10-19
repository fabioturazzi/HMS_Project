package com.csis3275.controller;

import javax.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	StaffDAOImpl staffDAOImp;

	@Autowired
	CustomerDAOImpl customerDAOImp;

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
	@GetMapping("/login")
	public String login(ModelMap model) {

		User user = new User();
		model.addAttribute("user", user);

		return "login";
	}

	/*
	 * This method will serve POST handler to "/login" requests
	 */
	@PostMapping("/login")
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
	
	public boolean hasValidSession(HttpSession session) {
		boolean isValid = false;
		
		if (session.getAttribute("sessionHash") == session) {
			isValid = true;
		} else {
			isValid = false;
		}
		
		return isValid;
	}

}
