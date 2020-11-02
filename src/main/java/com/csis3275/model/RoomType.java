package com.csis3275.model;

public class RoomType {
	
	private int roomTypeId;
	private String roomType;
	private String[] photos;
	private double dailyPrice;
	private String[] amenities;
	private int capacity;
	
	public RoomType(String roomType, String[] photos, double dailyPrice, String[] amenities, int roomTypeId, int capacity) {
		super();
		this.roomType = roomType;
		this.photos = photos;
		this.dailyPrice = dailyPrice;
		this.amenities = amenities;
		this.roomTypeId = roomTypeId;
		this.capacity = capacity;
	}

	public RoomType() {
		
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String[] getPhotos() {
		return photos;
	}

	public void setPhotos(String[] photos) {
		this.photos = photos;
	}

	public double getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}

	public String[] getAmenities() {
		return amenities;
	}

	public void setAmenities(String[] amenities) {
		this.amenities = amenities;
	}

}
