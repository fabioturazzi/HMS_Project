package com.csis3275.model;

/**
 * @author Hackermen
 * Hotel Management System
 */

public class Staff extends User {

	private String position;

	/**
	 * Customized Constructor
	 * @param username
	 * @param password
	 * @param fName
	 * @param lName
	 * @param userType
	 * @param position
	 * @param passQuestion
	 * @param passAnswer
	 */
	public Staff(String username, String password, String fName, String lName, String userType, String position,
			String passQuestion, String passAnswer) {
		super(username, password, fName, lName, "Staff", passQuestion, passAnswer);
		this.position = position;
	}

	
	/**
	 * Default Constructor 
	 */
	public Staff() {
		super("Staff");
	}

	
	/**
	 * Getters and setters
	 */
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}