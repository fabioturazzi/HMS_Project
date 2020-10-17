package com.csis3275.model;

public class Customer extends User {

	private String phoneNumber;
	private String address;
	private String email;
	
	public Customer(String username, String password, String fName, String lName, String userType, String phoneNumber, String address, String email) {
		super(username, password, fName, lName, "Customer");
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;
	}
	
	public Customer() {
		super("Customer");
	}


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
