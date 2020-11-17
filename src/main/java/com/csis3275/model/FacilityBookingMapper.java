package com.csis3275.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Hackermen
 * Hotel Management System
 */

public class FacilityBookingMapper implements RowMapper<FacilityBooking>{

	/**
	 *FacilityBooking mapRow Method
	 *@return facilityBooking
	 */	
	
	public FacilityBooking mapRow(ResultSet resultSet, int i) throws SQLException	{
		
		FacilityBooking facilityBooking = new FacilityBooking();
		facilityBooking.setFacilityBookingId(resultSet.getInt("facilityBookingId"));
		facilityBooking.setFacilityName(resultSet.getString("facilityName"));
		facilityBooking.setNumberOfPeople(resultSet.getInt("numberOfPeople"));
		facilityBooking.setDate(resultSet.getString("date"));
		facilityBooking.setTimeStart(resultSet.getString("timeStart"));
		facilityBooking.setTimeEnd(resultSet.getString("timeEnd"));
		facilityBooking.setCustomerUsername(resultSet.getString("customerUsername"));
		facilityBooking.setCorrespBookingId(resultSet.getInt("correspBookingId"));

		return facilityBooking;
		
	}

}