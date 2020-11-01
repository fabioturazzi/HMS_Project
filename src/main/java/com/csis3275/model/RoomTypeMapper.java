package com.csis3275.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RoomTypeMapper implements RowMapper<RoomType> {

	public RoomType mapRow(ResultSet resultSet, int i) throws SQLException {

		RoomType roomType = new RoomType();
		roomType.setRoomTypeId(resultSet.getInt("roomTypeId"));
		roomType.setRoomType(resultSet.getString("roomType"));
		roomType.setDailyPrice(resultSet.getDouble("dailyPrice"));
		roomType.setPhotos(resultSet.getString("photos").replace("[", "").replace("]", "").split(","));
		roomType.setAmenities(resultSet.getString("amenities").replace("[", "").replace("]", "").split(","));		
		
		return roomType;

	}

}
