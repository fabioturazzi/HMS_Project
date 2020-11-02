package com.csis3275.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.csis3275.model.BookingMapper;
import com.csis3275.model.Booking;

@Component
public class BookingDAOImpl {
	
	JdbcTemplate jdbcTemplate;
	Date date;
	
	/* private final String SQL_GET_ALL = "SELECT BOOKINGID,  ROOMNUMBER, CUSTOMERID, customers.FNAME, customers.LNAME, customers.EMAIL,  customers.PHONENUMBER, STATUS, PAID, BOOKINGDATESTART,  	BOOKINDDATEEND, CHECKINDATE,  CHECKOUTDATE, PAYMENTDATE, DATEOFCREATION, TOTALCOST, ROOMTYPE FROM bookings\r\n" + 
			"JOIN customers ON bookings.customerId = customers.id;"; */
	private final String SQL_GET_ALL = "SELECT * FROM bookings;";
	private final String SQL_CREATE_BOOKING = "INSERT INTO bookings(roomNumber, customerId, status, paid, bookingDateStart, bookindDateEnd, checkinDate, checkoutDate, paymentDate, dateOfCreation, totalCost, roomType) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String SQL_DELETE_BOOKING = "DELETE FROM bookings WHERE id = ?";
	private final String SQL_FIND_BOOKING = "SELECT * FROM bookings WHERE id = ?";
	private final String SQL_UPDATE_BOOKING = "UPDATE bookings set (roomNumber = ?, customerId = ?, status = ?, paid = ?, bookingDateStart = ?, bookindDateEnd = ?, checkinDate = ?, checkoutDate = ?, paymentDate = ?, dateOfCreation = ?, totalCost = ?, roomType = ? WHERE id = ?";
	
	@Autowired
	public BookingDAOImpl(DataSource dataSource)	{
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Booking> getAllBookings()	{
		return jdbcTemplate.query(SQL_GET_ALL, new BookingMapper());	
	}
	
	public List<Booking> getBooking(int id)	{
		return jdbcTemplate.query(SQL_FIND_BOOKING, new BookingMapper(), id);	
	}
	
	public boolean createBooking(Booking newBooking) {
		return jdbcTemplate.update(SQL_CREATE_BOOKING, newBooking.getRoomNumber(), newBooking.getCustomerId(), newBooking.getStatus(), newBooking.isPaid(), newBooking.getBookingDateStart(), newBooking.getBookindDateEnd(), newBooking.getCheckinDate(), newBooking.getCheckoutDate(), newBooking.getPaymentDate(), newBooking.getDateOfCreation(), newBooking.getTotalCost(), newBooking.getRoomType()) > 0;
	}
	
	public boolean updateBooking(Booking newBooking) {
		return jdbcTemplate.update(SQL_UPDATE_BOOKING, newBooking.getRoomNumber(), newBooking.getCustomerId(), newBooking.getStatus(), newBooking.isPaid(), newBooking.getBookingDateStart(), newBooking.getBookindDateEnd(), newBooking.getCheckinDate(), newBooking.getCheckoutDate(), newBooking.getPaymentDate(), newBooking.getDateOfCreation(), newBooking.getTotalCost(), newBooking.getRoomType()) > 0;
	}
	
	public boolean deleteBooking(int idToDelete) {
		return jdbcTemplate.update(SQL_DELETE_BOOKING, idToDelete) > 0;
	}
	
	
}
