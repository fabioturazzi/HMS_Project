package com.csis3275.controller;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.csis3275.dao.RoomDAOImpl;
import com.csis3275.model.Booking;
import com.csis3275.model.Customer;
import com.csis3275.model.Room;
import com.csis3275.model.RoomType;

@Controller
public class BookingController {

	@Autowired
	BookingDAOImpl bookingDAOImp;

	@Autowired
	CustomerDAOImpl customerDAOImp;

	@Autowired
	RoomDAOImpl roomDAOImpl;

	@ModelAttribute("booking")
	public Booking setupAddForm() {
		return new Booking();
	}

	@ModelAttribute("customer")
	public Customer setupCustomerForm() {
		return new Customer();
	}

	// Create object for the UserController
	UserController user = new UserController();

	@GetMapping("/bookingManagement/bookings")
	public String showBookings(HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get a list of customers from the database
		List<Booking> bookings = bookingDAOImp.getAllBookings();

		List<Customer> custormersList = customerDAOImp.getAllCustomers();
		model.addAttribute("custormersList", custormersList);

		List<Room> roomList = roomDAOImpl.getAllRooms();
		model.addAttribute("roomList", roomList);

		// Add the list of customers to the model to be returned to the view
		model.addAttribute("bookings", bookings);

		return "bookingManagement";
	}

	@GetMapping("/deleteBooking")
	public String deleteBooking(@RequestParam(required = true) int id, HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get the room
		bookingDAOImp.deleteBooking(id);

		// Get a list of rooms from the controller
		List<Booking> bookings = bookingDAOImp.getAllBookings();
		model.addAttribute("bookings", bookings);

		model.addAttribute("message", "Deleted Booking: " + id);

		return "bookingManagement";
	}

	@PostMapping("/createBooking")
	public String createRoom(@ModelAttribute("room") Booking createBooking, HttpServletRequest request,
			HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		String sd = createBooking.getBookingDateStart();
		String ed = createBooking.getBookindDateEnd();
		

		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(sd, new ParsePosition(0));
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(ed, new ParsePosition(0));
		
		System.out.println(startDate + " " + endDate);

		if (endDate.compareTo(startDate) < 0) {
			
			model.addAttribute("errorMessage", "Please, select correct ending date.");
			
		} else {

			// set some fields depending on the input
			createBooking.setDateOfCreation(createBooking.setTodaysDate());
			createBooking.setStatus("booked");

			createBooking.setRoomNumber(Integer.parseInt(request.getParameter("roomList")));
			createBooking.setCustomerId(Integer.parseInt(request.getParameter("customer")));

			List<Room> room = roomDAOImpl.getRoomByNumber(Integer.parseInt(request.getParameter("roomList")));
			createBooking.setRoomType(room.get(0).getRoomType());

			// put new booking to the DB
			bookingDAOImp.createBooking(createBooking);

			model.addAttribute("booking", createBooking);
			model.addAttribute("message", "Booking created successfully created!");
			
		}
		
		// Get a list of bookings from the controller
		List<Booking> bookings = bookingDAOImp.getAllBookings();
		model.addAttribute("bookings", bookings);
		
		return "bookingManagement";

	}

}
