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


import com.csis3275.dao.BookingDAOImpl;
import com.csis3275.dao.RoomDAOImpl;
import com.csis3275.model.Booking;
import com.csis3275.model.Room;
import com.csis3275.model.RoomType;

@Controller
public class RoomSearchBookingController {

	@Autowired
	RoomDAOImpl roomDAOImp;

	@Autowired
	BookingDAOImpl bookingDAOImp;

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

		// Check dates logic (start date < end date)
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
			Date date = new Date(System.currentTimeMillis());

			session.setAttribute("message", "Search filter updated " + formatter.format(date));
		}

		return "redirect:/roomSearch";
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
