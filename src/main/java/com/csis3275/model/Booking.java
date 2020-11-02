package com.csis3275.model;

public class Booking {

	private int bookingId;
	private int roomNumber;
	private int customerId;
	private String status; // booked, checked-in, checked-out
	private boolean paid;
	private String bookingDateStart;
	private String bookindDateEnd;
	private String checkinDate;
	private String checkoutDate;
	private String paymentDate;
	private String dateOfCreation;
	private double totalCost;
	private String roomType;
	
	public Booking() {
		
	}
	
	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public String getBookingDateStart() {
		return bookingDateStart;
	}

	public void setBookingDateStart(String bookingDateStart) {
		this.bookingDateStart = bookingDateStart;
	}

	public String getBookindDateEnd() {
		return bookindDateEnd;
	}

	public void setBookindDateEnd(String bookindDateEnd) {
		this.bookindDateEnd = bookindDateEnd;
	}

	public String getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(String dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		roomType = roomType;
	}

}
