package com.csis3275.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.csis3275.dao.FacilitiesDAOImpl;
import com.csis3275.model.Facilities;

/**
 * @author Hackermen Hotel Management System
 */

@Controller
public class FacilitiesController {

	@Autowired
	FacilitiesDAOImpl facilityDAOimp;

	// Create object for the UserController. Access Control checking
	UserController user = new UserController();

	@ModelAttribute("facility")
	public Facilities setupAddFormFacilities() {
		return new Facilities();
	}

	/**
	 * GET method to Staff Facilities Management
	 */
	@RequestMapping("/facilitiesManagement")
	public String manageFacilities(HttpSession session, ModelMap model) {

		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Staff"))
			return "denied";

		// Get all Facilities entries and store them in a list
		List<Facilities> facilitiesList = facilityDAOimp.getAllFacilities();

		// Add attributes to the model
		model.addAttribute("facilitiesList", facilitiesList);
		
		//Removing session attribute
		session.removeAttribute("message");

		return "facilitiesManagement";
	}

	/**
	 * Get list of Facilities types
	 */
	@ModelAttribute("facilitiesTypesList")
	public List<String> getFacilitiesTypes() {
		List<String> facilitiesTypesList = new ArrayList<String>();
		facilitiesTypesList.add("Restaurant");
		facilitiesTypesList.add("Meeting Room");

		return facilitiesTypesList;
	}

	/**
	 * Method to create a new Facility
	 * 
	 * @param newBooking
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping("/createFacility")
	public String createFacility(@ModelAttribute("facility") Facilities newFacility, HttpSession session, Model model) {
		
		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Staff"))
			return "denied";

		//Calling to check if name already exist
		String facilityName = newFacility.getFacilityName();
		if (hasFacilityNameAlready(facilityName)) {
			
			// Set attribute on the session
			session.setAttribute("message", "Facility name already exists. Please choose a different one");
		
		} else {
			
			// Add booking to Database
			facilityDAOimp.createFacility(newFacility);
			
			// Set attribute on the session
			session.setAttribute("message", "Facility created successfully");
		}

		// Get all Facilities entries and store them in a list
		List<Facilities> facilitiesList = facilityDAOimp.getAllFacilities();

		// Add attributes to the model
		model.addAttribute("facilitiesList", facilitiesList);

		return "facilitiesManagement";
	}
	
	/**
	 * Method to check if the Facility Name already exist
	 * @param facilityName
	 * @return
	 */
	private boolean hasFacilityNameAlready(String facilityName) {
		
		List<Facilities> facilitiesList = facilityDAOimp.getFacilityByName(facilityName);
		if (facilitiesList.size() > 0)
			return true;
		
		return false;		
	}	

	/**
	 * Get mapping to delete facility entry
	 * 
	 * @param facilityId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/deleteFacility")
	public String deleteFacility(@RequestParam(required = true) int facilityId, HttpSession session, Model model) {

		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Staff"))
			return "denied";

		// Calling DAO method to delete facility
		facilityDAOimp.deleteFacility(facilityId);

		// Get all Facilities entries and store them in a list
		List<Facilities> facilitiesList = facilityDAOimp.getAllFacilities();

		// Add attributes to the model
		model.addAttribute("facilitiesList", facilitiesList);

		// Add attributes to session
		session.setAttribute("message", "Facility deleted successfully");

		return "facilitiesManagement";

	}

	/**
	 * Get mapping to update facility entry
	 * 
	 * @param facilityId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/updateFacility")
	public String updateFacility(@RequestParam(required = true) int facilityId, HttpSession session, Model model) {

		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Staff"))
			return "denied";

		// Calling DAO method to get facility to update
		Facilities facility = facilityDAOimp.getFacilityById(facilityId);

		// Get all Facilities entries and store them in a list
		List<Facilities> facilitiesList = facilityDAOimp.getAllFacilities();

		// Add attributes to the model
		model.addAttribute("facilitiesList", facilitiesList);
		model.addAttribute("facility", facility);

		// Managing sessions messages
		session.setAttribute("update", "Updating '" + facility.getFacilityName() + "' facility resource");
		session.removeAttribute("message");

		return "facilitiesManagementEdit";

	}

	/**
	 * Updated Facility method
	 * 
	 * @param newFacility
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping("/updateFacility")
	public String updateFacility(@ModelAttribute("facility") Facilities updatedFacility, HttpSession session,
			Model model) {
		
		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Staff"))
			return "denied";

		// Posting the updated Facility
		facilityDAOimp.updateFacility(updatedFacility);

		// Get all Facilities entries and store them in a list
		List<Facilities> facilitiesList = facilityDAOimp.getAllFacilities();

		// Add attributes to the model
		model.addAttribute("facilitiesList", facilitiesList);

		// Set attribute on the session
		session.setAttribute("message", "Facility updated successfully");

		return "facilitiesManagement";
	}
}
