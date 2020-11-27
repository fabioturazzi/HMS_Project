package com.csis3275.model;


/**
 * @author Hackermen
 * Hotel Management System
 */

public class FacilityBooking {

	private int facilityBookingId;
	private String facilityName;
	private int numberOfPeople;
	private String date;
	private String timeStart;
	private String timeEnd;
	private String customerUsername;
	private int correspBookingId;
	
	/**
	 * Customized Constructor
	 * @param facilityName
	 * @param numberOfPeople
	 * @param dateTimeStart
	 * @param dateTimeEnd
	 * @param customerUsername
	 * @param correspBookingId
	 */
	
	public FacilityBooking(int facilityBookingId, String facilityName, int numberOfPeople, String date,
			String timeStart, String timeEnd, String customerUsername, String facilityType, int correspBookingId) {
		super();
		this.facilityBookingId = facilityBookingId;
		this.facilityName = facilityName;
		this.numberOfPeople = numberOfPeople;
		this.date = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.customerUsername = customerUsername;
	}

	/**
	 * Default Constructor
	 */
	public FacilityBooking() {

	}
	
	/**
	 * Getters and Setters
	 */
	public int getFacilityBookingId() {
		return facilityBookingId;
	}

	public void setFacilityBookingId(int facilityBookingId) {
		this.facilityBookingId = facilityBookingId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}

	public int getCorrespBookingId() {
		return correspBookingId;
	}

	public void setCorrespBookingId(int correspBookingId) {
		this.correspBookingId = correspBookingId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	
}
