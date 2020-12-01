package com.csis3275.controller;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csis3275.dao.BookingDAOImpl;
import com.csis3275.dao.CustomerDAOImpl;
import com.csis3275.dao.RoomDAOImpl;
import com.csis3275.model.Booking;
import com.csis3275.model.Customer;
import com.csis3275.model.Room;
import com.csis3275.model.RoomType;

/**
 * @author Hackermen
 * Hotel Management System
 */

@Controller
public class RoomSearchBookingController {

	@Autowired
	RoomDAOImpl roomDAOImp;

	@Autowired
	BookingDAOImpl bookingDAOImp;

	@Autowired
	CustomerDAOImpl customerDAOImp;

	@ModelAttribute("roomType")
	public RoomType setupAddForm() {
		return new RoomType();
	}

	// Create object for the UserController
	UserController user = new UserController();

	/**
	 * Get request for roomSearch page
	 */
	@GetMapping("/roomSearch")
	public String searchRooms(HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Customer"))
			return "denied";

		// Get amenities and room types to start the form
		model.addAttribute("amenitiesListSearch", getAmenitiesSearch());
		model.addAttribute("roomTypesListItems", getRoomTypeListItems());

		// Get a list of roomTypes from the database
		List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();
		List<Integer> availableRooms = new ArrayList<>();

		// Verify how many available rooms exist in each roomType
		for (int i = 0; i < roomTypes.size(); i++) {
			availableRooms.add(roomDAOImp.getRoomsByRoomType(roomTypes.get(i).getRoomType()).size());
		}
		
		if(session.getAttribute("message") == null) {
			// Add number of available rooms to the model
			model.addAttribute("availableRooms", availableRooms);

			// Add the list of roomTypes to the model to be returned to the view
			model.addAttribute("roomTypeList", roomTypes);
		} else {
			model.addAttribute("availableRooms", session.getAttribute("availableRooms"));
			model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
			model.addAttribute("totalCost", session.getAttribute("totalCost"));
			model.addAttribute("roomTypeList", session.getAttribute("roomTypeList"));
			model.addAttribute("message", session.getAttribute("message"));
			model.addAttribute("startDate", session.getAttribute("startDate"));
			model.addAttribute("endDate", session.getAttribute("endDate"));
			model.addAttribute("capacity", session.getAttribute("capacity"));
			
			session.removeAttribute("availableRooms");
			session.removeAttribute("errorMessage");
			session.removeAttribute("totalCost");
			session.removeAttribute("roomTypeList");
			session.removeAttribute("message");
			session.removeAttribute("startDate");
			session.removeAttribute("endDate");
			session.removeAttribute("capacity");
		}


		return "roomSearch";
	}

