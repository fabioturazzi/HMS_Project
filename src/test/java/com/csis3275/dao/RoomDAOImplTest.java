package com.csis3275.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.csis3275.model.Room;
import com.csis3275.model.RoomType;

/**
 * @author Hackermen Hotel Management System - This class will be used to perform
 *         Integration Testing in the RoomType and RoomType entities. Since this integration is done by the RoomDAOImpl class,
 *         this class will be used to test interactions between them when using CRUD operations
 */

//@ContextConfiguration(classes = {SpringMVCConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@Transactional
public class RoomDAOImplTest {
		
	@Autowired
	RoomDAOImpl roomDAOImp;
	
	List<Room> allRooms = new ArrayList<>();
//	List<RoomType> allRoomTypes = new ArrayList<>();
	
	@Before
	public void setUp() throws Exception {
//		"SELECT * FROM rooms"
		allRooms = roomDAOImp.getAllRooms();
		
//		"SELECT * FROM roomTypes";
//		allRoomTypes = roomDAOImp.getAllRoomTypes();
	}

	@After
	public void tearDown() throws Exception {
	}

	/*This test will verify the checkRoomTypeBooking method,
	 * This method checks roomTypes in which capacity is larger than a given value,
	 * in order to find rooms that are adequate for a booking for a given number of people
	 * This method is overloaded with 2 implementation options, one providing a roomType and other checking only capacity of any roomType
	 */
	@Test
	public void checkRoomTypeBooking() {
//		"SELECT * FROM roomTypes WHERE capacity >= ? AND roomType = ?";
		//Testing the implementation WITH roomType
		List<RoomType> checkedRoomTypes = roomDAOImp.checkRoomTypeBooking(3, "Premium");
		assertTrue(checkedRoomTypes.size() > 0); //checks if rooms are found
//		
//		//Check all retrieved roomTypes to verify that their capacity is larger than 2
		for(RoomType checkedRoomType : checkedRoomTypes) {
			assertTrue(checkedRoomType.getCapacity() >= 2);
		}
		
		//Testing the implementation WITHOUT roomType
		List<RoomType> checkedRoomTypesNoType = roomDAOImp.checkRoomTypeBooking(3);
		assertTrue(checkedRoomTypesNoType.size() > 0); //checks if rooms are found
//		
//		//Check all retrieved roomTypes to verify that their capacity is larger than 2
		for(RoomType checkedRoomType : checkedRoomTypesNoType) {
			assertTrue(checkedRoomType.getCapacity() >= 2);
		}
	}
	
	/*This test will verify the getRoomByNumber method,
	 * This method gets a complete room by its unique roomNumber
	 */
	@Test
	public void getRoomByNumber() {
//		private final String SQL_GET_ROOM_BY_NUMBER = "SELECT * FROM rooms WHERE roomNumber = ?";
		List<RoomType> checkedRoomTypes = roomDAOImp.checkRoomTypeBooking(3, "Premium");
		assertTrue(checkedRoomTypes.size() > 0); //checks if rooms are found
//		
//		//Check all retrieved roomTypes to verify that their capacity is larger than 2
		for(RoomType checkedRoomType : checkedRoomTypes) {
			assertTrue(checkedRoomType.getCapacity() >= 2);
		}
		
		//Testing the implementation WITHOUT roomType
		List<RoomType> checkedRoomTypesNoType = roomDAOImp.checkRoomTypeBooking(3);
		assertTrue(checkedRoomTypesNoType.size() > 0); //checks if rooms are found
//		
//		//Check all retrieved roomTypes to verify that their capacity is larger than 2
		for(RoomType checkedRoomType : checkedRoomTypesNoType) {
			assertTrue(checkedRoomType.getCapacity() >= 2);
		}
	}
	
	
//	public List<RoomType> getRoomType(String roomType) 
//	public List<Room> checkRoomConflict(int roomId, int roomNumber) 
//	public List<RoomType> checkRoomTypeConflict(int roomTypeId, String roomType) 
//	public Room getRoomById(int roomId) 
//	public RoomType getRoomTypeById(int roomTypeId) 
//	public List<Room> getRoomsByRoomType(String roomType) 
//	public boolean createRoom(Room newRoom) 
//	public boolean createRoomType(RoomType newRoomType) 
//	public boolean deleteRoom(int idToDelete) 
//	public boolean deleteRoomType(int idToDelete) 
//	public boolean updateRoom(Room room)
//	public boolean updateRoomType(RoomType roomType) 
//	public boolean updateRoomTypePhotos(String roomType, String[] photos)

	
//	private final String SQL_GET_ROOM_BY_NUMBER = "SELECT * FROM rooms WHERE roomNumber = ?";
//	private final String SQL_GET_ROOMTYPE_BY_TYPE = "SELECT * FROM roomTypes WHERE roomType = ?";
//	
//	private final String SQL_GET_ROOM_BY_ID = "SELECT * FROM rooms WHERE roomId = ?";
//	private final String SQL_GET_ROOMTYPE_BY_ID = "SELECT * FROM roomTypes WHERE roomTypeId = ?";
//	
//	private final String SQL_CHECK_ROOM_CONFLICT = "SELECT * FROM rooms WHERE roomId != ? AND roomNumber = ?";
//	private final String SQL_CHECK_ROOMTYPE_CONFLICT = "SELECT * FROM roomTypes WHERE roomTypeId != ? AND roomType = ?";
//	
//	private final String SQL_GET_ROOMS_BY_TYPE = "SELECT * FROM rooms WHERE roomType = ?";
//
//	private final String SQL_CREATE_ROOMS = "INSERT INTO rooms (roomNumber, roomType, floor, houseKeepingStatus) VALUES (?,?,?,?)";
//	private final String SQL_CREATE_ROOMTYPES = "INSERT INTO roomTypes (roomType, photos, dailyPrice, amenities, capacity) VALUES (?,?,?,?,?)";
//	
//	private final String SQL_DELETE_ROOM = "DELETE FROM rooms WHERE roomId = ?";
//	private final String SQL_DELETE_ROOMTYPE = "DELETE FROM roomTypes WHERE roomTypeId = ?";
//	
//	private final String SQL_UPDATE_ROOM = "UPDATE rooms set roomNumber = ?, roomType = ?, floor = ?, houseKeepingStatus = ? WHERE roomId = ?";
//	private final String SQL_UPDATE_ROOMTYPE = "UPDATE roomTypes set roomType = ?, photos = ?, dailyPrice = ?, amenities = ?, capacity = ? WHERE roomTypeId = ?";
//	
//	private final String SQL_UPDATE_ROOMTYPE_PHOTOS = "UPDATE roomTypes set photos = ? WHERE roomType = ?";
//
//	private final String SQL_CHECK_ROOMTYPE_FOR_BOOKING_NOTYPE = "SELECT * FROM roomTypes WHERE capacity >= ?";
	
	
}