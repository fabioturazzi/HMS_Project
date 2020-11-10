package com.csis3275.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Hackermen
 * Hotel Management System
 */

public class RoomMapper implements RowMapper<Room> {

	/**
	 *Room mapRow Method
	 *@return room
	 */
	public Room mapRow(ResultSet resultSet, int i) throws SQLException {

		Room room = new Room();
		room.setRoomId(resultSet.getInt("roomId"));
		room.setHousekeepingStatus(resultSet.getString("housekeepingStatus"));
		room.setFloor(resultSet.getInt("floor"));
		room.setRoomType(resultSet.getString("roomType"));
		room.setRoomNumber(resultSet.getInt("roomNumber"));
		
		return room;

	}

}
