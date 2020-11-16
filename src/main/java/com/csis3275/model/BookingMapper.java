package com.csis3275.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Hackermen
 * Hotel Management System
 */

public class BookingMapper implements RowMapper<Booking>{
	
	/**
	 *Booking MapRow Method
	 *@return booking
	 */
	public Booking mapRow(ResultSet resultSet, int i) throws SQLException {
		
		Booking booking = new Booking();
		booking.setBookingId(resultSet.getInt("bookingId"));
		booking.setRoomNumber(resultSet.getInt("roomNumber"));
		booking.setCustomerUsername(resultSet.getString("customerUsername"));
		booking.setNumbOfPeople(resultSet.getInt("numbOfPeople"));
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
