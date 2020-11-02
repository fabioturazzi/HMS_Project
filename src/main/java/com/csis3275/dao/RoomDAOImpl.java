package com.csis3275.dao;

import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.csis3275.model.Customer;
import com.csis3275.model.CustomerMapper;
import com.csis3275.model.Room;
import com.csis3275.model.RoomMapper;
import com.csis3275.model.RoomType;
import com.csis3275.model.RoomTypeMapper;

@Component
public class RoomDAOImpl {

	JdbcTemplate jdbcTemplate;
	Date date;

//	CREATE TABLE rooms (
//			 roomId INTEGER PRIMARY KEY AUTO_INCREMENT,
//			 roomNumber INTEGER,
//			 roomType VARCHAR(30) REFERENCES roomTypes(roomType),
//			 floor INTEGER,
//			 housekeepingStatus VARCHAR(50)
//			 );

//	CREATE TABLE roomTypes (
//			 roomTypeId INTEGER PRIMARY KEY AUTO_INCREMENT,
//			 roomType VARCHAR(30),
//			 photos VARCHAR(60),
//			 dailyPrice DOUBLE,
//			 amenities VARCHAR(100)
//			 );

	private final String SQL_GET_ALL_ROOMS = "SELECT * FROM rooms";
	private final String SQL_GET_ALL_ROOMTYPES = "SELECT * FROM roomTypes";
	
	private final String SQL_GET_ROOM_BY_NUMBER = "SELECT * FROM rooms WHERE roomNumber = ?";
	private final String SQL_GET_ROOMTYPE_BY_TYPE = "SELECT * FROM roomTypes WHERE roomType = ?";
	
	private final String SQL_GET_ROOM_BY_ID = "SELECT * FROM rooms WHERE roomId = ?";
	private final String SQL_GET_ROOMTYPE_BY_ID = "SELECT * FROM roomTypes WHERE roomTypeId = ?";
	
	private final String SQL_CHECK_ROOM_CONFLICT = "SELECT * FROM rooms WHERE roomId != ? AND roomNumber = ?";
	private final String SQL_CHECK_ROOMTYPE_CONFLICT = "SELECT * FROM roomTypes WHERE roomTypeId != ? AND roomType = ?";
	
	private final String SQL_GET_ROOMS_BY_TYPE = "SELECT * FROM rooms WHERE roomType = ?";

	private final String SQL_CREATE_ROOMS = "INSERT INTO rooms (roomNumber, roomType, floor, houseKeepingStatus) VALUES (?,?,?,?)";
	private final String SQL_CREATE_ROOMTYPES = "INSERT INTO roomTypes (roomType, photos, dailyPrice, amenities, capacity) VALUES (?,?,?,?,?)";
	
	private final String SQL_DELETE_ROOM = "DELETE FROM rooms WHERE roomId = ?";
	private final String SQL_DELETE_ROOMTYPE = "DELETE FROM roomTypes WHERE roomTypeId = ?";
	
	private final String SQL_UPDATE_ROOM = "UPDATE rooms set roomNumber = ?, roomType = ?, floor = ?, houseKeepingStatus = ? WHERE roomId = ?";
	private final String SQL_UPDATE_ROOMTYPE = "UPDATE roomTypes set roomType = ?, photos = ?, dailyPrice = ?, amenities = ?, capacity = ? WHERE roomTypeId = ?";
	
	private final String SQL_GET_ROOM_PRICE_BY_ROOM_ID = "SELECT dailyPrice FROM rooms JOIN roomTypes ON rooms.roomType = roomTypes.roomType WHERE roomNumber = ?";
	private final String SQL_GET_ROOM_PHOTOS_BY_ROOM_ID = "SELECT photos FROM rooms JOIN roomTypes ON rooms.roomType = roomTypes.roomType WHERE roomNumber = ?";
	

	@Autowired
	public RoomDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Room> getAllRooms() {
		return jdbcTemplate.query(SQL_GET_ALL_ROOMS, new RoomMapper());
	}
	
	public List<RoomType> getAllRoomTypes() {
		return jdbcTemplate.query(SQL_GET_ALL_ROOMTYPES, new RoomTypeMapper());
	}

	public List<Room> getRoomByNumber(int roomNumber) {
		return jdbcTemplate.query(SQL_GET_ROOM_BY_NUMBER, new RoomMapper(), roomNumber);
	}

	public List<RoomType> getRoomType(String roomType) {
		return jdbcTemplate.query(SQL_GET_ROOMTYPE_BY_TYPE, new RoomTypeMapper(), roomType);
	}
	

	public List<Room> checkRoomConflict(int roomId, int roomNumber) {
		return jdbcTemplate.query(SQL_CHECK_ROOM_CONFLICT, new RoomMapper(), roomId, roomNumber);
	}

	public List<RoomType> checkRoomTypeConflict(int roomTypeId, String roomType) {
		return jdbcTemplate.query(SQL_CHECK_ROOMTYPE_CONFLICT, new RoomTypeMapper(), roomTypeId, roomType);
	}

	public Room getRoomById(int roomId) {
		return jdbcTemplate.queryForObject(SQL_GET_ROOM_BY_ID, new Object[] { roomId },new RoomMapper());
	}

	public RoomType getRoomTypeById(int roomTypeId) {
		return jdbcTemplate.queryForObject(SQL_GET_ROOMTYPE_BY_ID, new Object[] { roomTypeId }, new RoomTypeMapper());
	}
		
	public List<Room> getRoomsByRoomType(String roomType) {
		return jdbcTemplate.query(SQL_GET_ROOMS_BY_TYPE, new RoomMapper(), roomType);
	}

	
	public boolean createRoom(Room newRoom) {
		return jdbcTemplate.update(SQL_CREATE_ROOMS, newRoom.getRoomNumber(), newRoom.getRoomType(), newRoom.getFloor(), newRoom.getHousekeepingStatus()) > 0;
	}
	
	public boolean createRoomType(RoomType newRoomType) {
		return jdbcTemplate.update(SQL_CREATE_ROOMTYPES, newRoomType.getRoomType(), newRoomType.getPhotos(), newRoomType.getDailyPrice(), newRoomType.getAmenities()) > 0;
	}
	
	public boolean deleteRoom(int idToDelete) {
		return jdbcTemplate.update(SQL_DELETE_ROOM, idToDelete) > 0;
	}
	
	public boolean deleteRoomType(int idToDelete) {
		return jdbcTemplate.update(SQL_DELETE_ROOMTYPE, idToDelete) > 0;
	}
	
	public boolean updateRoom(Room room) {
		/** @return boolean indicating changes have been made to database */
		return jdbcTemplate.update(SQL_UPDATE_ROOM, room.getRoomNumber(), room.getRoomType(), room.getFloor(), room.getHousekeepingStatus(), room.getRoomId()) > 0;
	}
	
	public boolean updateRoomType(RoomType roomType) {
		/** @return boolean indicating changes have been made to database */
		return jdbcTemplate.update(SQL_UPDATE_ROOMTYPE, roomType.getRoomType(), roomType.getPhotos(), roomType.getDailyPrice(), roomType.getAmenities(), roomType.getRoomTypeId()) > 0;
	}
	
	public List<Room> getRoomPriceByRoomNumber(int roomNumber) {
		return jdbcTemplate.query(SQL_GET_ROOM_PRICE_BY_ROOM_ID, new RoomMapper(), roomNumber);
	}
	
	public List<Room> getRoomPhotosByRoomNumber(int roomNumber) {
		return jdbcTemplate.query(SQL_GET_ROOM_PHOTOS_BY_ROOM_ID, new RoomMapper(), roomNumber);
	}
}
