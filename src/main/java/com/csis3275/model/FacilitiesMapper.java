package com.csis3275.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Hackermen
 * Hotel Management System
 */

public class FacilitiesMapper implements RowMapper<Facilities>{

	/**
	 * Facilities MapRow Method
	 */
	public Facilities mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Facilities facility = new Facilities();
		facility.setFacilityId(resultSet.getInt("facilityid"));
		facility.setFacilityName(resultSet.getString("facilityname"));
		facility.setFacilityType(resultSet.getString("facilitytype"));
		facility.setCapacity(resultSet.getInt("capacity"));		
		return facility;
	}

}
