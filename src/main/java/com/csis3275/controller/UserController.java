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
		
		/*
		 * 
		 * DELETE THIS BEFORE RELEASE
		 */
//		Customer user = new Customer("denngall", "user123", "Daniil", "Volovik", "customer", "0000000000", "!23 ABC", "abc@gmail.com");
//		
//		user.setId(999);
		
		Customer user = customerDAOImp.getCustomerById(1);
		
		// put this when profile is created
//		user.putRegistrationDate();
//		user.putProfileUpdated();
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
	public String deleteProfile(@ModelAttribute("user") Customer customerToDelete, HttpSession session, Model model) {

//		//create object to get session data
//		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		HttpSession mySession = attr.getRequest().getSession(false);
//		//checking if user has a valid session hash
//		if (mySession.getAttribute("sessionHash") != mySession)
//			return "login";
//		Customer user = new Customer("denngall", "user123", "Daniil", "Volovik", "customer", "0000000000", "!23 ABC", "abc@gmail.com");
//		user.setId(999);
		
		model.addAttribute("user", customerToDelete);
		
		return "deleteProfile";
	}
	
	@PostMapping("/deleteProfile")
	public String deleteProfile(HttpSession session, @RequestParam(required = true) int id, Model model) {

//		//create object to get session data
//		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		HttpSession mySession = attr.getRequest().getSession(false);
//		//checking if user has a valid session hash
//		if (mySession.getAttribute("sessionHash") != mySession)
//			return "login";
		
		customerDAOImp.deleteCustomer(id);
		
		return "registration";
	}
}
