package com.csis3275;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class RoomSearchBookingControllerTest {

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
	void submitBooking() throws Exception {
		
		MockHttpSession session = new MockHttpSession();
		
		session.setAttribute("username", "doeJohn");
		session.setAttribute("sessionHash", session);
		session.setAttribute("userType", "Customer");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/submitBooking")
				.param("roomNumber", "101")
				.param("customerUsername", "doeJohn")
				.param("numbOfPeople", "1")
				.param("status", "")
				.param("bookingDateStart", "2020-01-01")
				.param("bookindDateEnd", "2020-01-02")
				.param("totalCost", "200")
				.param("dateOfCreation", "2020-11-18")
				.param("roomType", "Premium")
                .session(session);
		
		this.mockMvc.perform(builder)
			.andExpect(forwardedUrl(null));

	}
	
	
}
