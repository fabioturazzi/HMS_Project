package com.csis3275.controller;

import java.io.FileOutputStream;
import java.text.NumberFormat.Style;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
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

import com.csis3275.dao.BookingDAOImpl;
import com.csis3275.dao.CustomerDAOImpl;
import com.csis3275.dao.RoomDAOImpl;
import com.csis3275.model.Booking;
import com.csis3275.model.Customer;
import com.csis3275.model.Room;
import com.csis3275.model.RoomType;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Text;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Hackermen Hotel Management System
 */

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

	/**
	 * Get method to Show all bookings
	 * @param session
	 * @param model
	 * @return booking management page (list of all bookings)
	 */
	@GetMapping("/bookingManagement/bookings")
	public String showBookings(HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// Get a list of customers from the database
		List<Booking> bookings = bookingDAOImp.getAllBookings();

		setDropdownLists(model);

		// Add the list of customers to the model to be returned to the view
		model.addAttribute("bookings", bookings);

		return "bookingManagement";
	}
	
	/**
	 * Get method to Delete booking
	 * @param id
	 * @param session
	 * @param model
	 * @return booking management page (list of all bookings)
	 */

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

		// set dropdown lists
		setDropdownLists(model);

		model.addAttribute("message", "Deleted Booking: " + id);

		return "bookingManagement";
	}

	/**
	 * Post Method to create a new booking
	 * @param createBooking
	 * @param request
	 * @param session
	 * @param model
	 * @return booking management page (list of all bookings)
	 */
	@PostMapping("/createBooking")
	public String createRoom(@ModelAttribute("room") Booking createBooking, HttpServletRequest request,
			HttpSession session, Model model) {

		// set dropdown lists
		setDropdownLists(model);
		List<Booking> bookings = bookingDAOImp.getAllBookings();
		model.addAttribute("bookings", bookings);

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// checking if we have valid (not occupied) dates for a room
		String sd = createBooking.getBookingDateStart();
		String ed = createBooking.getBookindDateEnd();
		createBooking.setDateOfCreation(createBooking.setTodaysDate());
		String td = createBooking.getDateOfCreation();

		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(sd, new ParsePosition(0));
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(ed, new ParsePosition(0));
		Date today = new SimpleDateFormat("yyyy-MM-dd").parse(td, new ParsePosition(0));

		if (startDate.compareTo(today) < 0) {

			model.addAttribute("errorMessage", "Please, check the starting date.");
			return "/bookingManagement";

		} else {

			if (endDate.compareTo(startDate) < 0) {

				model.addAttribute("errorMessage", "Please, select correct ending date.");
				return "/bookingManagement";

			} else {

				int rNum = Integer.parseInt(request.getParameter("room"));

				List<Room> rooms = roomDAOImpl.getRoomByNumber(rNum);
				Room room = rooms.get(0);

				String rType = room.getRoomType();
				List<RoomType> roomTypes = roomDAOImpl.getRoomType(rType);
				int cap = roomTypes.get(0).getCapacity();

				int numOfPeople = createBooking.getNumbOfPeople();

				// check number of people in the form and room capacity
				if (cap < numOfPeople) {

					model.addAttribute("errorMessage",
							"Capacity of this room is insufficient. Max number of people is: " + cap + ".");
					return "/bookingManagement";

				} else {

					List<Booking> bookingList = bookingDAOImp.checkBookingConflict(rNum, sd, ed);

					if (!bookingList.isEmpty()) {

						model.addAttribute("errorMessage", "This room is not available for these dates.");
						return "/bookingManagement";

					} else {

						// set some fields depending on the input
						createBooking.setDateOfCreation(createBooking.setTodaysDate());
						createBooking.setStatus("booked");

						createBooking.setRoomNumber(Integer.parseInt(request.getParameter("room")));
						createBooking.setCustomerUsername(request.getParameter("customer"));

						createBooking.setRoomType(rType);

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

						List<Booking> bookingss = bookingDAOImp.getAllBookings();
						model.addAttribute("bookings", bookingss);

						return "/bookingManagement";
					}
				}
			}
		}
	}

	/**
	 * Get method to display booking to edit
	 * @param id
	 * @param session
	 * @param request
	 * @param model
	 * @return edit booking page
	 */
	@GetMapping("/editBooking")
	public String editBooking(@RequestParam(required = true) int id, HttpSession session, HttpServletRequest request,
			Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

//		if (request.getQueryString().toString() != null) {
//
//			String[] linkParts = request.getQueryString().toString().split("=");
//
//			if (linkParts.length > 1) {
//				String msgPart = linkParts[linkParts.length - 1].toString();
//
//				try {
//					
//				String[] msgParts = msgPart.split("+");
//				for (int i = 1; i <= msgParts.length - 1; i++) {
//					System.out.println(msgParts[i]);
//				}
//				} catch (Exception ex) {
//					System.out.println(ex.getMessage());
//				}
//
//			}
//
//		}
//
//		if (model.getAttribute("errorMessage") != null) {
//			System.out.println(request.getRequestURI());
//			System.out.println(session.getAttribute("errorMessage") + model.getAttribute("errorMessage").toString());
//		}

		Booking bookingToUpdate = bookingDAOImp.getBooking(id);
		model.addAttribute("booking", bookingToUpdate);
		// Get the customer
		Customer customer = customerDAOImp.getCustomer(bookingToUpdate.getCustomerUsername()).get(0);
		model.addAttribute("customer", customer);

		// checking if we have valid (not occupied) dates for a room
		String sd = bookingToUpdate.getBookingDateStart();
		String td = bookingToUpdate.setTodaysDate();
		String ed = bookingToUpdate.getBookindDateEnd();

		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(ed, new ParsePosition(0));
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(sd, new ParsePosition(0));
		Date todayDate = new SimpleDateFormat("yyyy-MM-dd").parse(td, new ParsePosition(0));

		if (todayDate.compareTo(startDate) < 0 || todayDate.compareTo(endDate) > 0) {
			model.addAttribute("canCheckin", false);
		} else {
			model.addAttribute("canCheckin", true);
		}

		setDropdownLists(model);

		return "bookingManagementEdit";
	}

	/**
	 * Post method to update booking
	 * @param updatedBooking
	 * @param request
	 * @param session
	 * @param model
	 * @return list of all bookings
	 */
	@PostMapping("/editBooking")
	public String updateBooking(@ModelAttribute("booking") Booking updatedBooking, HttpServletRequest request,
			HttpSession session, Model model) {

		// Get the customer
		Customer customer = customerDAOImp.getCustomer(updatedBooking.getCustomerUsername()).get(0);
		model.addAttribute("customer", customer);
		setDropdownLists(model);

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		// checking if we have valid (not occupied) dates for a room
		String sd = updatedBooking.getBookingDateStart();
		String ed = updatedBooking.getBookindDateEnd();

		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(sd, new ParsePosition(0));
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(ed, new ParsePosition(0));

		if (endDate.compareTo(startDate) < 0) {

			model.addAttribute("errorMessage", "Please, select correct ending date.");
			return "redirect:/editBooking/?id=" + updatedBooking.getBookingId();

		} else {

			System.out.println("Here");

			updatedBooking.setRoomNumber(Integer.parseInt(request.getParameter("room")));

			// getting the booked room
			List<Room> rooms = roomDAOImpl.getRoomByNumber(Integer.parseInt(request.getParameter("room")));
			Room room = rooms.get(0);

			updatedBooking.setRoomType(room.getRoomType());

			setDropdownLists(model);

			// Checking status and Putting dates if necessary
			String status = request.getParameter("status");
			System.out.println(status);

			if (status.equals("paid")) {

				if (updatedBooking.getPaymentDate() == "") {
					updatedBooking.setPaymentDate(updatedBooking.setTodaysDate());
					updatedBooking.setPaid(true);
				}

				updatedBooking.setCheckinDate(null);
				updatedBooking.setCheckoutDate(null);

			} else if (status.equals("checked-in")) {

				if (updatedBooking.getCheckinDate() == "")
					updatedBooking.setCheckinDate(updatedBooking.setTodaysDate());

				if (updatedBooking.getPaymentDate() == "") {
					updatedBooking.setPaymentDate(updatedBooking.setTodaysDate());
					updatedBooking.setPaid(true);
				}

				updatedBooking.setCheckoutDate(null);

			} else if (status.equals("checked-out")) {

				updatedBooking.setCheckoutDate(updatedBooking.setTodaysDate());
				String cd = updatedBooking.getCheckoutDate();
				Date checkoutDate = new SimpleDateFormat("yyyy-MM-dd").parse(cd, new ParsePosition(0));

				// if checkout was before ending date
				if (checkoutDate.compareTo(endDate) < 0) {
					ed = cd;
					updatedBooking.setBookindDateEnd(ed);
				}

			} else if (status.equals("booked")) {
				updatedBooking.setPaymentDate(null);
				updatedBooking.setCheckinDate(null);
				updatedBooking.setCheckoutDate(null);
			}

			// Calculate price
			long noOfDaysBetween = ChronoUnit.DAYS.between(LocalDate.parse(sd), LocalDate.parse(ed));

			if (noOfDaysBetween == 0) {
				noOfDaysBetween = 1;
			}

			RoomType roomType = roomDAOImpl.getRoomType(room.getRoomType()).get(0);

			double totalCost = noOfDaysBetween * roomType.getDailyPrice();
			updatedBooking.setTotalCost(totalCost);

			// New
			bookingDAOImp.updateBooking(updatedBooking);

			List<Booking> bookings = bookingDAOImp.getAllBookings();
			model.addAttribute("bookings", bookings);
			model.addAttribute("message", "Updated Booking " + updatedBooking.getBookingId());
			return "redirect:/bookingManagement/bookings";

		}

	}

	/**
	 * Get method to generate invoice
	 * @param id
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/generateInvoice")
	public String createInvoice(@RequestParam(required = true) int id, HttpSession session, HttpServletRequest request,
			Model model) {

		// Get the Booking
		Booking bookingToPrint = bookingDAOImp.getBooking(id);
		// Get the customer
		Customer customer = customerDAOImp.getCustomer(bookingToPrint.getCustomerUsername()).get(0);

		try {

			List<RoomType> roomTypes = roomDAOImpl.getRoomType(bookingToPrint.getRoomType());
			
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("invoice_booking_" + id + ".pdf"));

			Calendar d = Calendar.getInstance();
			d.get(Calendar.YEAR);
			
			document.open();
			
			Font headerFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
			Font textFont = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
			
			document.add(new Paragraph("Hackermen Hotel Management System", headerFont));
			document.add(new Paragraph(" ", textFont));
			
			document.add(new Paragraph("Invoice HHMS #" + id + "/" + bookingToPrint.getDateOfCreation(), headerFont));
			document.add(new Paragraph(" ", textFont));
			document.add(new Paragraph("Customer Information", textFont));
			
			PdfPTable table = new PdfPTable(2);

			table.setWidthPercentage(100);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(10f);
			
		    table.addCell("Name: ");
			table.addCell(customer.getfName() + " " + customer.getlName());

		    table.addCell("Address: ");
			table.addCell(customer.getAddress());
			
			table.addCell("Email: ");
			table.addCell(customer.getEmail());
			
			table.addCell("Phone: ");
			table.addCell(customer.getPhoneNumber());
			
			document.add(table);
			
			document.add(new Paragraph("Booking Information", textFont));
			
			PdfPTable bookingTable = new PdfPTable(2);

			bookingTable.setWidthPercentage(100);
			bookingTable.setSpacingBefore(10f);
			bookingTable.setSpacingAfter(10f);
			
			bookingTable.addCell("ID: ");
			bookingTable.addCell("#" + bookingToPrint.getBookingId());
			
			bookingTable.addCell("Booking Room Type: ");
			bookingTable.addCell(bookingToPrint.getRoomType());
			
			bookingTable.addCell("Room Number: ");
			bookingTable.addCell("" + bookingToPrint.getRoomNumber());
			
			bookingTable.addCell("Start Date: ");
			bookingTable.addCell(bookingToPrint.getBookingDateStart());
			
			bookingTable.addCell("End Date: ");
			bookingTable.addCell(bookingToPrint.getBookindDateEnd());
			
			bookingTable.addCell("Paid: ");
			String status;
			
			if (bookingToPrint.isPaid()) {
				status = "YES";
				bookingTable.addCell(status);
				bookingTable.addCell("Payment Date: ");
				bookingTable.addCell(bookingToPrint.getPaymentDate());
			} else {
				status = "NO";
				bookingTable.addCell(status);
			}
			
			document.add(bookingTable);
			
			document.add(new Paragraph("Total Cost", textFont));
			
			PdfPTable costTable = new PdfPTable(4);

			costTable.setWidthPercentage(100);
			costTable.setSpacingBefore(10f);
			costTable.setSpacingAfter(20f);

			costTable.addCell(new Paragraph("Price per Night", textFont));
			costTable.addCell(new Paragraph("Total Days", textFont));
			costTable.addCell(new Paragraph("Price", textFont));
			costTable.addCell(new Paragraph("Total Cost", textFont));
			

			costTable.addCell("" + roomTypes.get(0).getDailyPrice());
			
			long noOfDaysBetween = ChronoUnit.DAYS.between(LocalDate.parse(bookingToPrint.getBookingDateStart()), 
					LocalDate.parse(bookingToPrint.getBookindDateEnd()));

			if (noOfDaysBetween == 0) {
				noOfDaysBetween = 1;
			}

			double totalCost = noOfDaysBetween * roomTypes.get(0).getDailyPrice();
			double tax = 0.12;
			
			costTable.addCell("" + noOfDaysBetween);
			costTable.addCell("$" + totalCost);
			costTable.addCell("$" + Math.round(totalCost * (1 + tax)*100.0)/100.0);
 
			document.add(costTable);
			
			Paragraph date = new Paragraph("Date: " + d.get(Calendar.YEAR) + "-" +
					d.get(Calendar.MONTH) + "-" + d.get(Calendar.DAY_OF_MONTH), textFont);
			
			Paragraph managSign = new Paragraph("Manager", textFont);
			Paragraph custSign = new Paragraph(customer.getfName() + " " + customer.getlName(), textFont);
			
			Chunk signField = new Chunk("  X ____________________");
			
			document.add(date);
			document.add(managSign);
			document.add(signField);
			document.add(custSign);
			document.add(signField);
			
			document.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		model.addAttribute("message", "Created Invoice for Booking #" + bookingToPrint.getBookingId());
		setDropdownLists(model);
		List<Booking> bookings = bookingDAOImp.getAllBookings();
		model.addAttribute("bookings", bookings);
		return "/bookingManagement";
	}

	/**
	 * Sets dropdown lists for customers, rooms, and booking status
	 * @param model
	 */
	public void setDropdownLists(Model model) {

		List<Customer> custormersList = customerDAOImp.getAllCustomers();
		model.addAttribute("custormersList", custormersList);

		List<Room> roomList = roomDAOImpl.getAllRooms();
		model.addAttribute("roomList", roomList);

		List<String> roomStatus = Arrays.asList("booked", "paid", "checked-in", "checked-out");
		model.addAttribute("roomStatus", roomStatus);

	}

}
