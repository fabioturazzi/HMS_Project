package com.csis3275.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Hackermen Hotel Management System This class will be used to perform
 *         Unit Testing on the RoomType entity. Since the relationship between
 *         RoomType and their associated classes Room and Booking is done using
 *         the Database's constraints and operations, the Integration tests for
 *         this class will be performed separately using the respective Room and
 *         Booking DAO Implementation classes.
 */

public class RoomTypeTest {

	RoomType roomType;

	// Arrays to be used in the testing
	String[] photos = { "photo1link", "photo2link" };
	String[] amenities = { "Air Conditioner", "Bath Tub" };

	@Before
	public void setUp() throws Exception {
//		public RoomType(String roomType, String[] photos, double dailyPrice, String[] amenities, int roomTypeId, int capacity) {

		// Set up a room with all its properties
		roomType = new RoomType("Regular", photos, 200, amenities, 0, 3);

		/*
		 * Date properties created to fulfill roomSearch form requirement, considering
		 * springMVC's constraint of a single modelAttribute on each form
		 */
		roomType.setStartDateFormControl("01/01/2021");
		roomType.setEndDateFormControl("06/01/2021");
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

	// Testing getters from roomType

	// Test getter from roomType property, comparing to instantiated value on
	// constructor
	@Test
	public void getRoomType() {
		assertEquals("Regular", roomType.getRoomType());
	}

	// Test getter from dailyPrice property, comparing to instantiated value on
	// constructor
	@Test
	public void getDailyPrice() {
		assertEquals(200, roomType.getDailyPrice(), 0);
	}

	// Test getter from roomTypeId property, comparing to instantiated value on
	// constructor
	@Test
	public void getRoomTypeId() {
		assertEquals(0, roomType.getRoomTypeId());
	}

	// Test getter from capacity property, comparing to instantiated value on
	// constructor
	@Test
	public void getCapacity() {
		assertEquals(3, roomType.getCapacity());
	}

	// Test getter from startDateFormControl property, comparing to instantiated
	// value on constructor
	@Test
	public void getStartDateFormControl() {
		assertEquals("01/01/2021", roomType.getStartDateFormControl());
	}

	// Test getter from startDateFormControl property, comparing to instantiated
	// value on constructor
	@Test
	public void getEndDateFormControl() {
		assertEquals("06/01/2021", roomType.getEndDateFormControl());
	}

	// Test getter from photos array property, comparing to instantiated value on
	// constructor.
	// Since array is reference type, the test will use assertArrayEquals to compare
	// the values inside it
	@Test
	public void getPhotos() {
		String[] photosTest = { "photo1link", "photo2link" };
		assertArrayEquals(photosTest, roomType.getPhotos());
	}

	// Test getter from photos array property, comparing to instantiated value on
	// constructor.
	// Since array is reference type, the test will use assertArrayEquals to compare
	// the values inside it
	@Test
	public void getAmenities() {
		String[] amenitiesTest = { "Air Conditioner", "Bath Tub" };
		assertArrayEquals(amenitiesTest, roomType.getAmenities());
	}

	// Testing the setters from RoomType class

	// Test setter for roomType property
	@Test
	public void setRoomType() {
		String testValue = "Premium";
		roomType.setRoomType(testValue);
		assertEquals(testValue, roomType.getRoomType());
	}

	// Test setter for roomType property
	@Test
	public void setDailyPrice() {
		double testValue = 200;
		roomType.setDailyPrice(testValue);
		assertEquals(testValue, roomType.getDailyPrice(), 0);
	}

	// Test setter for roomTypeId property
	@Test
	public void setRoomTypeId() {
		int testValue = 2;
		roomType.setRoomTypeId(testValue);
		assertEquals(testValue, roomType.getRoomTypeId());
	}

	// Test setter for capacity property
	@Test
	public void setCapacity() {
		int testValue = 2;
		roomType.setCapacity(testValue);
		assertEquals(testValue, roomType.getCapacity());
	}
	
	// Test setter for startDateFormControl property
	@Test
	public void setStartDateFormControl() {
		String testValue = "07/01/2020";
		roomType.setStartDateFormControl(testValue);
		assertEquals(testValue, roomType.getStartDateFormControl());
	}
	
	// Test setter for endDateFormControl property
	@Test
	public void setEndDateFormControl() {
		String testValue = "07/01/2020";
		roomType.setEndDateFormControl(testValue);
		assertEquals(testValue, roomType.getEndDateFormControl());
	}
	
//	public RoomType(String roomType, String[] photos, double dailyPrice, String[] amenities, int roomTypeId, int capacity) {

	// Test setter for photos array property (using assertArrayEquals to compare values)
	@Test
	public void setPhotos() {
		String[] testValue = {"photoA", "photoB"};
		roomType.setPhotos(testValue);
		assertArrayEquals(testValue, roomType.getPhotos());
	}
	
	// Test setter for amenities array property (using assertArrayEquals to compare values)
	@Test
	public void setAmenities() {
		String[] testValue = {"Porch", "King Bed"};
		roomType.setAmenities(testValue);
		assertArrayEquals(testValue, roomType.getAmenities());
	}
}
