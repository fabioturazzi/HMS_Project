package com.csis3275.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Hackermen
 * Hotel Management System
 * @description Unit testing for Room Model Class
 */

public class RoomTest {
	
	Room newRoom;
	
	/**
	 * Set up
	 * @params roomNumber, roomType, floor, housekeepingStatus, roomId
	 */

	@Before
	public void setUp() throws Exception {
		newRoom = new Room(501, "Premium", 2, "clean", 1);
	}

	//Testing getters
	@Test
	public void getRoomNumber() {
		assertEquals(501, newRoom.getRoomNumber());
	}
	
	@Test
	public void getRoomType() {
		assertEquals("Premium", newRoom.getRoomType());
	}
	
	@Test
	public void getFloor() {
		assertEquals(2,newRoom.getFloor());
	}
	
	@Test
	public void getHouseKeepingStatus() {
		assertEquals("clean", newRoom.getHousekeepingStatus());
	}
	
	@Test
	public void getRoomID() {
		assertEquals(1, newRoom.getRoomId());
	}
	
	//Testing setters
	@Test
	public void setRoomNumber() {
		newRoom.setRoomNumber(502);
		assertEquals(502, newRoom.getRoomNumber());
	}
	
	@Test
	public void setRoomType() {
		newRoom.setRoomType("Regular");
		assertEquals("Regular", newRoom.getRoomType());
	}
	
	@Test
	public void setFloor() {
		newRoom.setFloor(5);
		assertEquals(5,newRoom.getFloor());
	}
	
	@Test
	public void setHouseKeepingStatus() {
		newRoom.setHousekeepingStatus("not clean");
		assertEquals("not clean", newRoom.getHousekeepingStatus());
	}
	
	@Test
	public void setRoomID() {
		newRoom.setRoomId(33);
		assertEquals(33, newRoom.getRoomId());
	}
}
