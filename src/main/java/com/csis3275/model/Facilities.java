package com.csis3275.model;

/**
 * @author Hackermen
 * Hotel Management System
 */
public class Facilities {
	
	/**
	 * Declaring class variables
	 */
	private int facilityId;
	private String facilityName;
	private String facilityType;
	private int capacity;
	
	/**
	 * Customized Constructor
	 */
	public Facilities(String facilityName, String facilityType, int capacity) {
		this.facilityName = facilityName;
		this.facilityType = facilityType;
		this.capacity= capacity;
	}
	
	/**
	 * Default Constructor
	 */
	public Facilities() {
		
	}

	/**
	 * Declaring Getters and Setters
	 */
	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
