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

import com.csis3275.dao.CustomerDAOImpl;
import com.csis3275.dao.StaffDAOImpl;
import com.csis3275.model.Customer;
import com.csis3275.model.Staff;

@Controller
public class CustomerController {

	@Autowired
	CustomerDAOImpl customerDAOImp;
	
	@Autowired
	StaffDAOImpl staffDAOImp;

	@ModelAttribute("customer")
	public Customer setupAddForm() {
		return new Customer();
	}

	@GetMapping("/registration")
	public String showRegistration(HttpSession session, Model model) {

		return "registration";
	}

	// Handle Form Post
	@PostMapping("/registration")
	public String registerCustomer(@ModelAttribute("customer") Customer createCustomer, Model model) {
		
		createCustomer.setUserType("Customer");
		
		List<Customer> usernameCheckCustomer = customerDAOImp.getCustomer(createCustomer.getUsername());
		
		List<Staff> usernameCheckStaff = staffDAOImp.getStaff(createCustomer.getUsername());
		
		if(!(usernameCheckCustomer.isEmpty() && usernameCheckStaff.isEmpty()))
		{
			model.addAttribute("errorMessage", "Username already in use");
			return "registration";
		}
		else {
			// Create the customer pass the object in.
			customerDAOImp.createCustomer(createCustomer);
			model.addAttribute("customer", createCustomer);
			return "registrationSuccess";
		}

	}

	// Get the customer and display the form
	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam(required = true) int id, Model model) {

		// Get the customer
		customerDAOImp.deleteCustomer(id);

		// Get a list of customers from the controller
		List<Customer> customers = customerDAOImp.getAllCustomers();
		model.addAttribute("customerList", customers);

		model.addAttribute("message", "Deleted Customer: " + id);

		return "customerManagement";
	}
	
	/**
	 * Post request to add entry to database
	 */
	// Handle Form Post
	@PostMapping("/createCustomer")
	public String createCustomer(@ModelAttribute("customer") Customer createCustomer, Model model) {

		List<Customer> usernameCheckCustomer = customerDAOImp.getCustomer(createCustomer.getUsername());
		List<Staff> usernameCheckStaff = staffDAOImp.getStaff(createCustomer.getUsername());
		
		if(!(usernameCheckCustomer.isEmpty() && usernameCheckStaff.isEmpty()))
		{
			model.addAttribute("errorMessage", "Username already in use");
		}
		else {
			// Create the customer pass the object in.
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
	public String editCustomer(@RequestParam(required = true) int id, Model model) {

		// Get the customer
		Customer updatedCustomer = customerDAOImp.getCustomerById(id);
		model.addAttribute("customer", updatedCustomer);

		return "customerManagementEdit";
	}

	/**
	 * Post request to edit entry from database
	 */
	@PostMapping("/editCustomer")
	public String updateCustomer(@ModelAttribute("customer") Customer updatedCustomer, Model model) {

		List<Customer> usernameCheckCustomer = customerDAOImp.getCustomer(updatedCustomer.getUsername(), updatedCustomer.getId());
		
		List<Staff> usernameCheckStaff = staffDAOImp.getStaff(updatedCustomer.getUsername());
		
		if(!(usernameCheckCustomer.isEmpty() && usernameCheckStaff.isEmpty()))
		{
			model.addAttribute("errorMessage", "Username already in use");
			return "customerManagementEdit";
		}
		else {
			customerDAOImp.updateCustomer(updatedCustomer);
			
			List<Customer> customers = customerDAOImp.getAllCustomers();
			model.addAttribute("customerList", customers);
			model.addAttribute("message", "Updated Customer " + updatedCustomer.getUsername());
			
			return "customerManagement";
		}

	}
	
	
}
