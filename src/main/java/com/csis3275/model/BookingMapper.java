package com.csis3275.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BookingMapper implements RowMapper<BookingModel>{
	
	public BookingModel mapRow(ResultSet resultSet, int i) throws SQLException {
		
		BookingModel booking = new BookingModel();
		booking.setBookingId(resultSet.getInt("bookingId"));
		booking.setRoomNumber(resultSet.getInt("roomNumber"));
		booking.setCustomerId(resultSet.getInt("customerId"));
		booking.setStatus(resultSet.getString("status"));
		booking.setPaid(resultSet.getBoolean("paid"));
		booking.setBookingDateStart(resultSet.getString("bookingDateStart"));
		booking.setBookindDateEnd(resultSet.getString("bookindDateEnd"));
		booking.setCheckinDate(resultSet.getString("checkinDate"));
		booking.setCheckoutDate(resultSet.getString("checkoutDate"));
		booking.setPaymentDate(resultSet.getString("paymentDate"));
		booking.setDateOfCreation(resultSet.getString("dateOfCreation"));
		booking.setTotalCost(resultSet.getDouble("totalCost"));
		booking.setRoomType(resultSet.getString("roomType"));

		return booking;

	}


}
