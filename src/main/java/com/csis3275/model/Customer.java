package com.csis3275.model;


/**
 * @author Hackermen
 * Hotel Management System
 */

public class Customer extends User {

	private String phoneNumber;
	private String address;
	private String email;

	/**
	 * Customized Constructor
	 * @param username
	 * @param password
	 * @param fName
	 * @param lName
	 * @param userType
	 * @param phoneNumber
	 * @param address
	 * @param email
	 * @param passQuestion
	 * @param passAnswer
	 */
	public Customer(String username, String password, String fName, String lName, String userType, String phoneNumber, String address, String email, String passQuestion, String passAnswer) {
		super(username, password, fName, lName, "Customer", passQuestion, passAnswer);
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;

	}
	
	/**
	 * Default Constructor
	 */
	
	public Customer() {
		super("Customer");
	}

	/**
	 * Getters and Setters
	 */
	
	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}



}
