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
public class StaffController {

	@Autowired
	StaffDAOImpl staffDAOImp;
	
	@Autowired
	CustomerDAOImpl customerDAOImp;

	@ModelAttribute("staff")
	public Staff setupAddForm() {
		return new Staff();
	}

	// Get the staff and display the form
	@GetMapping("/deleteStaff")
	public String deleteStaff(@RequestParam(required = true) int id, Model model) {
		
		// Get the staff
		staffDAOImp.deleteStaff(id);

		// Get a list of staffs from the controller
		List<Staff> staffs = staffDAOImp.getAllStaffs();
		model.addAttribute("staffList", staffs);

		model.addAttribute("message", "Deleted Staff: " + id);
		
		/*
		 * creating object to get session data
		 */
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);

		//checking if user has a valid session hash
		if (session.getAttribute("sessionHash") != session)
			return "redirect:/login";
		else
			return "staffManagement";

	}
	
	/**
	 * Post request to add entry to database
	 */
	// Handle Form Post
	@PostMapping("/createStaff")
	public String createStaff(@ModelAttribute("staff") Staff createStaff, Model model) {

		List<Staff> usernameCheckStaff = staffDAOImp.getStaff(createStaff.getUsername());
		List<Customer> usernameCheckCustomer = customerDAOImp.getCustomer(createStaff.getUsername());
		
		if(!(usernameCheckCustomer.isEmpty() && usernameCheckStaff.isEmpty()))
		{
			model.addAttribute("errorMessage", "Username already in use");
		}
		else {
			// Create the staff pass the object in.
			staffDAOImp.createStaff(createStaff);
			model.addAttribute("staff", createStaff);
			model.addAttribute("message", "User created: " + createStaff.getUsername());
		}
		
		// Get a list of staffs from the controller
		List<Staff> staffs = staffDAOImp.getAllStaffs();
		model.addAttribute("staffList", staffs);
		
		/*
		 * creating object to get session data
		 */
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);

		//checking if user has a valid session hash
		if (session.getAttribute("sessionHash") != session)
			return "redirect:/login";
		else
			return "staffManagement";
		
	}
	
	
	@GetMapping("/userManagement/staff")
	public String showStaffs(Model model) {
		// Get a list of staffs from the database
		List<Staff> staffs = staffDAOImp.getAllStaffs();

	
		// Add the list of staffs to the model to be returned to the view
		model.addAttribute("staffList", staffs);
		
		/*
		 * creating object to get session data
		 */
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);

		//checking if user has a valid session hash
		if (session.getAttribute("sessionHash") != session)
			return "redirect:/login";
		else
			return "staffManagement";

	}

	/**
	 * Get request to get entry to be edited from database
	 */
	@GetMapping("/editStaff")
	public String editStaff(@RequestParam(required = true) int id, Model model) {
		
		// Get the staff
		Staff updatedStaff = staffDAOImp.getStaffById(id);
		model.addAttribute("staff", updatedStaff);
		
		/*
		 * creating object to get session data
		 */
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false);

		//checking if user has a valid session hash
		if (session.getAttribute("sessionHash") != session)
			return "redirect:/login";
		else
			return "staffManagementEdit";
	}

	/**
	 * Post request to edit entry from database
	 */
	@PostMapping("/editStaff")
	public String updateStaff(@ModelAttribute("staff") Staff updatedStaff, Model model) {

		List<Staff> usernameCheckStaff = staffDAOImp.getStaff(updatedStaff.getUsername(), updatedStaff.getId());
		
		List<Customer> usernameCheckCustomer = customerDAOImp.getCustomer(updatedStaff.getUsername());
		
		if(!(usernameCheckCustomer.isEmpty() && usernameCheckStaff.isEmpty()))
		{
			model.addAttribute("errorMessage", "Username already in use");
			
			/*
			 * creating object to get session data
			 */
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(false);

			//checking if user has a valid session hash
			if (session.getAttribute("sessionHash") != session)
				return "redirect:/login";
			else
				return "staffManagementEdit";

		}
		else {
			staffDAOImp.updateStaff(updatedStaff);
			
			List<Staff> staffs = staffDAOImp.getAllStaffs();
			model.addAttribute("staffList", staffs);
			model.addAttribute("message", "Updated Staff " + updatedStaff.getUsername());
			
			/*
			 * creating object to get session data
			 */
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(false);

			//checking if user has a valid session hash
			if (session.getAttribute("sessionHash") != session)
				return "redirect:/login";
			else
				return "staffManagement";
		}

	}
	
}