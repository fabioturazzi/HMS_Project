package com.csis3275;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class FacilitiesControllerTest {
	
	@Autowired
	private WebApplicationContext appContext;

	// Creating MockMVC object to check with system without running
	private MockMvc mockMvc;

	// Setup the test prior execution
	@BeforeEach
	void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(appContext).build();
		
	}

	/**
	 * Testing Facilities Staff creation
	 * @throws Exception
	 */
	@Test
	void facilitiesCreation() throws Exception {
		
		this.mockMvc.perform(post("/createFacility")
				.param("facilityName", "BAMBALALAO")
				.param("facilityType", "Meeting Room")
				.param("capacity", "4"))
				.andExpect(status().isOk())
				.andExpect(view().name("facilitiesManagement"));

	}

}
