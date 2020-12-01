package com.csis3275.dao;

import static org.junit.Assert.*;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.csis3275.model.Booking;
import com.csis3275.model.Room;
import com.csis3275.model.User;

/**
 * @author Hackermen Hotel Management System - This class will be used to
 *         perform System Testing, considering the Booking feature. This will be done by 
 *         implementing the BookingDAOImpl, that combines Users, Bookings, Rooms and RoomTypes
 */

@RunWith(MockitoJUnitRunner.class)
public class BookingDAOImplTest {

	@Mock
	BookingDAOImpl bookingDAOImp;

	@Before
	public void setUp() throws Exception {
		//MockitoAnnotations.openMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	// This test tests methods to get all bookings from the database
	@Test
	public void getAllBookings() {

		List<Booking> listBooking = new ArrayList<>();

		Booking testBooking = new Booking(0, 201, "pedroUser", 2, "Booked", false, "2020-10-10", "2020-10-15", null, null, null, null, 1500, "Regular");
		Booking testBooking2 = new Booking(1, 201, "pedroUser", 2, "Booked", false, "2020-10-16", "2020-10-25", null, null, null, null, 1500, "Regular");

		listBooking.add(testBooking);
		listBooking.add(testBooking2);

		// MOCK ALERT: return mocked result set on find
		when(bookingDAOImp.getAllBookings()).thenReturn(listBooking);

		// call the main method you want to test
		List<Booking> result = bookingDAOImp.getAllBookings();

		// MOCK ALERT: verify the method was called
		verify(bookingDAOImp).getAllBookings();
		assertEquals(result.size(), listBooking.size());
	}
	
	// This test tests method to check booking conflict based on dates and room number in the database
	//It retrieves conflicting rooms based on any matches found
	@Test
	public void checkBookingConflict() {

		List<Booking> listBooking = new ArrayList<>();

		Booking testBooking = new Booking(0, 201, "pedroUser", 2, "Booked", false, "2020-10-10", "2020-10-15", null, null, null, null, 1500, "Regular");
		Booking testBooking2 = new Booking(1, 201, "pedroUser", 2, "Booked", false, "2020-10-16", "2020-10-25", null, null, null, null, 1500, "Regular");

		//To perform BOUNDARIES TESTING, dates on the verge of conflict will be used
		Booking newBooking = new Booking(1, 201, "pedroUser", 2, "Booked", false, "2020-10-24", "2020-10-27", null, null, null, null, 1500, "Regular");

		Date startDate;
		Date endDate;
		Date startDateCheck  = new SimpleDateFormat("yyyy-MM-dd").parse(newBooking.getBookingDateStart(), new ParsePosition(0));
		Date endDateCheck = new SimpleDateFormat("yyyy-MM-dd").parse(newBooking.getBookindDateEnd(), new ParsePosition(0));
		
		listBooking.add(testBooking);
		listBooking.add(testBooking2);
		
		for(Booking booking:listBooking) {
			startDate  = new SimpleDateFormat("yyyy-MM-dd").parse(booking.getBookingDateStart(), new ParsePosition(0));
			endDate = new SimpleDateFormat("yyyy-MM-dd").parse(booking.getBookingDateStart(), new ParsePosition(0));
			if(!(booking.getRoomNumber() == newBooking.getRoomNumber() && (((startDateCheck.compareTo(startDate)>=0) && (endDateCheck.compareTo(startDate) <=0)) || ((startDateCheck.compareTo(endDate) >=0 && endDateCheck.compareTo(endDate) <=0) ||  ((startDateCheck.compareTo(startDate)>=0 && endDateCheck.compareTo(endDate) <=0)))))) {
				listBooking.remove(booking);
			}
				
		}

		// MOCK ALERT: return mocked result set on find
		when(bookingDAOImp.checkBookingConflict(newBooking.getRoomNumber(), newBooking.getBookingDateStart(), newBooking.getBookindDateEnd())).thenReturn(listBooking);

		// call the main method you want to test
		List<Booking> result = bookingDAOImp.checkBookingConflict(newBooking.getRoomNumber(), newBooking.getBookingDateStart(), newBooking.getBookindDateEnd());

		// MOCK ALERT: verify the method was called
		verify(bookingDAOImp).checkBookingConflict(newBooking.getRoomNumber(), newBooking.getBookingDateStart(), newBooking.getBookindDateEnd());
		assertEquals(result.size(), listBooking.size());
	}
	
//	/*This test will verify the createBooking method,
//	 * This method creates a Booking and returns a boolean confirming addition was made
//	 */
	@Test
	public void createBooking() {
		
		User user = new User("pedroUser", "Pass", "Pedro", "Silva", "Customer", "Question?", "Answer");
		Room testRoom = new Room(202,"Regular", 2, "Clean", 0);
		
		Booking testBooking = new Booking(0, testRoom.getRoomNumber(), user.getUsername(), 2, "Booked", false, "2020-10-10", "2020-10-15", null, null, null, null, 1500, testRoom.getRoomType());
	
		// MOCK ALERT: return mocked result set on find
		when(bookingDAOImp.createBooking(testBooking)).thenReturn(true);

		// call the main method you want to test
		bookingDAOImp.createBooking(testBooking);

		// MOCK ALERT: verify the method was called
		verify(bookingDAOImp).createBooking(testBooking);
	}
	
//	/*This test will verify the deleteBooking method,
//	 * This method deletes a Booking and returns a boolean confirming change was made
//	 */
	@Test
	public void deleteBooking() {

		Booking testBooking = new Booking(0, 201, "pedroUser", 2, "Booked", false, "2020-10-10", "2020-10-15", null, null, null, null, 1500, "Regular");

		// MOCK ALERT: return mocked result set on find
		when(bookingDAOImp.deleteBooking(testBooking.getBookingId())).thenReturn(true);

		// call the main method you want to test
		bookingDAOImp.deleteBooking(0);

		// MOCK ALERT: verify the method was called
		verify(bookingDAOImp).deleteBooking(0);
	}
	
//	/*This test will verify the updateBooking method,
//	 * This method updates a Booking and returns a boolean confirming change was made
//	 */
	@Test
	public void updateBooking() {
		
		Booking testBooking = new Booking(0, 201, "pedroUser", 2, "Booked", false, "2020-10-10", "2020-10-15", null, null, null, null, 1500, "Regular");

		// MOCK ALERT: return mocked result set on find
		when(bookingDAOImp.updateBooking(testBooking)).thenReturn(true);

		// call the main method you want to test
		bookingDAOImp.updateBooking(testBooking);

		// MOCK ALERT: verify the method was called
		verify(bookingDAOImp).updateBooking(testBooking);
	}
	
	// This test tests methods to get a booking from the database by its id
	@Test
	public void getBooking() {

		Booking testBooking = new Booking(0, 201, "pedroUser", 2, "Booked", false, "2020-10-10", "2020-10-15", null, null, null, null, 1500, "Regular");

		// MOCK ALERT: return mocked result set on find
		when(bookingDAOImp.getBooking(testBooking.getBookingId())).thenReturn(testBooking);

		// call the main method you want to test
		Booking result = bookingDAOImp.getBooking(0);

		// MOCK ALERT: verify the method was called
		verify(bookingDAOImp).getBooking(0);
		assertEquals(result, testBooking);
	}
	
	// This test tests methods to get all bookings from the database by username
	@Test
	public void getBookingByUsername() {

		List<Booking> listBooking = new ArrayList<>();
		
		User user = new User("pedroUser", "Pass", "Pedro", "Silva", "Customer", "Question?", "Answer");

		Booking testBooking = new Booking(0, 201, user.getUsername(), 2, "Booked", false, "2020-10-10", "2020-10-15", null, null, null, null, 1500, "Regular");
		Booking testBooking2 = new Booking(1, 201, user.getUsername(), 2, "Booked", false, "2020-10-16", "2020-10-25", null, null, null, null, 1500, "Regular");

		listBooking.add(testBooking);
		listBooking.add(testBooking2);

		// MOCK ALERT: return mocked result set on find
		when(bookingDAOImp.getBookingByUsername(testBooking.getCustomerUsername())).thenReturn(listBooking);

		// call the main method you want to test
		List<Booking> result = bookingDAOImp.getBookingByUsername("pedroUser");

		// MOCK ALERT: verify the method was called
		verify(bookingDAOImp).getBookingByUsername("pedroUser");
		assertEquals(result.size(), listBooking.size());
	}
	
	// This test will check the complete workflow of Bookings, checking how the complete system behaves when placing a booking
	//The test checks the system for previous bookings on conflicting dates, then attempts to book a room in a date that is already busy, and lastly books a room for a new date that is free.
	@Test
	public void sysTestingBookingWorkflow() {

		List<Booking> listBooking = new ArrayList<>();

		Booking previousBooking1 = new Booking(0, 201, "pedroUser", 2, "Booked", false, "2020-10-10", "2020-10-15", null, null, null, null, 1500, "Regular");
		Booking previousBooking2 = new Booking(1, 201, "pedroUser", 2, "Booked", false, "2020-10-16", "2020-10-25", null, null, null, null, 1500, "Regular");

		//To perform BOUNDARIES TESTING, dates on the verge of conflict will be used
		Booking newBooking = new Booking(1, 201, "pedroUser", 2, "Booked", false, "2020-10-24", "2020-10-27", null, null, null, null, 1500, "Regular");

		Date startDate;
		Date endDate;
		Date startDateCheck  = new SimpleDateFormat("yyyy-MM-dd").parse(newBooking.getBookingDateStart(), new ParsePosition(0));
		Date endDateCheck = new SimpleDateFormat("yyyy-MM-dd").parse(newBooking.getBookindDateEnd(), new ParsePosition(0));
		
		listBooking.add(previousBooking1);
		listBooking.add(previousBooking2);
		
		for(Booking booking:listBooking) {
			startDate  = new SimpleDateFormat("yyyy-MM-dd").parse(booking.getBookingDateStart(), new ParsePosition(0));
			endDate = new SimpleDateFormat("yyyy-MM-dd").parse(booking.getBookingDateStart(), new ParsePosition(0));
			if(!(booking.getRoomNumber() == newBooking.getRoomNumber() && (((startDateCheck.compareTo(startDate)>=0) && (endDateCheck.compareTo(startDate) <=0)) || ((startDateCheck.compareTo(endDate) >=0 && endDateCheck.compareTo(endDate) <=0) ||  ((startDateCheck.compareTo(startDate)>=0 && endDateCheck.compareTo(endDate) <=0)))))) {
				listBooking.remove(booking);
			}
				
		}

		// MOCK ALERT: return mocked result set on find
		when(bookingDAOImp.checkBookingConflict(newBooking.getRoomNumber(), newBooking.getBookingDateStart(), newBooking.getBookindDateEnd())).thenReturn(listBooking);

		// call the main method you want to test
		List<Booking> result = bookingDAOImp.checkBookingConflict(newBooking.getRoomNumber(), newBooking.getBookingDateStart(), newBooking.getBookindDateEnd());

		// MOCK ALERT: verify the method was called
		verify(bookingDAOImp).checkBookingConflict(newBooking.getRoomNumber(), newBooking.getBookingDateStart(), newBooking.getBookindDateEnd());
		assertEquals(result.size(), listBooking.size());
		
		User user = new User("pedroUser", "Pass", "Pedro", "Silva", "Customer", "Question?", "Answer");
		Room testRoom = new Room(202,"Regular", 2, "Clean", 0);
		
		Booking testConflictingBooking = new Booking(0, testRoom.getRoomNumber(), user.getUsername(), 2, "Booked", false, "2020-10-10", "2020-10-15", null, null, null, null, 1500, testRoom.getRoomType());
	
		// MOCK ALERT: return mocked result set on find
		when(bookingDAOImp.createBooking(testConflictingBooking)).thenReturn(false);

		// call the main method you want to test
		bookingDAOImp.createBooking(testConflictingBooking);

		// MOCK ALERT: verify the method was called
		verify(bookingDAOImp).createBooking(testConflictingBooking);
		
		assertFalse(bookingDAOImp.createBooking(testConflictingBooking));
		
		Booking testNewBooking = new Booking(0, testRoom.getRoomNumber(), user.getUsername(), 2, "Booked", false, "2020-12-10", "2020-12-15", null, null, null, null, 1500, testRoom.getRoomType());
		
		// MOCK ALERT: return mocked result set on find
		when(bookingDAOImp.createBooking(testNewBooking)).thenReturn(true);

		// call the main method you want to test
		bookingDAOImp.createBooking(testNewBooking);

		// MOCK ALERT: verify the method was called
		verify(bookingDAOImp).createBooking(testNewBooking);
		
		assertTrue(bookingDAOImp.createBooking(testNewBooking));
	}
	
}