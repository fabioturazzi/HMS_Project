package com.csis3275.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.csis3275.dao.RoomDAOImpl;
import com.csis3275.model.Room;
import com.csis3275.model.RoomType;

@Controller
public class RoomSearchBookingController {

	@Autowired
	RoomDAOImpl roomDAOImp;

	@ModelAttribute("roomType")
	public RoomType setupAddForm() {
		return new RoomType();
	}
	
	//Create object for the UserController
	UserController user = new UserController();

	
	@GetMapping("/roomSearch")
	public String searchRoomTypes(HttpSession session, Model model) {

		model.addAttribute("amenitiesList", getAmenities());
		
		// Get a list of roomTypes from the database
		List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();
		List<Integer> availableRooms = new ArrayList<>();
		
		for(int i = 0; i < roomTypes.size(); i++) {
			availableRooms.add(roomDAOImp.getRoomsByRoomType(roomTypes.get(i).getRoomType()).size());
		}
		
		model.addAttribute("availableRooms", availableRooms);
		
	
		// Add the list of roomTypes to the model to be returned to the view
		model.addAttribute("roomTypeList", roomTypes);
		
		return "roomSearch";
	}

	@ModelAttribute("amenitiesList")
	public List<String> getAmenities() {
		List<String> amenitiesList = new ArrayList<String>();
		amenitiesList.add("Air Conditioner");
		amenitiesList.add("Heating");
		amenitiesList.add("King Size Bed");
		amenitiesList.add("Single Bed");
		amenitiesList.add("Bath Tub");
		amenitiesList.add("Porch");
		amenitiesList.add("Room Service");
		amenitiesList.add("Wi-Fi");

		return amenitiesList;
	}
}
