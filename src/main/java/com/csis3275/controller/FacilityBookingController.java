package com.csis3275.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csis3275.dao.FacilityBookingDAOImpl;
import com.csis3275.model.FacilityBooking;

/**
 * @author Hackermen
 * Hotel Management System
 */

@Controller
public class FacilityBookingController {

	@Autowired
	FacilityBookingDAOImpl facilityBookingDAOImp;

	@ModelAttribute("facilityBooking")
	public FacilityBooking setupAddForm() {
		return new FacilityBooking();
	}
	
	//Create object for the UserController
	UserController user = new UserController();

	// Get the facilityBooking and display the form
	@GetMapping("/deleteFacilityBooking")
	public String deleteFacilityBooking(@RequestParam(required = true) int id, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		// Get the facilityBooking
		facilityBookingDAOImp.deleteFacilityBooking(id);

		// Get a list of facilityBookings from the controller
		List<FacilityBooking> facilityBookings = facilityBookingDAOImp.getAllFacilityBookings();
		model.addAttribute("facilityBookingList", facilityBookings);

		model.addAttribute("message", "Deleted FacilityBooking: " + id);

		return "facilityBookingManagement";
	}
//	Arrays.asList(facilityBookingType.amenities).contains(amenity);
	/**
	 * Post request to add entry to database
	 */
	// Handle Form Post
	@PostMapping("/createFacilityBooking")
	public String createFacilityBooking(@ModelAttribute("facilityBooking") FacilityBooking createFacilityBooking, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Create the facilityBooking pass the object in.
		facilityBookingDAOImp.createFacilityBooking(createFacilityBooking);
		model.addAttribute("facilityBooking", createFacilityBooking);
		model.addAttribute("message", "Facility Booking created: " + createFacilityBooking.getFacilityBookingId());
		
		// Get a list of facilityBookings from the controller
		List<FacilityBooking> facilityBookings = facilityBookingDAOImp.getAllFacilityBookings();
		model.addAttribute("facilityBookingList", facilityBookings);
		
		return "facilityBookingManagement";
	}
	
	
	@GetMapping("/facilityBookingManagement")
	public String showFacilityBookings(HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		// Get a list of facilityBookings from the database
		List<FacilityBooking> facilityBookings = facilityBookingDAOImp.getAllFacilityBookings();
	
		// Add the list of facilityBookings to the model to be returned to the view
		model.addAttribute("facilityBookingList", facilityBookings);
		
//		model.addAttribute("facilityBookingTypesListItems", getFacilityBookingTypeListItems());
		
		return "facilityBookingManagement";
	}

	/**
	 * Get request to get entry to be edited from database
	 */
	@GetMapping("/editFacilityBooking")
	public String editFacilityBooking(@RequestParam(required = true) int id, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		// Get the facilityBooking
		FacilityBooking updatedFacilityBooking = facilityBookingDAOImp.getFacilityBookingById(id);
		model.addAttribute("facilityBooking", updatedFacilityBooking);
		
//		model.addAttribute("facilityBookingTypesListItems", getFacilityBookingTypeListItems());
		
		return "facilityBookingManagementEdit";
	}

	/**
	 * Post request to edit entry from database
	 */
	@PostMapping("/editFacilityBooking")
	public String updateFacilityBooking(@ModelAttribute("facilityBooking") FacilityBooking updatedFacilityBooking, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
			
			facilityBookingDAOImp.updateFacilityBooking(updatedFacilityBooking);
			
			List<FacilityBooking> facilityBookings = facilityBookingDAOImp.getAllFacilityBookings();
			model.addAttribute("facilityBookingList", facilityBookings);
			model.addAttribute("message", "Updated Facility Booking " + updatedFacilityBooking.getFacilityBookingId());
			
			return "facilityBookingManagement";
	}
	
	/** Get the list of facilityBooking types for the form
	 * @return facilityBookingTypesListItems used in the listbox to register a facilityBooking
	 */
//	@ModelAttribute("facilityBookingTypesListItems")
//	public List<String> getFacilityBookingTypeListItems() {
//		List<String> facilityBookingTypesListItems = new ArrayList<String>();
//		
//		List<FacilityBookingType> facilityBookingTypes = facilityBookingDAOImp.getAllFacilityBookingTypes();
//		
//		for(FacilityBookingType facilityBookingType: facilityBookingTypes) {
//			facilityBookingTypesListItems.add(facilityBookingType.getFacilityBookingType());
//		}
//
//		return facilityBookingTypesListItems;
//	}
	
}
