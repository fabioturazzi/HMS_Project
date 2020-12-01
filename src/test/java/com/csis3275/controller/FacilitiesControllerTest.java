package com.csis3275.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
		
		MockHttpSession session = new MockHttpSession();
		
		session.setAttribute("username", "doeJohn");
		session.setAttribute("sessionHash", session);
		session.setAttribute("userType", "Customer");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/createFacility")
				.param("facilityName", "BAMBALALAO")
				.param("facilityType", "Meeting Room")
				.param("capacity", "4")
	            .session(session);
		
        this.mockMvc.perform(builder)
        	.andExpect(MockMvcResultMatchers.status()
        			.isOk());

	}

}
