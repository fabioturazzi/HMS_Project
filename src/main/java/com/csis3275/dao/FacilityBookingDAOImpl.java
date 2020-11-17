package com.csis3275.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.csis3275.model.Customer;
import com.csis3275.model.CustomerMapper;
import com.csis3275.model.FacilityBooking;
import com.csis3275.model.FacilityBookingMapper;
import com.csis3275.model.User;

/**
 * @author Hackermen
 * Hotel Management System
 */

@Component
public class FacilityBookingDAOImpl {

	JdbcTemplate jdbcTemplate;

	private final String SQL_GET_ALL = "SELECT * FROM facilityBookings";
	private final String SQL_GET_FAC_BOOKINGS_USER = "SELECT * FROM facilityBookings WHERE customerUsername = ?";
	private final String SQL_CREATE_FACILITYBOOKING = "INSERT INTO facilityBookings (facilityName, numberOfPeople, date, timeStart, timeEnd, customerUsername, correspBookingId) VALUES (?, ?, ?,to_date(?, 'hh:mm'), to_date(?, 'hh:mm'), ?, ?);";
	private final String SQL_DELETE_FACILITYBOOKING = "DELETE FROM facilityBookings WHERE facilityBookingId = ?";
	private final String SQL_UPDATE_FACILITYBOOKING = "UPDATE facilityBookings set facilityName = ?, numberOfPeople = ?, date = ?, timeStart = to_date(?, 'hh:mm'), timeEnd = to_date(?, 'hh:mm'), customerUsername = ?, correspBookingId = ? WHERE facilityBookingId = ?";
	private final String SQL_FIND_FACILITYBOOKING = "SELECT * FROM facilityBookings WHERE facilityBookingId = ?";

	/**
	 * DAO Methods
	 */
	
	@Autowired
	public FacilityBookingDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<FacilityBooking> getAllFacilityBookings() {
		return jdbcTemplate.query(SQL_GET_ALL, new FacilityBookingMapper());
	}

	public List<FacilityBooking> getFacilityBookingFromUser(String username) {
		return jdbcTemplate.query(SQL_GET_FAC_BOOKINGS_USER, new FacilityBookingMapper(), username);
	}

	public boolean createFacilityBooking(FacilityBooking newFacilityBooking) {
		return jdbcTemplate.update(SQL_CREATE_FACILITYBOOKING, newFacilityBooking.getFacilityName(), newFacilityBooking.getNumberOfPeople(),
				newFacilityBooking.getDate(), newFacilityBooking.getTimeStart(), newFacilityBooking.getTimeEnd(), newFacilityBooking.getCustomerUsername(),
				newFacilityBooking.getCorrespBookingId()) > 0;
	}

	public boolean deleteFacilityBooking(int idToDelete) {
		return jdbcTemplate.update(SQL_DELETE_FACILITYBOOKING, idToDelete) > 0;
	}

	public boolean updateFacilityBooking(FacilityBooking facilityBooking) {
		/**@return boolean indicating changes have been made to database */
		return jdbcTemplate.update(SQL_UPDATE_FACILITYBOOKING, facilityBooking.getFacilityName(), facilityBooking.getNumberOfPeople(),
				facilityBooking.getDate(), facilityBooking.getTimeStart(), facilityBooking.getTimeEnd(), facilityBooking.getCustomerUsername(),
				facilityBooking.getCorrespBookingId(), facilityBooking.getFacilityBookingId()) > 0;
	}

	public FacilityBooking getFacilityBookingById(int id) {
		/** @return an entry by its id */
		return jdbcTemplate.queryForObject(SQL_FIND_FACILITYBOOKING, new Object[] { id }, new FacilityBookingMapper());
	}


}