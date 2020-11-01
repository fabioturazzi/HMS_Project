package com.csis3275.controller;

import java.util.ArrayList;
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
public class RoomTypeController {

	@Autowired
	RoomDAOImpl roomDAOImp;

	@ModelAttribute("roomType")
	public RoomType setupAddForm() {
		return new RoomType();
	}
	
	//Create object for the UserController
	UserController user = new UserController();

	// Get the roomType and display the form
	@GetMapping("/deleteRoomType")
	public String deleteRoomType(@RequestParam(required = true) int id, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		model.addAttribute("amenitiesList", getAmenities());
		
		RoomType roomTypeObj = roomDAOImp.getRoomTypeById(id);
		
		List<Room> assignedRooms =roomDAOImp.getRoomsByRoomType(roomTypeObj.getRoomType());
		
		if(!(assignedRooms.isEmpty()))
		{
			model.addAttribute("message", "There are rooms assigned to the Room Type " + roomTypeObj.getRoomType() + ". Please reassign them before deleting.");
		}
		else {
			// Delete the roomType
			roomDAOImp.deleteRoomType(id);
			
			model.addAttribute("message", "Deleted RoomType: " + id);
		}
		
		// Get a list of roomTypes from the controller
		List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();
		model.addAttribute("roomTypeList", roomTypes);

		return "roomTypeManagement";
	}
	
	/**
	 * Post request to add entry to database
	 */
	// Handle Form Post
	@PostMapping("/createRoomType")
	public String createRoomType(@ModelAttribute("roomType") RoomType createRoomType, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		model.addAttribute("amenitiesList", getAmenities());
		
		List<RoomType> checkRoomType = roomDAOImp.getRoomType(createRoomType.getRoomType());
		
		if(!(checkRoomType.isEmpty()))
		{
			model.addAttribute("errorMessage", "Room Type already exists");
		}
		else {
			// Create the roomType pass the object in.
			roomDAOImp.createRoomType(createRoomType);
			model.addAttribute("roomType", createRoomType);
			model.addAttribute("message", "RoomType created: " + createRoomType.getRoomType());
		}
		
		// Get a list of roomTypes from the controller
		List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();
		model.addAttribute("roomTypeList", roomTypes);
		
		return "roomTypeManagement";
	}
	
	
	@GetMapping("/roomManagement/roomType")
	public String showRoomTypes(HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		model.addAttribute("amenitiesList", getAmenities());
		
		// Get a list of roomTypes from the database
		List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();
	
		// Add the list of roomTypes to the model to be returned to the view
		model.addAttribute("roomTypeList", roomTypes);
		
		return "roomTypeManagement";
	}

	/**
	 * Get request to get entry to be edited from database
	 */
	@GetMapping("/editRoomType")
	public String editRoomType(@RequestParam(required = true) int id, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		model.addAttribute("amenitiesList", getAmenities());
		
		// Get the roomType
		RoomType updatedRoomType = roomDAOImp.getRoomTypeById(id);
		model.addAttribute("roomType", updatedRoomType);
		
		return "roomTypeManagementEdit";
	}

	/**
	 * Post request to edit entry from database
	 */
	@PostMapping("/editRoomType")
	public String updateRoomType(@ModelAttribute("roomType") RoomType updatedRoomType, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		model.addAttribute("amenitiesList", getAmenities());

		List<RoomType> checkRoomType = roomDAOImp.checkRoomTypeConflict(updatedRoomType.getRoomTypeId(), updatedRoomType.getRoomType());
		
		if(!checkRoomType.isEmpty())
		{
			model.addAttribute("errorMessage", "Room Type already exists");
			
			return "roomTypeManagementEdit";
		}
		else {
			roomDAOImp.updateRoomType(updatedRoomType);
			
			List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();
			model.addAttribute("roomTypeList", roomTypes);
			model.addAttribute("message", "Updated RoomType " + updatedRoomType.getRoomType());
			
			return "roomTypeManagement";
		}
	}
	@GetMapping("/roomSearch")
	public String searchRoomTypes() {

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
