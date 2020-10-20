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
	public String login(HttpSession session, ModelMap model) {

		User user = new User();
		model.addAttribute("user", user);

		session.removeAttribute("sessionHash");
		session.removeAttribute("userType");
		session.removeAttribute("manage");
		session.removeAttribute("username");

		session.invalidate();
		
		return "login";
	}

	/*
	 * This method will serve POST handler to "/login" requests
	 */
	@PostMapping("/")
	public String checkCredentials(HttpSession session, User user, ModelMap model) {

		
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
				
				//testing
				session.setAttribute("username", authCustomer.get(0).getUsername());
				
				//Check if user has management access
				hasManageAccess(session, authCustomer.get(0).getUserType());
				
				return "roomSearch";
			} else {
				model.addAttribute("message", "Username and/or Password do not match");
				session.removeAttribute("sessionHash");
				return "login";
			}
		}
		
		if (authStaff.size() > 0) {
			if (isAuthenticated(authStaff.get(0).getUsername(), authStaff.get(0).getPassword()
					, user.getUsernameForm(), user.getPasswordForm())) {
				
				model.addAttribute("message", "Hello " + authStaff.get(0).getfName());
				session.setAttribute("sessionHash", session);
				
				//testing
				session.setAttribute("username", authStaff.get(0).getUsername());
				
				//Check if user has management access
				hasManageAccess(session, authStaff.get(0).getUserType());
				
				// Get a list of customers from the database
				List<Customer> customers = customerDAOImp.getAllCustomers();

				// Add the list of customers to the model to be returned to the view
				model.addAttribute("customerList", customers);
				
				return "customerManagement";
			} else {
				model.addAttribute("message", "Username and/or Password does not match");
				session.removeAttribute("sessionHash");
				return "login";
			}
		}
		model.addAttribute("message", "Username and/or Password do not match");
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
			session.setAttribute("userType", userType);
		} else {
			session.setAttribute("manage", "no");
			session.setAttribute("userType", userType);
		}
	}
}
