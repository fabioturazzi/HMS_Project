package com.csis3275.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.csis3275.model.BookingMapper;
import com.csis3275.model.BookingModel;


public class BookingDAOImpl {
	
	JdbcTemplate jdbcTemplate;
	Date date;
	
	private final String SQL_GET_ALL = "SELECT BOOKINGID,  ROOMNUMBER, CUSTOMERID, customers.FNAME, customers.LNAME, customers.EMAIL,  customers.PHONENUMBER, STATUS, PAID, BOOKINGDATESTART,  	BOOKINDDATEEND, CHECKINDATE,  CHECKOUTDATE, PAYMENTDATE, DATEOFCREATION, TOTALCOST, ROOMTYPE FROM bookings\r\n" + 
			"JOIN customers ON bookings.customerId = customers.id;";
	private final String SQL_DELETE_BOOKING = "DELETE FROM bookings WHERE id = ?";
	private final String SQL_FIND_BOOKING = "SELECT * FROM bookings WHERE id = ?";
	private final String SQL_UPDATE_BOOKING = "UPDATE bookings set fName = ?, lName = ?, email = ?, address = ?, phoneNumber = ?, username = ?, registrationDate = ?, profileUpdated = ?, passQuestion = ?, passAnswer = ? WHERE id = ?";
	
	public List<BookingModel> getAllBookings()	{
		return jdbcTemplate.query(SQL_GET_ALL, new BookingMapper());	
	}
	
	public List<BookingModel> getBooking(int id)	{
		return jdbcTemplate.query(SQL_DELETE_BOOKING, new BookingMapper(), id);	
	}
}
