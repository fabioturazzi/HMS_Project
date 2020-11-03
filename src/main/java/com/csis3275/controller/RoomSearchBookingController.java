package com.csis3275.controller;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.csis3275.dao.BookingDAOImpl;
import com.csis3275.dao.RoomDAOImpl;
import com.csis3275.model.Booking;
import com.csis3275.model.Customer;
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

	@GetMapping("/roomSearch")
	public String searchRooms(HttpSession session, Model model) {

		model.addAttribute("amenitiesListSearch", getAmenitiesSearch());

		// Get a list of roomTypes from the database
		List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();
		List<Integer> availableRooms = new ArrayList<>();

		for (int i = 0; i < roomTypes.size(); i++) {
			availableRooms.add(roomDAOImp.getRoomsByRoomType(roomTypes.get(i).getRoomType()).size());
		}

		model.addAttribute("availableRooms", availableRooms);

		// Add the list of roomTypes to the model to be returned to the view
		model.addAttribute("roomTypeList", roomTypes);

		return "roomSearch";
	}

	@PostMapping("/roomSearch")
	public String applySearchRoomTypes(HttpSession session, Model model,
			@ModelAttribute("roomType") RoomType searchedRoomType) {

		model.addAttribute("amenitiesListSearch", getAmenitiesSearch());

		List<RoomType> firstFilterCheck = new ArrayList<RoomType>();

		if (searchedRoomType.getRoomType().isBlank()) {
			firstFilterCheck = roomDAOImp.checkRoomTypeBooking(searchedRoomType.getCapacity());
		} else {
			firstFilterCheck = roomDAOImp.checkRoomTypeBooking(searchedRoomType.getCapacity(),
					searchedRoomType.getRoomType());
		}

		List<RoomType> secondFilterCheck = new ArrayList<RoomType>();

		if (searchedRoomType.getAmenities().length > 0
				&& !searchedRoomType.getAmenities()[0].equals("Disregard Amenities")) {

			boolean testAmenities = true;

			for (int i = 0; i < firstFilterCheck.size(); i++) {
				for (int j = 0; j < searchedRoomType.getAmenities().length; j++) {
					if (!Arrays.asList(firstFilterCheck.get(i).getAmenities())
							.contains(searchedRoomType.getAmenities()[j])) {
						testAmenities = false;
					}
				}
				if (testAmenities) {
					secondFilterCheck.add(firstFilterCheck.get(i));
				}
				testAmenities = true;
			}
		} else {
			secondFilterCheck = firstFilterCheck;
		}

		// Check available rooms
		List<Room> availableRoomsByType = new ArrayList<>();
		List<Room> allRoomsByType = new ArrayList<>();
		List<Booking> checkBookings = new ArrayList<>();
		List<Integer> availableRooms = new ArrayList<>();

		for (int i = 0; i < secondFilterCheck.size(); i++) {
			System.out.println(searchedRoomType.getStartDateFormControl());

			allRoomsByType.clear();
			allRoomsByType = roomDAOImp.getRoomsByRoomType(secondFilterCheck.get(i).getRoomType());

			availableRoomsByType.clear();

			for (int j = 0; j < allRoomsByType.size(); j++) {
				checkBookings = bookingDAOImp.checkBookingConflict(allRoomsByType.get(j).getRoomNumber(),
						searchedRoomType.getStartDateFormControl(), searchedRoomType.getEndDateFormControl());

				if (checkBookings.isEmpty()) {
					availableRoomsByType.add(allRoomsByType.get(j));
				}
			}
			availableRooms.add(availableRoomsByType.size());
		}

		
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchedRoomType.getStartDateFormControl(),
				new ParsePosition(0));
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchedRoomType.getEndDateFormControl(),
				new ParsePosition(0));

		if (endDate.compareTo(startDate) < 0) {

			model.addAttribute("errorMessage", "Please select an end date after the selected start date.");
			model.addAttribute("roomTypeList", secondFilterCheck);
			model.addAttribute("availableRooms", availableRooms);
			// Get a list of roomTypes from the database
			List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();
			// Add the list of roomTypes to the model to be returned to the view
			model.addAttribute("roomTypeList", roomTypes);
			return "roomSearch";
		}
		
//		List<Booking> bookings = bookingDAOImp.checkBookingConflict(roomNumber, attemptedStartDate, attemptedEndDate);

		model.addAttribute("availableRooms", availableRooms);

		// Add the list of roomTypes to the model to be returned to the view
		model.addAttribute("roomTypeList", secondFilterCheck);
		model.addAttribute("message", "Search filter updated");

		return "roomSearch";
	}

	@ModelAttribute("amenitiesListSearch")
	public List<String> getAmenitiesSearch() {
		List<String> amenitiesList = new ArrayList<String>();
		amenitiesList.add("Disregard Amenities");
		amenitiesList.add("Air Conditioner");
		amenitiesList.add("Heating");
		amenitiesList.add("King Size Bed");
		amenitiesList.add("Bath Tub");
		amenitiesList.add("Porch");
		amenitiesList.add("Room Service");
		amenitiesList.add("Wi-Fi");

		return amenitiesList;
	}
}
