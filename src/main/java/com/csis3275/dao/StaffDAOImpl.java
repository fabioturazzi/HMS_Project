package com.csis3275.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.csis3275.model.Customer;
import com.csis3275.model.CustomerMapper;
import com.csis3275.model.Staff;
import com.csis3275.model.StaffMapper;

@Component
public class StaffDAOImpl {

	JdbcTemplate jdbcTemplate;
	
	private final String SQL_GET_ALL = "SELECT * FROM staffs";
	private final String SQL_GET_USERNAME = "SELECT * FROM staffs WHERE username = ?";
	private final String SQL_GET_USERNAME_AND_ID = "SELECT * FROM staffs WHERE username = ? AND id != ?";
	private final String SQL_CREATE_STAFF = "INSERT INTO staffs (fName, lName, password, username, userType, position) VALUES (?,?,?,?,?,?)";
	private final String SQL_DELETE_STAFF = "DELETE FROM staffs WHERE id = ?";
	private final String SQL_UPDATE_STAFF = "UPDATE staffs set fName = ?, lName = ?, username = ?, position = ? WHERE id = ?";
	private final String SQL_FIND_STAFF = "SELECT * FROM staffs WHERE id = ?";
	
	@Autowired
	public StaffDAOImpl(DataSource dataSource)	{
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
		
	public List<Staff> getAllStaffs()	{
		return jdbcTemplate.query(SQL_GET_ALL, new StaffMapper());	
	}
	
	public List<Staff> getStaff(String username)	{
		return jdbcTemplate.query(SQL_GET_USERNAME, new StaffMapper(), username);	
	}
	public List<Staff> getStaff(String username, int id)	{
		return jdbcTemplate.query(SQL_GET_USERNAME_AND_ID, new StaffMapper(), username, id);	
	}
	public boolean createStaff(Staff newStaff) {
		return jdbcTemplate.update(SQL_CREATE_STAFF, newStaff.getfName(), newStaff.getlName(), newStaff.getPassword(), newStaff.getUsername(), newStaff.getUserType(), newStaff.getPosition()) > 0;
	}
	
	public boolean deleteStaff(int idToDelete) {
		return jdbcTemplate.update(SQL_DELETE_STAFF, idToDelete) > 0;
	}
	public boolean updateStaff(Staff staff) {
		/**@return boolean indicating changes have been made to database */
		return jdbcTemplate.update(SQL_UPDATE_STAFF, staff.getfName(), staff.getlName(), staff.getUsername(), staff.getPosition(), staff.getId()) > 0;
	}
	public Staff getStaffById(int id) {
		/**@return an entry by its id */
		return jdbcTemplate.queryForObject(SQL_FIND_STAFF, new Object[] { id }, new StaffMapper());
	}

}