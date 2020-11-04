package com.csis3275.controller;

import java.text.ParseException;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.csis3275.dao.BookingDAOImpl;
import com.csis3275.dao.CustomerDAOImpl;
import com.csis3275.dao.RoomDAOImpl;
import com.csis3275.model.Booking;
import com.csis3275.model.Customer;
import com.csis3275.model.Room;
import com.csis3275.model.RoomType;
import com.csis3275.model.User;

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

		/*
		 * @author: Fernando Casaloti Silva Add start and end date to the view to be
		 * used on booking
		 */
		model.addAttribute("startDate", searchedRoomType.getStartDateFormControl().toString());
		model.addAttribute("endDate", searchedRoomType.getEndDateFormControl().toString());
		model.addAttribute("capacity", searchedRoomType.getCapacity());

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

	/*
	 * @author: Fernando Casaloti Silva
	 * @param: roomType passing from the search
	 * @param: startDate passing from the search
	 * @param: endDate passing from the search
	 * @param: session and model passing information of session and to manipulate Model.
	 */
	@GetMapping("/roomBooking")
	public String bookingRoom(@RequestParam(required = true) String roomType,
			@RequestParam(required = true) String startDate, @RequestParam(required = true) String endDate,
			@RequestParam(required = true) int capacity, HttpSession session, Model model) {

		//Check if user is logged in and if it is a Customer
		if (session.getAttribute("username") != null && session.getAttribute("userType").equals("Customer")) {
			
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
			
		} else {
			return "denied";
		}
		
	}

	/*
	 * @author: Fernando Casaloti Silva
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
	 * @author: Fernando Casaloti Silva
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

	/*
	 * @author: Fernando Casaloti Silva
	 * @param: roomType and nights
	 * Calculate the price of the stay
	 */
	private double calculatePrice(String roomType, int nights) {
		double price;

		List<RoomType> listRoomsByType = roomDAOImp.getRoomType(roomType);
		double dailyPrice = listRoomsByType.get(0).getDailyPrice();

		price = dailyPrice * nights;

		return price;
	}

	/*
	 * @author: Fernando Casaloti Silva 
	 * Edit user information on Booking Page
	 */
	@PostMapping("/profileBook")
	public String showEditProfile(@ModelAttribute("customerBook") Customer updatedUser, HttpSession session,
			Model model) {

		// Update customer information
		updatedUser.putProfileUpdated();
		customerDAOImp.updateCustomer(updatedUser);
		Customer customer = customerDAOImp.getCustomerById(updatedUser.getId());
		model.addAttribute("customerBook", customer);
		session.setAttribute("lastname", customer.getlName());

		return "roomSearch";
	}

	/*
	 * author: Fernando Casaloti Silva
	 * @param: roomType, capacity (number of People), startDate, endDate, price, and username.
	 */
	private Booking setNewBooking(String roomType, int capacity, String startDate, String endDate, double price,
			String userName) {

		Booking newBooking = new Booking();

		//Setting Data on Booking Model
		List<Room> listRoomsByType = roomDAOImp.getRoomsByRoomType(roomType);
		newBooking.setRoomNumber(listRoomsByType.get(0).getRoomNumber());
		newBooking.setCustomerUsername(userName);
		newBooking.setNumbOfPeople(capacity);
		newBooking.setStatus("null");
		newBooking.setPaid(false);
		newBooking.setBookingDateStart(startDate);
		newBooking.setBookindDateEnd(endDate);
		newBooking.setCheckinDate("1980-01-01");
		newBooking.setCheckoutDate("1980-01-02");
		newBooking.setPaymentDate("1980-01-01");
		newBooking.setTotalCost(price);
		newBooking.setRoomType(roomType);
		
		
		//Getting date of the Booking creation and setting on Model
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String logBookingTime = df.format(new Date());
		newBooking.setDateOfCreation(logBookingTime);

		return newBooking;
	}
	
	/*
	 * @author: Fernando Casaloti Silva
	 * @param: booking, session and Model.
	 * POST method to create a new Booking
	 */
	@PostMapping("/myBookings")
	public String myBookings(@ModelAttribute("booking") Booking newBooking, HttpSession session, Model model) {

		//Add booking on Database
		bookingDAOImp.createBooking(newBooking);
		
		
		//TODO: CREATE CODE TO INSERT IN A NEW PAGE CALLED MYBOOKINGS. CODE BELLOW IN THIS METHOD WILL BE OVERRIDEN.
		// Get our customer and show his profile
		String userName = session.getAttribute("username").toString();
		List<Customer> customerData = customerDAOImp.getCustomer(userName);
		
		model.addAttribute("user", customerData.get(0));

		return "profileView";
	}
}
