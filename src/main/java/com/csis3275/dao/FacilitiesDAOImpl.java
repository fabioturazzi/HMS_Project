package com.csis3275.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.csis3275.model.Facilities;
import com.csis3275.model.FacilitiesMapper;

/**
 * @author Hackermen
 * Hotel Management System
 */

@Component
public class FacilitiesDAOImpl {
	
	JdbcTemplate jdbcTemplate;
	
	/**
	 * Declaring class variables
	 * SQL queries
	 */
	
	private final String SQL_GET_ALL_FACILITIES = "SELECT * FROM facilities";
	
	private final String SQL_GET_FACILITY_BY_FACILITYID = "SELECT * FROM facilities where FACILITYID = ?";
	private final String SQL_GET_FACILITY_BY_FACILITYNAME = "SELECT * FROM facilities where FACILITYNAME = ?";
	private final String SQL_GET_FACILITIES_BY_FACILITYTYPE = "SELECT * FROM facilities where FACILITYTYPE = ?";

	private final String SQL_CREATE_FACILITY = "INSERT INTO facilities (facilityname, facilitytype, capacity) VALUES (?, ?, ?)";
	private final String SQL_DELETE_FACILITY = "DELETE FROM facilities WHERE facilityid = ?";
	
	private final String SQL_UPDATE_FACILITY = "UPDATE facilities set facilityname = ?, facilitytype = ?, capacity = ? where facilityid = ?";
	

	/**
	 * Constructor
	 */
	@Autowired
	public FacilitiesDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/*
	 * DAO Methods
	 */	
	public List<Facilities> getAllFacilities() {
		return jdbcTemplate.query(SQL_GET_ALL_FACILITIES, new FacilitiesMapper());
	}
	
	public Facilities getFacilityById(int facilityId) {
		return jdbcTemplate.queryForObject(SQL_GET_FACILITY_BY_FACILITYID, new FacilitiesMapper(), facilityId);
	}
	
	public List<Facilities> getFacilityByName(String facilityName) {
		return jdbcTemplate.query(SQL_GET_FACILITY_BY_FACILITYNAME, new FacilitiesMapper(), facilityName);
	}
	
	public List<Facilities> getAllFacilitiesByType(String facilitiesType) {
		return jdbcTemplate.query(SQL_GET_FACILITIES_BY_FACILITYTYPE, new FacilitiesMapper(), facilitiesType);
	}
	
	public boolean createFacility(Facilities newFacility) {
		return jdbcTemplate.update(SQL_CREATE_FACILITY, newFacility.getFacilityName(), newFacility.getFacilityType(), newFacility.getCapacity()) > 0;
	}
	
	public boolean deleteFacility(int idToDelete) {
		return jdbcTemplate.update(SQL_DELETE_FACILITY, idToDelete) > 0;
	}
	
	public boolean updateFacility(Facilities updatedFacility) {
		/** @return boolean indicating changes have been made to database */
		return jdbcTemplate.update(SQL_UPDATE_FACILITY, updatedFacility.getFacilityName(), updatedFacility.getFacilityType(), updatedFacility.getCapacity(), updatedFacility.getFacilityId()) > 0;
	}

}
