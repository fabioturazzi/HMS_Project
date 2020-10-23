package com.csis3275.model;

public class Staff extends User {

	private String position;

	public Staff(String username, String password, String fName, String lName, String userType, String position,
			String passQuestion, String passAnswer) {
		super(username, password, fName, lName, "Staff", passQuestion, passAnswer);
		this.position = position;
	}

	public Staff() {
		super("Staff");
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}