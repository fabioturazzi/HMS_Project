package com.csis3275.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Hackermen
 * Hotel Management System
 */


public class Booking {

	private int bookingId;
	private int roomNumber;
	private String customerUsername;
	private int numbOfPeople;
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
	
	public String setTodaysDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = df.format(new Date());
		return formatted;
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

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}	

	public int getNumbOfPeople() {
		return numbOfPeople;
	}

	public void setNumbOfPeople(int numbOfPeople) {
		this.numbOfPeople = numbOfPeople;
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
		this.roomType = roomType;
	}

}
