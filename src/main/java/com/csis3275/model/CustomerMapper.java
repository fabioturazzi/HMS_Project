package com.csis3275.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomerMapper implements RowMapper<Customer>{

	public Customer mapRow(ResultSet resultSet, int i) throws SQLException	{
		
		Customer customer = new Customer();
		customer.setId(resultSet.getInt("id"));
		customer.setfName(resultSet.getString("fName"));
		customer.setlName(resultSet.getString("lName"));
		customer.setEmail(resultSet.getString("email"));
		customer.setAddress(resultSet.getString("address"));
		customer.setPassword(resultSet.getString("password"));
		customer.setPhoneNumber(resultSet.getString("phoneNumber"));
		customer.setUsername(resultSet.getString("username"));
		customer.setUserType(resultSet.getString("userType"));
		
		return customer;
		
	}

}
