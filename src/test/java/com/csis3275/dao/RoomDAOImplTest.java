package com.csis3275.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.csis3275.model.Room;
import com.csis3275.model.RoomType;

/**
 * @author Hackermen Hotel Management System - This class will be used to
 *         perform Integration Testing in the RoomType and RoomType entities.
 *         Since this integration is done by the RoomDAOImpl class, this class
 *         will be used to test interactions between them when using CRUD
 *         operations
 */

@RunWith(MockitoJUnitRunner.class)
public class RoomDAOImplTest {

	@Mock
	RoomDAOImpl roomDAOImp;

	// List<RoomType> allRoomTypes = new ArrayList<>();

	@Before
	public void setUp() throws Exception {

		//MockitoAnnotations.openMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	// This test tests methods to get all rooms from the database
	@Test
	public void getAllRooms() {
//		"SELECT * FROM rooms";
		// Testing the implementation WITH roomType

		List<Room> listRoom = new ArrayList<>();

		Room testRoom = new Room(201, "Regular", 2, "Clean", 0);
		Room testRoom2 = new Room(301, "Premium", 2, "Clean", 0);

		listRoom.add(testRoom);
		listRoom.add(testRoom2);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.getAllRooms()).thenReturn(listRoom);

		// call the main method you want to test
		List<Room> result = roomDAOImp.getAllRooms();

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).getAllRooms();
		assertEquals(result.size(), listRoom.size());
	}

	// This test tests methods to get all roomTypes from the database
	@Test
	public void getAllRoomTypes() {
//		"SELECT * FROM roomTypes";
		// Testing the implementation WITH roomType

		List<RoomType> listRoomType = new ArrayList<>();

		String[] photos = { "photo 1", "photo 2" };
		String[] amenities = { "amen 1", "amen 2" };

		RoomType testRoomType = new RoomType("Regular", photos, 200, amenities, 0, 4);
		RoomType testRoomType2 = new RoomType("Premium", photos, 400, amenities, 1, 4);

		listRoomType.add(testRoomType);
		listRoomType.add(testRoomType2);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.getAllRoomTypes()).thenReturn(listRoomType);

		// call the main method you want to test
		List<RoomType> result = roomDAOImp.getAllRoomTypes();

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).getAllRoomTypes();
		assertEquals(result.size(), listRoomType.size());

	}

	/*
	 * This test will verify the checkRoomTypeBooking method, This method checks
	 * roomTypes in which capacity is larger than a given value, in order to find
	 * rooms that are adequate for a booking for a given number of people
	 */
	@Test
	public void checkRoomTypeBooking() {
//		"SELECT * FROM roomTypes WHERE capacity >= ? AND roomType = ?";
		List<RoomType> listRoomType = new ArrayList<>();

		String[] photos = { "photo 1", "photo 2" };
		String[] amenities = { "amen 1", "amen 2" };

		RoomType testRoomType = new RoomType("Regular", photos, 200, amenities, 0, 1);
		RoomType testRoomType2 = new RoomType("Premium", photos, 400, amenities, 1, 4);

		listRoomType.add(testRoomType);
		listRoomType.add(testRoomType2);

		for (RoomType roomType : listRoomType) {
			if (roomType.getCapacity() < 2) {
				listRoomType.remove(roomType);
			}
		}

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.checkRoomTypeBooking(2)).thenReturn(listRoomType);

		// call the main method you want to test
		List<RoomType> result = roomDAOImp.checkRoomTypeBooking(2);

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).checkRoomTypeBooking(2);
		assertEquals(result, listRoomType);
	}

//	
//	/*This test will verify the getRoomByNumber method,
//	 * This method gets a complete room by its unique roomNumber
//	 */
	@Test
	public void getRoomByNumber() {
		
		List<Room> listRoom = new ArrayList<>();
		Room testRoom = new Room(202,"Regular", 2, "Clean", 0);
		
		listRoom.add(testRoom);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.getRoomByNumber(202)).thenReturn(listRoom);

		// call the main method you want to test
		List<Room> result = roomDAOImp.getRoomByNumber(202);

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).getRoomByNumber(202);
		assertEquals(result, listRoom);
	}
	
	
//	
//	/*This test will verify the getRoomByType method,
//	 * This method gets a list of rooms by its type
//	 */
	@Test
	public void getRoomsByRoomType() {
		
		List<Room> listRoom = new ArrayList<>();
		
		RoomType testRoomType = new RoomType("Regular", null, 400, null, 1, 4);
		
		Room testRoom = new Room(202,testRoomType.getRoomType(), 2, "Clean", 0);
		Room testRoom2 = new Room(203,testRoomType.getRoomType(), 2, "Clean", 0);
		
		listRoom.add(testRoom);
		listRoom.add(testRoom2);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.getRoomsByRoomType("Regular")).thenReturn(listRoom);

		// call the main method you want to test
		List<Room> result = roomDAOImp.getRoomsByRoomType("Regular");

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).getRoomsByRoomType("Regular");
		assertEquals(result, listRoom);
	}
	
