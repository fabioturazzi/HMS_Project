package com.csis3275.model;


import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Base64;

import java.util.Base64;

/**
 * @author Hackermen
 * Hotel Management System
 */

public class User {
	private int id;
	private String username;
	private String password;
	private String fName;
	private String lName;
	private String userType;
	private String usernameForm;
	private String passwordForm;
	private String registrationDate;
	private String profileUpdated;
	private String passQuestion;
	private String passAnswer;
	
	/**
	 * Customized Constructor
	 * @param username
	 * @param password
	 * @param fName
	 * @param lName
	 * @param userType
	 * @param passQuestion
	 * @param passAnswer
	 */
	public User(String username, String password, String fName, String lName, String userType, String passQuestion, String passAnswer) {

		this.username = username;
		this.password = password;
		this.password = Base64.getEncoder().encodeToString(password.getBytes());
		this.fName = fName;
		this.lName = lName;
		this.userType = userType;
		this.passQuestion = passQuestion;
		this.passAnswer = passAnswer;
	}
	
	public String getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(String reg) {
		this.registrationDate = reg;
	}

	// Method to set the registration date (current day)
	public void putRegistrationDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = df.format(new Date());
		this.registrationDate = formatted;
	}

	public String getProfileUpdated() {
		return profileUpdated;
	}
	
	public void setProfileUpdated(String upd) {
		this.profileUpdated = upd;
	}

	// Method to set the date when profile was updated (current day)
	public void putProfileUpdated() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = df.format(new Date());
		this.profileUpdated = formatted;
	}


	public String getUsernameForm() {
		return usernameForm;
	}

	public void setUsernameForm(String usernameForm) {
		this.usernameForm = usernameForm;
	}

	public String getPasswordForm() {
//		if (this.passwordForm != null)
//			this.passwordForm = Base64.getEncoder().encodeToString(this.passwordForm.getBytes());
		
		return this.passwordForm;
	}

	public void setPasswordForm(String passwordForm) {
		this.passwordForm = Base64.getEncoder().encodeToString(passwordForm.getBytes());
	}

	public User() {
		
	}
	
	public User(String userType) {
		this.userType = userType;
	}
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Base64.getEncoder().encodeToString(password.getBytes());
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPassQuestion() {
		return passQuestion;
	}

	public void setPassQuestion(String passQuestion) {
		this.passQuestion = passQuestion;
	}

	public String getPassAnswer() {
		return passAnswer;
	}

	public void setPassAnswer(String passAnswer) {
		this.passAnswer = passAnswer;
	}

}
