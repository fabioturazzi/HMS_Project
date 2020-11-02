package com.csis3275.controller;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(createBooking.getBookingDateStart(),
				new ParsePosition(0));
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(createBooking.getBookindDateEnd(),
				new ParsePosition(0));

		if (endDate.compareTo(startDate) < 0) {

			model.addAttribute("errorMessage", "Please, select correct ending date.");

		} else {

			// set some fields depending on the input
			createBooking.setDateOfCreation(createBooking.setTodaysDate());
			createBooking.setStatus("booked");
			
System.out.println(request.getParameter("room"));

			createBooking.setRoomNumber(Integer.parseInt(request.getParameter("room")));
			createBooking.setCustomerUsername(request.getParameter("customer"));

			List<Room> rooms = roomDAOImpl.getRoomByNumber(Integer.parseInt(request.getParameter("room")));
			Room room = rooms.get(0);
			System.out.println(room.getRoomType());

			createBooking.setRoomType(room.getRoomType());

			// Calculate price
			long noOfDaysBetween = ChronoUnit.DAYS.between(LocalDate.parse(sd), LocalDate.parse(ed));

			if (noOfDaysBetween == 0) {
				noOfDaysBetween = 1;
			}

			RoomType roomType = roomDAOImpl.getRoomType(room.getRoomType()).get(0);
			
			double totalCost = noOfDaysBetween * roomType.getDailyPrice();
			createBooking.setTotalCost(totalCost);

			// put new booking to the DB
			bookingDAOImp.createBooking(createBooking);

			model.addAttribute("booking", createBooking);
			model.addAttribute("message", "Booking was successfully created!");

		}

		// Get a list of bookings from the controller
		List<Booking> bookings = bookingDAOImp.getAllBookings();
		model.addAttribute("bookings", bookings);

		return "bookingManagement";

	}

	@GetMapping("/editBooking")
	public String editBooking(@RequestParam(required = true) int id, HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get the customer
		Booking bookingToUpdate = bookingDAOImp.getBooking(id);
		model.addAttribute("booking", bookingToUpdate);

		Customer customer = customerDAOImp.getCustomer(bookingToUpdate.getCustomerUsername()).get(0);
		model.addAttribute("customer", customer);

		List<Customer> custormersList = customerDAOImp.getAllCustomers();
		model.addAttribute("custormersList", custormersList);

		List<Room> roomList = roomDAOImpl.getAllRooms();
		model.addAttribute("roomList", roomList);

		return "bookingManagementEdit";
	}

	@PostMapping("/editBooking")
	public String updateBooking(@ModelAttribute("booking") Booking updatedBooking, HttpServletRequest request,
			HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		String sd = updatedBooking.getBookingDateStart();
		String ed = updatedBooking.getBookindDateEnd();

		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(updatedBooking.getBookingDateStart(),
				new ParsePosition(0));
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(updatedBooking.getBookindDateEnd(),
				new ParsePosition(0));;

		if (endDate.compareTo(startDate) < 0) {

			model.addAttribute("errorMessage", "Please, select correct ending date.");
			return "bookingManagementEdit";

		} else {

			updatedBooking.setRoomNumber(Integer.parseInt(request.getParameter("roomList")));
			
			long noOfDaysBetween = ChronoUnit.DAYS.between(LocalDate.parse(sd), LocalDate.parse(ed));

			if (noOfDaysBetween == 0) {
				noOfDaysBetween = 1;
			}
			
			List<Room> rooms = roomDAOImpl.getRoomByNumber(Integer.parseInt(request.getParameter("roomList")));
			Room room = rooms.get(0);
			RoomType roomType = roomDAOImpl.getRoomTypeById(room.getRoomId());

			double totalCost = noOfDaysBetween * roomType.getDailyPrice();
			updatedBooking.setTotalCost(totalCost);
			
			bookingDAOImp.updateBooking(updatedBooking);

			List<Booking> bookings = bookingDAOImp.getAllBookings();
			model.addAttribute("bookings", bookings);
			model.addAttribute("message", "Updated Booking " + updatedBooking.getBookingId());
			return "bookingManagement";

		}

	}

}
