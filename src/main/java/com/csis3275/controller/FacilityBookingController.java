package com.csis3275.controller;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.csis3275.dao.FacilitiesDAOImpl;
import com.csis3275.dao.FacilityBookingDAOImpl;
import com.csis3275.model.Booking;
import com.csis3275.model.Customer;
import com.csis3275.model.Facilities;
import com.csis3275.model.FacilityBooking;

/**
 * @author Hackermen Hotel Management System
 */

@Controller
public class FacilityBookingController {

	@Autowired
	FacilityBookingDAOImpl facilityBookingDAOImp;

	@Autowired
	CustomerDAOImpl customerDAOImp;

	@Autowired
	BookingDAOImpl bookingDAOImp;

	@Autowired
	FacilitiesDAOImpl facilityDAOImp;

	@ModelAttribute("facilityBooking")
	public FacilityBooking setupAddForm() {
		return new FacilityBooking();
	}

	// Create object for the UserController
	UserController user = new UserController();

	// Get the facilityBooking and display the form
	@GetMapping("/deleteFacilityBooking")
	public String deleteFacilityBooking(@RequestParam(required = true) int id, HttpSession session, Model model) {

		// checking if user has a valid session hash and access
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

//	Arrays.asList(pe.amenities).contains(amenity);
	/**
	 * Post request to add entry to database
	 * 
	 * @throws ParseException
	 */
	// Handle Form Post
	@PostMapping("/createFacilityBooking")
	public String createFacilityBooking(@ModelAttribute("facilityBooking") FacilityBooking createFacilityBooking,
			HttpSession session, Model model) throws ParseException {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get a list of facilityBookings from the controller
		List<FacilityBooking> facilityBookings = facilityBookingDAOImp.getAllFacilityBookings();
		model.addAttribute("facilityBookingList", facilityBookings);

		// Get booking from the customer
		Booking correspBooking = bookingDAOImp.getBooking(createFacilityBooking.getCorrespBookingId());

		// Get username from selected booking Id
		createFacilityBooking.setCustomerUsername(correspBooking.getCustomerUsername());

		Date facilityBookingDate = new SimpleDateFormat("yyyy-MM-dd").parse(createFacilityBooking.getDate(),
				new ParsePosition(0));
		Date bookingStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(correspBooking.getBookingDateStart(),
				new ParsePosition(0));
		Date bookingEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(correspBooking.getBookindDateEnd(),
				new ParsePosition(0));

		// Check if facility booking date is contained in booking
		if (!(facilityBookingDate.compareTo(bookingStartDate) >= 0
				&& facilityBookingDate.compareTo(bookingEndDate) <= 0)) {
			model.addAttribute("errorMessage",
					"Please select a date for facility booking contained in this customer's room booking period ("
							+ correspBooking.getBookingDateStart() + "-" + correspBooking.getBookindDateEnd() + ").");
			return "facilityBookingManagement";
		}

		// Get facility from this booking
		Facilities facility = facilityDAOImp.getFacilityByName(createFacilityBooking.getFacilityName()).get(0);

		// Get all bookings on this day for this facility
		List<FacilityBooking> currBookings = facilityBookingDAOImp.getFacBookingFromFacility(createFacilityBooking);

		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date attemptStartTime = parser.parse(createFacilityBooking.getTimeStart());
		Date attemptEndTime = parser.parse(createFacilityBooking.getTimeEnd());
		Date existingStartTimes;
		Date existingEndTimes;

		if (attemptStartTime.after(attemptEndTime) || attemptStartTime.equals(attemptEndTime)) {
			model.addAttribute("errorMessage", "Please select and end time after the selected start time.");
			return "facilityBookingManagement";
		}

		if (facility.getFacilityType().equals("Restaurant")) {
			// For restaurants, get the list of current bookings from that facility on that
			// date. If list is not empty, check times
			if (!currBookings.isEmpty()) {

				// Variable to store the current number of people registered for the restaurant
				int restCurrOccupation = 0;
				for (FacilityBooking eachBooking : currBookings) {
					// Check time from each booking from that facility in that date
					existingStartTimes = parser.parse(eachBooking.getTimeStart());
					existingEndTimes = parser.parse(eachBooking.getTimeEnd());

					// Compare times from bookings to identify if any overlap occurs
					if (((attemptStartTime.before(existingStartTimes) || attemptStartTime.equals(existingStartTimes))
							&& attemptEndTime.after(existingStartTimes))
							|| (attemptStartTime.before(existingEndTimes) && (attemptEndTime.after(existingEndTimes)
									|| attemptEndTime.equals(existingEndTimes)))
							|| ((attemptStartTime.after(existingStartTimes)
									|| attemptStartTime.equals(existingStartTimes))
									&& (attemptEndTime.before(existingStartTimes)
											|| attemptEndTime.equals(existingStartTimes)))) {
						// If overlap occurs, add the number of people from the existing reservation to
						// the current restaurant's occupation
						restCurrOccupation += eachBooking.getNumberOfPeople();
					}
				}
				// Check if, by allowing the booking, the restaurant would now surpass it's max
				// capacity
				if ((restCurrOccupation + createFacilityBooking.getNumberOfPeople()) > facility.getCapacity()) {
					model.addAttribute("errorMessage",
							"The restaurant is already too full to book " + createFacilityBooking.getNumberOfPeople()
									+ ", there are " + (facility.getCapacity() - restCurrOccupation)
									+ " available slots.");
					return "facilityBookingManagement";
				}
			} else if (createFacilityBooking.getNumberOfPeople() > facility.getCapacity()) {
				// Don't allow booking if capacity is smaller than number of people
				model.addAttribute("errorMessage",
						"This Restaurant is too small for " + createFacilityBooking.getNumberOfPeople()
								+ " people, the max capacity for it is " + facility.getCapacity());
				return "facilityBookingManagement";
			}
		} else {
			// For meeting rooms, get the list of current bookings from that facility on
			// that date. If list is not empty, check times
			if (!currBookings.isEmpty()) {
				for (FacilityBooking eachBooking : currBookings) {
					// Check time from each booking from that facility in that date
					existingStartTimes = parser.parse(eachBooking.getTimeStart());
					existingEndTimes = parser.parse(eachBooking.getTimeEnd());

					// Compare times from bookings to identify if any overlap occurs
					if (((attemptStartTime.before(existingStartTimes) || attemptStartTime.equals(existingStartTimes))
							&& attemptEndTime.after(existingStartTimes))
							|| (attemptStartTime.before(existingEndTimes) && (attemptEndTime.after(existingEndTimes)
									|| attemptEndTime.equals(existingEndTimes)))
							|| ((attemptStartTime.after(existingStartTimes)
									|| attemptStartTime.equals(existingStartTimes))
									&& (attemptEndTime.before(existingStartTimes)
											|| attemptEndTime.equals(existingStartTimes)))) {

						// Don't allow user to register if overlap exists
						model.addAttribute("errorMessage", "This Meeting Room is already booked by " + eachBooking.getCustomerUsername() + " from " + eachBooking.getTimeStart() + " to " + eachBooking.getTimeEnd() + ".");
						return "facilityBookingManagement";

					}
				}
			}
			// Check number of people from the booking against the facility's capacity
			if (createFacilityBooking.getNumberOfPeople() > facility.getCapacity()) {
				// Don't allow booking if capacity is smaller than number of people
				model.addAttribute("errorMessage",
						"This Meeting Room is too small for " + createFacilityBooking.getNumberOfPeople()
								+ " people, the max capacity for it is " + facility.getCapacity());
				return "facilityBookingManagement";
			}
		}

		// Create the facilityBooking pass the object in.
		facilityBookingDAOImp.createFacilityBooking(createFacilityBooking);
		model.addAttribute("facilityBooking", createFacilityBooking);
		model.addAttribute("message", "Facility Booking created for " + createFacilityBooking.getCustomerUsername()
				+ " in " + createFacilityBooking.getFacilityName() + " on " + createFacilityBooking.getDate());

		// Get a list of facilityBookings from the controller
		facilityBookings.clear();
		facilityBookings = facilityBookingDAOImp.getAllFacilityBookings();
		model.addAttribute("facilityBookingList", facilityBookings);

		return "facilityBookingManagement";
	}

	@GetMapping("/facilityBookingManagement")
	public String showFacilityBookings(HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get a list of facilityBookings from the database
		List<FacilityBooking> facilityBookings = facilityBookingDAOImp.getAllFacilityBookings();

		// Add the list of facilityBookings to the model to be returned to the view
		model.addAttribute("facilityBookingList", facilityBookings);

		return "facilityBookingManagement";
	}

	/**
	 * Get request to get entry to be edited from database
	 */
	@GetMapping("/editFacilityBooking")
	public String editFacilityBooking(@RequestParam(required = true) int id, HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get the facilityBooking
		FacilityBooking updatedFacilityBooking = facilityBookingDAOImp.getFacilityBookingById(id);
		model.addAttribute("facilityBooking", updatedFacilityBooking);

		return "facilityBookingManagementEdit";
	}

	/**
	 * Post request to edit entry from database
	 * 
	 * @throws ParseException
	 */
	@PostMapping("/editFacilityBooking")
	public String updateFacilityBooking(@ModelAttribute("facilityBooking") FacilityBooking updatedFacilityBooking,
			HttpSession session, Model model) throws ParseException {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get booking from the customer
		Booking correspBooking = bookingDAOImp.getBooking(updatedFacilityBooking.getCorrespBookingId());

		// Get username from selected booking Id
		updatedFacilityBooking.setCustomerUsername(correspBooking.getCustomerUsername());

		Date facilityBookingDate = new SimpleDateFormat("yyyy-MM-dd").parse(updatedFacilityBooking.getDate(),
				new ParsePosition(0));
		Date bookingStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(correspBooking.getBookingDateStart(),
				new ParsePosition(0));
		Date bookingEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(correspBooking.getBookindDateEnd(),
				new ParsePosition(0));

		// Check if facility booking date is contained in booking
		if (!(facilityBookingDate.compareTo(bookingStartDate) >= 0
				&& facilityBookingDate.compareTo(bookingEndDate) <= 0)) {
			model.addAttribute("errorMessage",
					"Please select a date for facility booking contained in this customer's room booking period ("
							+ correspBooking.getBookingDateStart() + "-" + correspBooking.getBookindDateEnd() + ").");
			return "facilityBookingManagementEdit";
		}

		// Get facility from this booking
		Facilities facility = facilityDAOImp.getFacilityByName(updatedFacilityBooking.getFacilityName()).get(0);

		// Get all bookings on this day for this facility
		List<FacilityBooking> currBookings = facilityBookingDAOImp.getFacBookingFromFacility(updatedFacilityBooking);

		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date attemptStartTime = parser.parse(updatedFacilityBooking.getTimeStart());
		Date attemptEndTime = parser.parse(updatedFacilityBooking.getTimeEnd());
		Date existingStartTimes;
		Date existingEndTimes;

		if (attemptStartTime.after(attemptEndTime) || attemptStartTime.equals(attemptEndTime)) {
			model.addAttribute("errorMessage", "Please select and end time after the selected start time.");
			return "facilityBookingManagementEdit";
		}

		if (facility.getFacilityType().equals("Restaurant")) {
			// For restaurants, get the list of current bookings from that facility on that
			// date. If list is not empty, check times
			if (!currBookings.isEmpty()) {

				// Variable to store the current number of people registered for the restaurant
				int restCurrOccupation = 0;
				for (FacilityBooking eachBooking : currBookings) {
					// Check time from each booking from that facility in that date
					existingStartTimes = parser.parse(eachBooking.getTimeStart());
					existingEndTimes = parser.parse(eachBooking.getTimeEnd());

					// Compare times from bookings to identify if any overlap occurs
					if (((attemptStartTime.before(existingStartTimes) || attemptStartTime.equals(existingStartTimes))
							&& attemptEndTime.after(existingStartTimes))
							|| (attemptStartTime.before(existingEndTimes) && (attemptEndTime.after(existingEndTimes)
									|| attemptEndTime.equals(existingEndTimes)))
							|| ((attemptStartTime.after(existingStartTimes)
									|| attemptStartTime.equals(existingStartTimes))
									&& (attemptEndTime.before(existingStartTimes)
											|| attemptEndTime.equals(existingStartTimes)))) {
						// If overlap occurs, add the number of people from the existing reservation to
						// the current restaurant's occupation
						restCurrOccupation += eachBooking.getNumberOfPeople();
					}
				}
				// Check if, by allowing the booking, the restaurant would now surpass it's max
				// capacity
				if ((restCurrOccupation + updatedFacilityBooking.getNumberOfPeople()) > facility.getCapacity()) {
					model.addAttribute("errorMessage",
							"The restaurant is already too full to book " + updatedFacilityBooking.getNumberOfPeople()
									+ ", there are " + (facility.getCapacity() - restCurrOccupation)
									+ " available slots.");
					return "facilityBookingManagementEdit";
				}
			} else if (updatedFacilityBooking.getNumberOfPeople() > facility.getCapacity()) {
				// Don't allow booking if capacity is smaller than number of people
				model.addAttribute("errorMessage",
						"This Restaurant is too small for " + updatedFacilityBooking.getNumberOfPeople()
								+ " people, the max capacity for it is " + facility.getCapacity());
				return "facilityBookingManagement";
			}
		} else {
			// For meeting rooms, get the list of current bookings from that facility on
			// that date. If list is not empty, check times
			if (!currBookings.isEmpty()) {
				for (FacilityBooking eachBooking : currBookings) {
					// Check time from each booking from that facility in that date
					existingStartTimes = parser.parse(eachBooking.getTimeStart());
					existingEndTimes = parser.parse(eachBooking.getTimeEnd());

					// Compare times from bookings to identify if any overlap occurs
					if (((attemptStartTime.before(existingStartTimes) || attemptStartTime.equals(existingStartTimes))
							&& attemptEndTime.after(existingStartTimes))
							|| (attemptStartTime.before(existingEndTimes) && (attemptEndTime.after(existingEndTimes)
									|| attemptEndTime.equals(existingEndTimes)))
							|| ((attemptStartTime.after(existingStartTimes)
									|| attemptStartTime.equals(existingStartTimes))
									&& (attemptEndTime.before(existingStartTimes)
											|| attemptEndTime.equals(existingStartTimes)))) {
						if (!eachBooking.getCustomerUsername().equals(updatedFacilityBooking.getCustomerUsername())) {
							// Don't allow user to register if overlap exists
							model.addAttribute("errorMessage", "This Meeting Room is already booked  by " + eachBooking.getCustomerUsername() + "  from " + eachBooking.getTimeStart() + " to " + eachBooking.getTimeEnd() + ".");
							return "facilityBookingManagement";
						}
					}
				}
			}
			// Check number of people from the booking against the facility's capacity
			if (updatedFacilityBooking.getNumberOfPeople() > facility.getCapacity()) {
				// Don't allow booking if capacity is smaller than number of people
				model.addAttribute("errorMessage",
						"This Meeting Room is too small for " + updatedFacilityBooking.getNumberOfPeople()
								+ " people, the max capacity for it is " + facility.getCapacity());
				return "facilityBookingManagementEdit";
			}
		}

		facilityBookingDAOImp.updateFacilityBooking(updatedFacilityBooking);

		List<FacilityBooking> facilityBookings = facilityBookingDAOImp.getAllFacilityBookings();
		model.addAttribute("facilityBookingList", facilityBookings);
		model.addAttribute("message", "Updated Facility Booking " + updatedFacilityBooking.getFacilityBookingId());

		return "facilityBookingManagement";
	}

	/**
	 * Get the list of customers for the form
	 * 
	 * @return customerListItems used in the listbox to register a facilityBooking
	 */
	@ModelAttribute("customerListItems")
	public List<String> getCustomerListItems() {
		List<String> customerList = new ArrayList<String>();

		List<Customer> allCustomers = customerDAOImp.getAllCustomers();

		for (Customer customer : allCustomers) {
			customerList.add(customer.getUsername());
		}
		return customerList;
	}

	/**
	 * Get the list of booking for the form
	 * 
	 * @return bookingList used in the listbox to register a facilityBooking
	 */
	@ModelAttribute("bookingList")
	public List<Booking> getAllBookings() {

		List<Booking> allBookings = bookingDAOImp.getAllBookings();

		return allBookings;
	}

	/**
	 * Get the list of facilities for the form
	 * 
	 * @return facilityListItems used in the listbox to register a
	 *         facilityBooking
	 */
	@ModelAttribute("facilityListItems")
	public List<String> getFacilityListItems() {
		List<String> facilityListItems = new ArrayList<String>();

		List<Facilities> facilities = facilityDAOImp.getAllFacilities();

		for (Facilities facility : facilities) {
			facilityListItems.add(facility.getFacilityName());
		}

		return facilityListItems;
	}

	/**
	 * Get the list of time slots types for the form
	 * 
	 * @return timeSlots used in the listbox to register a
	 *         facilityBooking
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	@ModelAttribute("timeListItems")
	public List<String> getTimeListItems() throws ParseException {

		List<String> timeSlots = new ArrayList<String>();

		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date startTime = parser.parse("08:00");
		Date endTime = parser.parse("23:00");

		while (startTime.before(endTime) || startTime.equals(endTime)) {
			timeSlots.add(
					String.format("%02d", startTime.getHours()) + ":" + String.format("%02d", startTime.getMinutes()));
			if (startTime.getMinutes() == 30) {
				startTime.setMinutes(0);
				startTime.setHours(startTime.getHours() + 1);
			} else {
				startTime.setMinutes(30);
			}
		}

		return timeSlots;
	}

}
