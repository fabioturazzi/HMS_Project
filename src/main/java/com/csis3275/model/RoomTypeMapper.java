package com.csis3275.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Hackermen
 * Hotel Management System
 */

public class RoomTypeMapper implements RowMapper<RoomType> {

	/**
	 *RoomType mapRow Method
	 *@return roomType
	 */
	public RoomType mapRow(ResultSet resultSet, int i) throws SQLException {

		RoomType roomType = new RoomType();
		roomType.setRoomTypeId(resultSet.getInt("roomTypeId"));
		roomType.setRoomType(resultSet.getString("roomType"));
		roomType.setDailyPrice(resultSet.getDouble("dailyPrice"));
		roomType.setPhotos(resultSet.getString("photos").replace("[", "").replace("]", "").split(", "));
		roomType.setAmenities(resultSet.getString("amenities").replace("[", "").replace("]", "").split(", "));
		roomType.setCapacity(resultSet.getInt("capacity"));
		roomType.setStartDateFormControl(resultSet.getString("startDateFormControl"));
		roomType.setEndDateFormControl(resultSet.getString("endDateFormControl"));
		
		return roomType;

	}

}