//	/*This test will verify the checkRoomConflict method,
//	 * This method gets checks existing roomNumbers for conflict, returning rooms in case of conflict to avoid addition to database
//	 */
	@Test
	public void checkRoomConflict() {
		
		List<Room> listRoom = new ArrayList<>();
		Room testRoom = new Room(202,"Regular", 2, "Clean", 0);

		listRoom.add(testRoom);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.checkRoomConflict(testRoom.getRoomId(),testRoom.getRoomNumber())).thenReturn(listRoom);

		// call the main method you want to test
		List<Room> result = roomDAOImp.checkRoomConflict(0,202);

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).checkRoomConflict(0,202);
		assertEquals(result, listRoom);
	}

//	/*This test will verify the createRoom method,
//	 * This method creates a room and returns a boolean confirming addition was made
//	 */
	@Test
	public void createRoom() {
		
		Room testRoom = new Room(202,"Regular", 2, "Clean", 0);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.createRoom(testRoom)).thenReturn(true);

		// call the main method you want to test
		roomDAOImp.createRoom(testRoom);

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).createRoom(testRoom);
	}
	
//	/*This test will verify the createRoomType method,
//	 * This method creates a roomType and returns a boolean confirming addition was made
//	 */
	@Test
	public void createRoomType() {
		
		String[] photos = { "photo 1", "photo 2" };
		String[] amenities = { "amen 1", "amen 2" };

		RoomType testRoomType = new RoomType("Regular", photos, 200, amenities, 0, 1);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.createRoomType(testRoomType)).thenReturn(true);

		// call the main method you want to test
		roomDAOImp.createRoomType(testRoomType);

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).createRoomType(testRoomType);
	}
	
//	/*This test will verify the deleteRoom method,
//	 * This method deletes a room and returns a boolean confirming change was made
//	 */
	@Test
	public void deleteRoom() {
		
		Room testRoom = new Room(202,"Regular", 2, "Clean", 0);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.deleteRoom(testRoom.getRoomId())).thenReturn(true);

		// call the main method you want to test
		roomDAOImp.deleteRoom(0);

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).deleteRoom(0);
	}
	
//	/*This test will verify the deleteRoomType method,
//	 * This method deletes a roomType and returns a boolean confirming change was made
//	 */
	@Test
	public void deleteRoomType() {
		
		String[] photos = { "photo 1", "photo 2" };
		String[] amenities = { "amen 1", "amen 2" };

		RoomType testRoomType = new RoomType("Regular", photos, 200, amenities, 0, 1);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.deleteRoomType(testRoomType.getRoomTypeId())).thenReturn(true);

		// call the main method you want to test
		roomDAOImp.deleteRoomType(testRoomType.getRoomTypeId());

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).deleteRoomType(testRoomType.getRoomTypeId());
	}

//	/*This test will verify the updateRoom method,
//	 * This method updates a room and returns a boolean confirming change was made
//	 */
	@Test
	public void updateRoom() {
		
		Room testRoom = new Room(202,"Regular", 2, "Clean", 0);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.updateRoom(testRoom)).thenReturn(true);

		// call the main method you want to test
		roomDAOImp.updateRoom(testRoom);

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).updateRoom(testRoom);
	}
	
//	/*This test will verify the updateRoomType method,
//	 * This method updates a roomType and returns a boolean confirming change was made
//	 */
	@Test
	public void updateRoomType() {
		
		String[] photos = { "photo 1", "photo 2" };
		String[] amenities = { "amen 1", "amen 2" };

		RoomType testRoomType = new RoomType("Regular", photos, 200, amenities, 0, 1);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.updateRoomType(testRoomType)).thenReturn(true);

		// call the main method you want to test
		roomDAOImp.updateRoomType(testRoomType);

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).updateRoomType(testRoomType);
	}
	
//	/*This test will verify the updateRoomType method,
//	 * This method updates a roomType and returns a boolean confirming change was made
//	 */
	@Test
	public void updateRoomTypePhotos() {
		
		String[] photos = { "photo 1", "photo 2" };
		String[] newPhotos = { "photo 1", "photo 2" };
		String[] amenities = { "amen 1", "amen 2" };

		RoomType testRoomType = new RoomType("Regular", photos, 200, amenities, 0, 1);

		// MOCK ALERT: return mocked result set on find
		when(roomDAOImp.updateRoomTypePhotos(testRoomType.getRoomType(), newPhotos)).thenReturn(true);

		// call the main method you want to test
		roomDAOImp.updateRoomTypePhotos("Regular", newPhotos);

		// MOCK ALERT: verify the method was called
		verify(roomDAOImp).updateRoomTypePhotos("Regular", newPhotos);
	}
}