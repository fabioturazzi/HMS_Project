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


import com.csis3275.dao.RoomDAOImpl;
import com.csis3275.model.Booking;
import com.csis3275.model.Customer;
import com.csis3275.model.Room;
import com.csis3275.model.RoomType;

public class BookingController {
	
	@Autowired
	RoomDAOImpl roomDAOImp;

	@ModelAttribute("booking")
	public Booking setupAddForm() {
		return new Booking();
	}
	
	// Create object for the UserController
	UserController user = new UserController();
	
	@GetMapping("/bookingManagement")
	public String showCustomers(HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get a list of customers from the database
		List<Room> bookings = roomDAOImp.getAllRooms();

		// Add the list of customers to the model to be returned to the view
		model.addAttribute("bookings", bookings);

		return "bookingManagement";
	}
	
	@GetMapping("/deleteBooking")
	public String deleteBooking(@RequestParam(required = true) int id, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		// Get the room
		roomDAOImp.deleteRoom(id);

		// Get a list of rooms from the controller
		List<Room> bookings = roomDAOImp.getAllRooms();
		model.addAttribute("bookings", bookings);

		model.addAttribute("message", "Deleted Room: " + id);

		return "roomManagement";
	}

}
