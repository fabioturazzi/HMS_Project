package com.csis3275.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RoomController {

	@GetMapping("/roomSearch")
	public String searchRooms() {

		return "roomSearch";
	}
}