	/**
	 * Post method for roomSearch page
	 */
	@PostMapping("/roomSearch")
	public String applySearchRoomTypes(HttpSession session, Model model,
			@ModelAttribute("roomType") RoomType searchedRoomType) {

		// Get list of amenities
		model.addAttribute("amenitiesListSearch", getAmenitiesSearch());

		// Initial filter check, verify if room capacity and room type match search
		// specifications
		List<RoomType> firstFilterCheck = new ArrayList<RoomType>();

		if (searchedRoomType.getRoomType().isBlank()) {
			firstFilterCheck = roomDAOImp.checkRoomTypeBooking(searchedRoomType.getCapacity());
		} else {
			firstFilterCheck = roomDAOImp.checkRoomTypeBooking(searchedRoomType.getCapacity(),
					searchedRoomType.getRoomType());
		}

		// List of roomtypes used for second check of the filter
		List<RoomType> secondFilterCheck = new ArrayList<RoomType>();

		// Iterate through second list to verify amenities
		if (searchedRoomType.getAmenities().length > 0
				&& !searchedRoomType.getAmenities()[0].equals("Disregard Amenities")) {

			// Boolean used to control loop to check amenities
			boolean testAmenities = true;

			// Iterate through amenities on the filter to verify which rooms satisfy the
			// constraints
			for (int i = 0; i < firstFilterCheck.size(); i++) {
				for (int j = 0; j < searchedRoomType.getAmenities().length; j++) {
					if (!Arrays.asList(firstFilterCheck.get(i).getAmenities())
							.contains(searchedRoomType.getAmenities()[j])) {
						testAmenities = false;
					}
				}
				// If constraints are satisfied, add the room from previous filter
				if (testAmenities) {
					secondFilterCheck.add(firstFilterCheck.get(i));
				}
				testAmenities = true;
			}
			// If no amenities are selected, consider first filter
		} else {
			secondFilterCheck = firstFilterCheck;
		}

		// Lists used for simultaneous checking of bookings, rooms and dates
		List<Room> availableRoomsByType = new ArrayList<>();
		List<Room> allRoomsByType = new ArrayList<>();
		List<Booking> checkBookings = new ArrayList<>();
		List<Integer> availableRooms = new ArrayList<>();

		// Parse date to control form submission rules and calculate total price
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchedRoomType.getStartDateFormControl(),
				new ParsePosition(0));
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchedRoomType.getEndDateFormControl(),
				new ParsePosition(0));

		// List for total price calculation
		List<Double> totalCost = new ArrayList<>();

		// Loop through second filter list to check dates
		for (int i = 0; i < secondFilterCheck.size(); i++) {

			// Get rooms from each roomType
			allRoomsByType.clear();
			allRoomsByType = roomDAOImp.getRoomsByRoomType(secondFilterCheck.get(i).getRoomType());

			availableRoomsByType.clear();

			// Loop through rooms and check for conflicts in dats on booking database
			for (int j = 0; j < allRoomsByType.size(); j++) {
				checkBookings = bookingDAOImp.checkBookingConflict(allRoomsByType.get(j).getRoomNumber(),
						searchedRoomType.getStartDateFormControl(), searchedRoomType.getEndDateFormControl());

				// If no conflict is found, add room to availableRoomsByType list
				if (checkBookings.isEmpty()) {
					availableRoomsByType.add(allRoomsByType.get(j));
				}
			}
			// Add count of rooms for each roomType
			availableRooms.add(availableRoomsByType.size());
			totalCost.add(secondFilterCheck.get(i).getDailyPrice()
					* ChronoUnit.DAYS.between(LocalDate.parse(searchedRoomType.getStartDateFormControl()),
							LocalDate.parse(searchedRoomType.getEndDateFormControl())));
		}

		if (endDate.compareTo(startDate) < 0) {

			// Return error if dates are wrong
			session.setAttribute("errorMessage", "Please select an end date after the selected start date.");
			session.setAttribute("roomTypeList", secondFilterCheck);
			session.setAttribute("availableRooms", availableRooms);

			// Get a list of roomTypes from the database
			List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();
			// Add the list of roomTypes to the model to be returned to the view
			session.setAttribute("roomTypeList", roomTypes);
			return "roomSearch";
		}


		// Add available rooms to model
		session.setAttribute("availableRooms", availableRooms);

		// Add Total cost to model
		session.setAttribute("totalCost", totalCost);

		// Add the list of roomTypes to the model to be returned to the view
		session.setAttribute("roomTypeList", secondFilterCheck);

		// Final check of list of rooms to start the view
		if (secondFilterCheck.isEmpty()) {
			session.setAttribute("message", "No rooms with the searched specifications are available for these dates.");
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

			session.setAttribute("message", "Search filters applied for " + searchedRoomType.getCapacity() + " guest(s) on dates: " + searchedRoomType.getStartDateFormControl() + " to " + searchedRoomType.getEndDateFormControl());
		}

		/*
		 * used on booking
		 */
		session.setAttribute("startDate", searchedRoomType.getStartDateFormControl().toString());
		session.setAttribute("endDate", searchedRoomType.getEndDateFormControl().toString());
		session.setAttribute("capacity", searchedRoomType.getCapacity());

		
		return "redirect:/roomSearch";

	}


	/*
	 * @param: roomType passing from the search
	 * @param: startDate passing from the search
	 * @param: endDate passing from the search
	 * @param: session and model passing information of session and to manipulate
	 * Model.
	 */
	@GetMapping("/roomBooking")
	public String bookingRoom(@RequestParam(required = true) String roomType,
			@RequestParam(required = true) String startDate, @RequestParam(required = true) String endDate,
			@RequestParam(required = true) int capacity, HttpSession session, Model model) {

		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Customer"))
			return "denied";

		// Getting Last Name of the user
		String userName = session.getAttribute("username").toString();
		List<Customer> customer = customerDAOImp.getCustomer(userName);

		// Getting String with the room amenities
		String amenities = getAmenities(roomType);

		// Getting #nights
		int nights = (int) getNights(startDate, endDate);

		// Calculating Price
		Double price = calculatePrice(roomType, nights);

		// Set a new booking
		Booking newBooking = setNewBooking(roomType, capacity, startDate, endDate, price, userName);

		// Adding Model Attributes to the View
		model.addAttribute("customerBook", customer.get(0));
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("roomType", roomType);
		model.addAttribute("amenities", amenities);
		model.addAttribute("nights", nights);
		model.addAttribute("price", price);
		model.addAttribute("booking", newBooking);

		return "customerBooking";

	}



	/*
	 */
	@PostMapping("/profileBook")
	public String showEditProfile(@ModelAttribute("customerBook") Customer updatedUser, HttpSession session,
			Model model) {

		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Customer"))
			return "denied";

		// Update customer information
		updatedUser.putProfileUpdated();
		customerDAOImp.updateCustomer(updatedUser);
		Customer customer = customerDAOImp.getCustomerById(updatedUser.getId());
		model.addAttribute("customerBook", customer);
		session.setAttribute("lastname", customer.getlName());

		return "redirect:/roomSearch";
	}

	/*
	 * @param: roomType, capacity (number of People), startDate, endDate, price, and
	 * username.
	 */
	private Booking setNewBooking(String roomType, int capacity, String startDate, String endDate, double price,
			String userName) {

		Booking newBooking = new Booking();

		// Setting Data on Booking Model
		List<Room> listRoomsByType = roomDAOImp.getRoomsByRoomType(roomType);
		newBooking.setRoomNumber(listRoomsByType.get(0).getRoomNumber());
		newBooking.setCustomerUsername(userName);
		newBooking.setNumbOfPeople(capacity);
		newBooking.setStatus("booked");
		newBooking.setPaid(false);
		newBooking.setBookingDateStart(startDate);
		newBooking.setBookindDateEnd(endDate);
		newBooking.setTotalCost(price);
		newBooking.setRoomType(roomType);

		// Getting date of the Booking creation and setting on Model
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String logBookingTime = df.format(new Date());
		newBooking.setDateOfCreation(logBookingTime);

		return newBooking;
	}

	/*
	 * @param: booking, session and Model. POST method to create a new Booking
	 */
	@PostMapping("/submitBooking")
	public String submitBooking(@ModelAttribute("booking") Booking newBooking, HttpSession session, Model model) {

		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Customer"))
			return "denied";

		// Add booking on Database
		bookingDAOImp.createBooking(newBooking);

		// Get our customer and show his profile
		String userName = session.getAttribute("username").toString();
		List<Customer> customerData = customerDAOImp.getCustomer(userName);

		// List all bookings of the user
		List<Booking> myBookings = bookingDAOImp.getBookingByUsername(customerData.get(0).getUsername());

		session.setAttribute("message", "Booking created successfully");

		return "redirect:/seeBookingCustomer";
	}

	/*
	 * @param: bookingId passing bookingid to delete
	 */
	@GetMapping("/deleteBookingCustomer")
	public String deleteRoom(@RequestParam(required = true) int bookingId, HttpSession session, Model model) {

		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Customer"))
			return "denied";

		// Delete Booking based on BookingID
		bookingDAOImp.deleteBooking(bookingId);

		// Get our customer username
		String userName = session.getAttribute("username").toString();
		List<Customer> customerData = customerDAOImp.getCustomer(userName);

		// List all bookings of the user
		List<Booking> myBookings = bookingDAOImp.getBookingByUsername(customerData.get(0).getUsername());

		// Add attributes
		session.setAttribute("message", "Deleted booking successfully");

		return "redirect:/seeBookingCustomer";

	}
	
	/**
	 * Delete booking Confirmation
	 * @param bookingId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/deleteBookingConfirmation")
	public String confirmationDeleteBooking(@RequestParam(required = true) int bookingId, HttpSession session, Model model) {
		
		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Customer"))
			return "denied";
		
		Booking bookingToDelete = bookingDAOImp.getBooking(bookingId);
		model.addAttribute("booking", bookingToDelete);
		
		return "deleteBooking";
	}
	

	/*@param: booking, session and Model. GET to list all bookings of a user
	 */
	@GetMapping("/seeBookingCustomer")
	public String seeBooking(HttpSession session, Model model) {

		// Check if user is logged in and if it is a Customer
		if (!user.hasValidSession(session))
			return "denied";
		else if (!session.getAttribute("userType").equals("Customer"))
			return "denied";

		// Get our customer and show his profile
		String userName = session.getAttribute("username").toString();
		List<Customer> customerData = customerDAOImp.getCustomer(userName);

		// Add booking on Database
		bookingDAOImp.getBookingByUsername(customerData.get(0).getUsername());

		// List all bookings of the user
		List<Booking> myBookings = bookingDAOImp.getBookingByUsername(customerData.get(0).getUsername());
		
		model.addAttribute("message", session.getAttribute("message"));
		session.removeAttribute("message");
		model.addAttribute("user", customerData.get(0));
		model.addAttribute("bookings", myBookings);

		return "seeBooking";

	}
	
	/*
	 * @param: passing roomType choosen on Room search
	 */
	private String getAmenities(String roomType) {
		List<RoomType> listRoomsByType = roomDAOImp.getRoomType(roomType);
		String[] amenitiesArr = listRoomsByType.get(0).getAmenities();
		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < amenitiesArr.length; i++) {
			if (i == amenitiesArr.length - 1)
				stringBuffer.append(amenitiesArr[i]);
			else
				stringBuffer.append(amenitiesArr[i] + ", ");
		}
		String amenities = stringBuffer.toString();
		return amenities;
	}

	/*
	 * @param: startDate - Arriving Date
	 * @param: endDate - Leaving Date
	 */
	private long getNights(String startDate, String endDate) {
		// Parsing the date
		LocalDate dateBefore = LocalDate.parse(startDate);
		LocalDate dateAfter = LocalDate.parse(endDate);

		// calculating number of days in between
		long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);

		// displaying the number of days
		return noOfDaysBetween;
	}

	/* @param: roomType and nights Calculate the price of the stay
	 */
	private double calculatePrice(String roomType, int nights) {
		double price;

		List<RoomType> listRoomsByType = roomDAOImp.getRoomType(roomType);
		double dailyPrice = listRoomsByType.get(0).getDailyPrice();

		price = dailyPrice * nights;

		return price;
	}
	
	/**
	 * Get list of amenities for form
	 */
	@ModelAttribute("amenitiesListSearch")
	public List<String> getAmenitiesSearch() {
		List<String> amenitiesList = new ArrayList<String>();
		amenitiesList.add("Disregard Amenities");

		RoomTypeController rtcontroller = new RoomTypeController();
		List<String> allAmenities = rtcontroller.getAmenities();
		amenitiesList.addAll(allAmenities);

		return amenitiesList;

	}

	/**
	 * Get list of roomTypes for form
	 */
	@ModelAttribute("roomTypesListItems")
	public List<String> getRoomTypeListItems() {
		List<String> roomTypesListItems = new ArrayList<String>();
		roomTypesListItems.add("");

		List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();

		for (RoomType roomType : roomTypes) {
			roomTypesListItems.add(roomType.getRoomType());
		}

		return roomTypesListItems;
	}

}
