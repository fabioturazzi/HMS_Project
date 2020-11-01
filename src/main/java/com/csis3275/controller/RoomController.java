package com.csis3275.controller;

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
public class RoomController {

	@Autowired
	RoomDAOImpl roomDAOImp;

	@ModelAttribute("room")
	public Room setupAddForm() {
		return new Room();
	}
	
	//Create object for the UserController
	UserController user = new UserController();

	// Get the room and display the form
	@GetMapping("/deleteRoom")
	public String deleteRoom(@RequestParam(required = true) int id, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		// Get the room
		roomDAOImp.deleteRoom(id);

		// Get a list of rooms from the controller
		List<Room> rooms = roomDAOImp.getAllRooms();
		model.addAttribute("roomList", rooms);

		model.addAttribute("message", "Deleted Room: " + id);

		return "roomManagement";
	}
//	Arrays.asList(roomType.amenities).contains(amenity);
	/**
	 * Post request to add entry to database
	 */
	// Handle Form Post
	@PostMapping("/createRoom")
	public String createRoom(@ModelAttribute("room") Room createRoom, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		List<RoomType> checkValidType = roomDAOImp.getRoomType(createRoom.getRoomType());
		List<Room> checkRoom = roomDAOImp.getRoomByNumber(createRoom.getRoomNumber());
		
		if(!(checkRoom.isEmpty()))
		{
			model.addAttribute("errorMessage", "Room number already in use");
		} else if(checkValidType.isEmpty()) {
			model.addAttribute("errorMessage", "Invalid Room Type. Please add any desired Room Types before assigning it to a room.");
			
		} else {
			// Create the room pass the object in.
			roomDAOImp.createRoom(createRoom);
			model.addAttribute("room", createRoom);
			model.addAttribute("message", "Room created: " + createRoom.getRoomNumber());
		}
		
		// Get a list of rooms from the controller
		List<Room> rooms = roomDAOImp.getAllRooms();
		model.addAttribute("roomList", rooms);
		
		return "roomManagement";
	}
	
	
	@GetMapping("/roomManagement/room")
	public String showRooms(HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		// Get a list of rooms from the database
		List<Room> rooms = roomDAOImp.getAllRooms();
	
		// Add the list of rooms to the model to be returned to the view
		model.addAttribute("roomList", rooms);
		
		return "roomManagement";
	}

	/**
	 * Get request to get entry to be edited from database
	 */
	@GetMapping("/editRoom")
	public String editRoom(@RequestParam(required = true) int id, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		
		// Get the room
		Room updatedRoom = roomDAOImp.getRoomById(id);
		model.addAttribute("room", updatedRoom);
		
		return "roomManagementEdit";
	}

	/**
	 * Post request to edit entry from database
	 */
	@PostMapping("/editRoom")
	public String updateRoom(@ModelAttribute("room") Room updatedRoom, HttpSession session, Model model) {
		
		//checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		List<RoomType> checkValidType = roomDAOImp.getRoomType(updatedRoom.getRoomType());
		List<Room> checkRoom = roomDAOImp.checkRoomConflict(updatedRoom.getRoomId(), updatedRoom.getRoomNumber());
		
		if(!checkRoom.isEmpty())
		{
			model.addAttribute("errorMessage", "Room number already in use");
			
			return "roomManagementEdit";
		} else if(checkValidType.isEmpty()) {
			model.addAttribute("errorMessage", "Invalid Room Type. Please add any desired Room Types before assigning it to a room.");
			
			return "roomManagementEdit";
		} else {
			roomDAOImp.updateRoom(updatedRoom);
			
			List<Room> rooms = roomDAOImp.getAllRooms();
			model.addAttribute("roomList", rooms);
			model.addAttribute("message", "Updated Room " + updatedRoom.getRoomNumber());
			
			return "roomManagement";
		}
	}
}
//
//@Controller
//public class RoomController {
//
//	@GetMapping("/roomSearch")
//	public String searchRooms() {
//
//		return "roomSearch";
//	}
//}
