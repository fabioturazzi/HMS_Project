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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.csis3275.dao.BookingDAOImpl;
import com.csis3275.dao.CustomerDAOImpl;
import com.csis3275.dao.StaffDAOImpl;
import com.csis3275.model.Booking;
import com.csis3275.model.Customer;
import com.csis3275.model.Staff;
import com.csis3275.model.User;

/**
 * @author Hackermen
 * Hotel Management System
 */

@Controller
public class CustomerController {

	@Autowired
	CustomerDAOImpl customerDAOImp;

	@Autowired
	StaffDAOImpl staffDAOImp;
	
	@Autowired
	BookingDAOImpl bookingDAOImpl;

	@ModelAttribute("customer")
	public Customer setupAddForm() {
		return new Customer();
	}

	// Create object for the UserController
	UserController user = new UserController();

	@GetMapping("/registration")
	public String showRegistration(Model model) {

		return "registration";
	}

	/** Handle Post Form for registration
	 */
	@PostMapping("/registration")
	public String registerCustomer(@ModelAttribute("customer") Customer createCustomer, Model model, HttpSession session) {

		createCustomer.setUserType("Customer");
		// Set the date of account creation and initial "Update"
		createCustomer.putRegistrationDate();
		createCustomer.putProfileUpdated();

		List<Customer> usernameCheckCustomer = customerDAOImp.getCustomer(createCustomer.getUsername());

		List<Staff> usernameCheckStaff = staffDAOImp.getStaff(createCustomer.getUsername());

		// Checks if the username is occupied
		if (!(usernameCheckCustomer.isEmpty() && usernameCheckStaff.isEmpty())) {
			model.addAttribute("errorMessage", "Username already in use");

			return "registration";
		} else {
			// Create the customer pass the object in.
			customerDAOImp.createCustomer(createCustomer);
			model.addAttribute("customer", createCustomer);
			session.setAttribute("manage", "no");
			session.setAttribute("userType", createCustomer.getUserType());
			session.setAttribute("username", createCustomer.getUsername());
			session.setAttribute("sessionHash", session);

			return "registrationSuccess";
		}

	}

	/** Get the customer and display the form
	 */
	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam(required = true) int id, HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get the customer
		customerDAOImp.deleteCustomer(id);

		// Get a list of customers from the controller
		List<Customer> customers = customerDAOImp.getAllCustomers();
		model.addAttribute("customerList", customers);

		model.addAttribute("warning", "Deleted Customer: " + id);

		return "customerManagement";
	}

	/**
	 * Post request to add entry to database
	 */
	// Handle Form Post
	@PostMapping("/createCustomer")
	public String createCustomer(@ModelAttribute("customer") Customer createCustomer, HttpSession session,
			Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		List<Customer> usernameCheckCustomer = customerDAOImp.getCustomer(createCustomer.getUsername());
		List<Staff> usernameCheckStaff = staffDAOImp.getStaff(createCustomer.getUsername());

		if (!(usernameCheckCustomer.isEmpty() && usernameCheckStaff.isEmpty())) {
			model.addAttribute("errorMessage", "Username already in use");
		} else {
			// Create the customer pass the object in.
			createCustomer.putRegistrationDate();
			createCustomer.getProfileUpdated();
			customerDAOImp.createCustomer(createCustomer);
			model.addAttribute("customer", createCustomer);
			model.addAttribute("message", "User created: " + createCustomer.getUsername());
		}

		// Get a list of customers from the controller
		List<Customer> customers = customerDAOImp.getAllCustomers();
		model.addAttribute("customerList", customers);

		return "customerManagement";

	}

	@GetMapping("/userManagement/customer")
	public String showCustomers(HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get a list of customers from the database
		List<Customer> customers = customerDAOImp.getAllCustomers();

		// Add the list of customers to the model to be returned to the view
		model.addAttribute("customerList", customers);

		return "customerManagement";
	}

	/**
	 * Get request to get entry to be edited from database
	 */
	@GetMapping("/editCustomer")
	public String editCustomer(@RequestParam(required = true) int id, HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get the customer
		Customer updatedCustomer = customerDAOImp.getCustomerById(id);
		model.addAttribute("customer", updatedCustomer);

		return "customerManagementEdit";
	}

	/**
	 * Post request to edit entry from database
	 */
	@PostMapping("/editCustomer")
	public String updateCustomer(@ModelAttribute("customer") Customer updatedCustomer, HttpSession session,
			Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		List<Customer> usernameCheckCustomer = customerDAOImp.getCustomer(updatedCustomer.getUsername(),
				updatedCustomer.getId());

		List<Staff> usernameCheckStaff = staffDAOImp.getStaff(updatedCustomer.getUsername());

		if (!(usernameCheckCustomer.isEmpty() && usernameCheckStaff.isEmpty())) {
			model.addAttribute("errorMessage", "Username already in use");

			return "customerManagementEdit";
		} else {
			updatedCustomer.putProfileUpdated();
			customerDAOImp.updateCustomer(updatedCustomer);

			List<Customer> customers = customerDAOImp.getAllCustomers();
			model.addAttribute("customerList", customers);
			model.addAttribute("message", "Updated Customer " + updatedCustomer.getUsername());

			return "customerManagement";
		}
	}

	/**
	 * Get request to show customer's profile information
	 */
	@GetMapping("/profile")
	public String showProfile(HttpSession session, Model model) {

		//Check if user is logged in and if it is a Customer
		if (session.getAttribute("username") != null && session.getAttribute("userType").equals("Customer")) {
			
			String username = session.getAttribute("username").toString();
		
			List<Booking> userBookings = bookingDAOImpl.getBookingByUsername(username);
			model.addAttribute("numOfBookings", userBookings.size());
			// Get our customer and show his profile
			List<Customer> customerData = customerDAOImp.getCustomer(username);
	
			model.addAttribute("user", customerData.get(0));
			return "profileView";
		
		} else {
			return "denied";
		}

	}

	/**
	 * Post request to accept changes to customer's profile information
	 */
	@PostMapping("/profile")
	public String showEditProfile(@ModelAttribute("user") Customer updatedUser, Model model, HttpSession session) {

		// Get updated user, set update profile date, and save changes to the DB
		updatedUser.putProfileUpdated();
		customerDAOImp.updateCustomer(updatedUser);
		Customer customer = customerDAOImp.getCustomerById(updatedUser.getId());
		model.addAttribute("user", customer);
		session.setAttribute("username", customer.getUsername());

		return "profileView";
	}

	/**
	 * Get request to show customer to delete
	 */
	@GetMapping("/deleteProfile")
	public String deleteProfile(HttpSession session, Model model) {

		String username = session.getAttribute("username").toString();
		
		// Get a customer to delete and redirect to delete confirmation page
		List<Customer> userToDelete = customerDAOImp.getCustomer(username);

		model.addAttribute("user", userToDelete.get(0));

		return "deleteProfile";
	}

	/**
	 * Get request to delete customer's profile
	 */
	@GetMapping("/deleteProfileCompletely")
	public String deleteProfile(@RequestParam(required = true) int id, Model model, HttpSession session) {

		// remove from the DB
		customerDAOImp.deleteCustomer(id);
		
		User user = new User();
		model.addAttribute("user", user);

		// remove session info and redirect to the registration page
		session.invalidate();

		return "registration";
	}

}
