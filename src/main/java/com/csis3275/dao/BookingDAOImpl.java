package com.csis3275.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.csis3275.model.BookingMapper;
import com.csis3275.model.CustomerMapper;
import com.csis3275.model.RoomMapper;
import com.csis3275.model.Booking;

/**
 * @author Hackermen
 * Hotel Management System
 */

@Component
public class BookingDAOImpl {
	
	JdbcTemplate jdbcTemplate;
	Date date;
	
	/* private final String SQL_GET_ALL = "SELECT BOOKINGID,  ROOMNUMBER, CUSTOMERID, customers.FNAME, customers.LNAME, customers.EMAIL,  customers.PHONENUMBER, STATUS, PAID, BOOKINGDATESTART,  	BOOKINDDATEEND, CHECKINDATE,  CHECKOUTDATE, PAYMENTDATE, DATEOFCREATION, TOTALCOST, ROOMTYPE FROM bookings\r\n" + 
			"JOIN customers ON bookings.customerId = customers.id;"; */
	private final String SQL_GET_ALL = "SELECT * FROM bookings;";
	private final String SQL_CREATE_BOOKING = "INSERT INTO bookings(roomNumber, customerUsername, numbOfPeople, status, paid, bookingDateStart, bookindDateEnd, checkinDate, checkoutDate, paymentDate, dateOfCreation, totalCost, roomType) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String SQL_DELETE_BOOKING = "DELETE FROM bookings WHERE bookingId = ?";
	private final String SQL_FIND_BOOKING = "SELECT * FROM bookings WHERE bookingId = ?";
	private final String SQL_UPDATE_BOOKING = "UPDATE bookings set roomNumber = ?, customerUsername = ?, numbOfPeople  = ?, status = ?, paid = ?, bookingDateStart = ?, bookindDateEnd = ?, checkinDate = ?, checkoutDate = ?, paymentDate = ?, dateOfCreation = ?, totalCost = ?, roomType = ? WHERE bookingId = ?";
//	private final String SQL_CHECK_BOOKING_DATES = "SELECT * FROM bookings WHERE roomNumber = ? AND (bookingDateStart BETWEEN ? AND  ?) OR (bookindDateEnd BETWEEN ? AND ?) OR (bookingDateStart <= ? AND bookindDateEnd >= ?)";
	private final String SQL_CHECK_BOOKING_DATES = "SELECT * FROM bookings WHERE roomNumber = ? AND ((bookingDateStart >= ? AND bookingDateStart <= ?) OR (bookindDateEnd >= ? AND bookindDateEnd <= ?) OR (bookingDateStart <= ? AND bookindDateEnd >= ?))";
	private final String SQL_FIND_BOOKING_BY_USERNAME = "SELECT * FROM bookings WHERE customerUsername = ?";

	/**
	 * DAO Methods
	 * 
	 */
	
	@Autowired
	public BookingDAOImpl (DataSource dataSource)	{
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Booking> checkBookingConflict(int roomNumber, String attemptedStartDate, String attemptedEndDate)	{
		return jdbcTemplate.query(SQL_CHECK_BOOKING_DATES, new BookingMapper(), roomNumber, attemptedStartDate, attemptedEndDate, attemptedStartDate, attemptedEndDate, attemptedStartDate, attemptedEndDate);	
	}
	
	public List<Booking> getAllBookings()	{
		return jdbcTemplate.query(SQL_GET_ALL, new BookingMapper());	
	}
	
	public Booking getBooking(int id)	{
		return jdbcTemplate.queryForObject(SQL_FIND_BOOKING, new Object[] { id },new BookingMapper());	
	}
	
	public boolean createBooking(Booking newBooking) {
		return jdbcTemplate.update(SQL_CREATE_BOOKING, newBooking.getRoomNumber(), newBooking.getCustomerUsername(), newBooking.getNumbOfPeople(), newBooking.getStatus(), newBooking.isPaid(), newBooking.getBookingDateStart(), newBooking.getBookindDateEnd(), newBooking.getCheckinDate(), newBooking.getCheckoutDate(), newBooking.getPaymentDate(), newBooking.getDateOfCreation(), newBooking.getTotalCost(), newBooking.getRoomType()) > 0;
	}
	
	public boolean updateBooking(Booking newBooking) {
		return jdbcTemplate.update(SQL_UPDATE_BOOKING, newBooking.getRoomNumber(), newBooking.getCustomerUsername(), newBooking.getNumbOfPeople(), newBooking.getStatus(), newBooking.isPaid(), newBooking.getBookingDateStart(), newBooking.getBookindDateEnd(), newBooking.getCheckinDate(), newBooking.getCheckoutDate(), newBooking.getPaymentDate(), newBooking.getDateOfCreation(), newBooking.getTotalCost(), newBooking.getRoomType(), newBooking.getBookingId()) > 0;
	}
	
	public boolean deleteBooking(int idToDelete) {
		return jdbcTemplate.update(SQL_DELETE_BOOKING, idToDelete) > 0;
	}
	
	public List<Booking> getBookingByUsername(String customerUsername)	{
		return jdbcTemplate.query(SQL_FIND_BOOKING_BY_USERNAME, new BookingMapper(), customerUsername);	
	}
	
}
