package com.csis3275.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Hackermen
 * Hotel Management System
 */

public class StaffMapper implements RowMapper<Staff>{

	/**
	 *Staff mapRow Method
	 *@return staff
	 */
	public Staff mapRow(ResultSet resultSet, int i) throws SQLException	{
		
		Staff staff = new Staff();
		staff.setId(resultSet.getInt("id"));
		staff.setfName(resultSet.getString("fName"));
		staff.setlName(resultSet.getString("lName"));
		staff.setPosition(resultSet.getString("position"));
		staff.setPassword(resultSet.getString("password"));
		staff.setUsername(resultSet.getString("username"));
		staff.setUserType(resultSet.getString("userType"));
		staff.setPassQuestion(resultSet.getString("passQuestion"));
		staff.setPassAnswer(resultSet.getString("passAnswer"));

		
		return staff;
		
	}

}