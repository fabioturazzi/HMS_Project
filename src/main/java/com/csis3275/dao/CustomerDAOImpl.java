package com.csis3275.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.csis3275.model.CustomerMapper;
import com.csis3275.model.Customer;

@Component
public class CustomerDAOImpl {

	JdbcTemplate jdbcTemplate;
	
	private final String SQL_GET_ALL = "SELECT * FROM customers";
	private final String SQL_GET_USERNAME = "SELECT * FROM customers WHERE username = ?";
	private final String SQL_CREATE_CUSTOMER = "INSERT INTO customers (fName, lName, email, address, password, phoneNumber, username, userType) VALUES (?,?,?,?,?,?,?,?)";
	private final String SQL_DELETE_CUSTOMER = "DELETE FROM customers WHERE id = ?";

	
	@Autowired
	public CustomerDAOImpl(DataSource dataSource)	{
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
		
	public List<Customer> getAllCustomers()	{
		return jdbcTemplate.query(SQL_GET_ALL, new CustomerMapper());	
	}
	
	public List<Customer> getCustomer(String username)	{
		return jdbcTemplate.query(SQL_GET_USERNAME, new CustomerMapper(), username);	
	}
	
	public boolean createCustomer(Customer newCustomer) {
		return jdbcTemplate.update(SQL_CREATE_CUSTOMER, newCustomer.getfName(), newCustomer.getlName(), newCustomer.getEmail(), newCustomer.getAddress(), newCustomer.getPassword(), newCustomer.getPhoneNumber(), newCustomer.getUsername(), newCustomer.getUserType()) > 0;
	}
	
	public boolean deleteCustomer(int idToDelete) {
		return jdbcTemplate.update(SQL_DELETE_CUSTOMER, idToDelete) > 0;
	}
}
