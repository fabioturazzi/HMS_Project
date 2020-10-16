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
import com.csis3275.dao.CustomerDAOImpl;
import com.csis3275.model.Customer;

@Controller
public class CustomerController {

	@Autowired
	CustomerDAOImpl customerDAOImp;

	@ModelAttribute("customer")
	public Customer setupAddForm() {
		return new Customer();
	}

	@GetMapping("/registration")
	public String showCustomers(HttpSession session, Model model) {
		// Get a list of students from the database
		List<Customer> customers = customerDAOImp.getAllCustomers();

		// Add the list of students to the model to be returned to the view
		model.addAttribute("customerList", customers);

		return "registration";
	}

	// Handle Form Post
	@PostMapping("/registration")
	public String createCustomer(@ModelAttribute("customer") Customer createCustomer, Model model) {
		
		createCustomer.setUserType("Customer");
		
		List<Customer> customers = customerDAOImp.getCustomer(createCustomer.getUsername());
		
		if(!customers.isEmpty())
		{
			model.addAttribute("errorMessage", "Username already in use");
			return "registration";
		}
		else {
			// Create the student pass the object in.
			customerDAOImp.createCustomer(createCustomer);
			model.addAttribute("customer", createCustomer);
			return "registrationSuccess";
		}

	}

	// Get the student and display the form
	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam(required = true) int id, Model model) {

		// Get the student
		customerDAOImp.deleteCustomer(id);

		// Get a list of students from the controller
		List<Customer> customers = customerDAOImp.getAllCustomers();
		model.addAttribute("customerList", customers);

		model.addAttribute("message", "Deleted Customer: " + id);

		return "registration";
	}

}
