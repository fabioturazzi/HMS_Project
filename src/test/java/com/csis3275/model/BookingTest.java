package com.csis3275.model;

/**
 * @author Daniil Volovik (300313470)
 * @description Unit testing for Booking object
 */
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookingTest {

	Booking booking;

	/**
	 * @param bookingNumber, bookingId, roomNumber, customerUsername, numbOfPeople,
	 *                       status, paid, bookingDateStart, bookindDateEnd,
	 *                       checkinDate, checkoutDate, paymentDate, dateOfCreation,
	 *                       totalCost, roomType;
	 * @throws Exception
	 */
	
	// Instantiate Booking object
	@Before
	public void setUp() throws Exception {
		booking = new Booking(12, 102, "PinkPony", "booked", false, "2020-11-25", "2020-11-30", null, null, null,
				"2020-10-16", 450.00, "Regular");
	}

	@After
	public void tearDown() throws Exception {
	}

	// Test getters for Booking object
	@Test
	public void getBookingId() {
		assertEquals(12, booking.getBookingId());
	}

	@Test
	public void getBookingRoomNum() {
		assertEquals(102, booking.getRoomNumber());
	}

	@Test
	public void getBookingCustomerUsername() {
		assertEquals("PinkPony", booking.getCustomerUsername());
	}

	@Test
	public void getBookingStatus() {
		assertEquals("booked", booking.getStatus());
	}

	@Test
	public void getPaidStatus() {
		assertFalse(booking.isPaid());
	}

	@Test
	public void getBookingStartDate() {
		assertEquals("2020-11-25", booking.getBookingDateStart());
	}

	@Test
	public void getBookingEndDate() {
		assertEquals("2020-11-30", booking.getBookindDateEnd());
	}

	@Test
	public void getCheckinDate() {
		assertEquals(null, booking.getCheckinDate());
	}

	@Test
	public void getCheckoutDate() {
		assertEquals(null, booking.getCheckoutDate());
	}

	@Test
	public void getPaymentDate() {
		assertEquals(null, booking.getPaymentDate());
	}

	@Test
	public void getDateOfCreation() {
		assertEquals("2020-10-16", booking.getDateOfCreation());
	}

	@Test
	public void getTotalCost() {
		assertEquals(450.00, booking.getTotalCost(), 0);
	}

	@Test
	public void getRoomType() {
		assertEquals("Regular", booking.getRoomType());
	}

	// Test setters for Booking object

	@Test
	public void setBookingId() {
		booking.setBookingId(123);
		assertEquals(123, booking.getBookingId());
	}

	@Test
	public void setBookingRoomNum() {
		booking.setRoomNumber(222);
		assertEquals(222, booking.getRoomNumber());
	}

	@Test
	public void setBookingCustomerUsername() {
		booking.setCustomerUsername("SamHill");
		assertEquals("SamHill", booking.getCustomerUsername());
	}

	@Test
	public void setBookingStatus() {
		booking.setStatus("checked-in");
		assertEquals("checked-in", booking.getStatus());
	}

	@Test
	public void setPaidStatus() {
		booking.setPaid(true);
		assertTrue(booking.isPaid());
	}

	@Test
	public void setBookingStartDate() {
		booking.setBookingDateStart("2020-12-24");
		assertEquals("2020-12-24", booking.getBookingDateStart());
	}

	@Test
	public void setBookingEndDate() {
		booking.setBookindDateEnd("2020-12-25");
		assertEquals("2020-12-25", booking.getBookindDateEnd());
	}

	@Test
	public void setCheckinDate() {
		booking.setCheckinDate("2020-12-24");
		assertEquals("2020-12-24", booking.getCheckinDate());
	}

	@Test
	public void setCheckoutDate() {
		booking.setCheckoutDate(null);
		assertEquals(null, booking.getCheckoutDate());
	}

	@Test
	public void setPaymentDate() {
		booking.setPaymentDate("2020-11-07");
		assertEquals("2020-11-07", booking.getPaymentDate());
	}

	@Test
	public void setDateOfCreation() {
		booking.setDateOfCreation("2020-11-05");
		assertEquals("2020-11-05", booking.getDateOfCreation());
	}

	@Test
	public void setTotalCost() {
		booking.setTotalCost(550);
		assertEquals(550, booking.getTotalCost(), 0);
	}

	@Test
	public void setRoomType() {
		booking.setRoomType("Premium");
		assertEquals("Premium", booking.getRoomType());
	}

}
