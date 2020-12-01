package com.csis3275.controller;

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
public class UserControllerTest {
	
	@Autowired
	private WebApplicationContext appContext;

	// Creating MockMVC object to check with system without running
	private MockMvc mockMvc;

	// Setup the test prior execution
	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(appContext).build();

	}

	/**
	 * Testing Staff Login
	 * @throws Exception
	 */
	@Test
	void testStaffLogin() throws Exception {
		this.mockMvc.perform(post("/")
				.param("usernameForm", "admin")
				.param("passwordForm", "admin"))
				.andExpect(status().isOk())
				.andExpect(view().name("customerManagement"));

	}
	
}
