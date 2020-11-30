package com.csis3275.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.csis3275.dao.RoomDAOImpl;
import com.csis3275.model.Room;
import com.csis3275.model.RoomType;

/**
 * @author Hackermen
 * Hotel Management System
 */

@Controller
public class RoomTypeController {

	@Autowired
	RoomDAOImpl roomDAOImp;

	@ModelAttribute("roomType")
	public RoomType setupAddForm() {
		return new RoomType();
	}

	// Create object for the UserController
	UserController user = new UserController();

	// Get the roomType and display the form
	@GetMapping("/deleteRoomType")
	public String deleteRoomType(@RequestParam(required = true) int id, HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		RoomType roomTypeObj = roomDAOImp.getRoomTypeById(id);

		List<Room> assignedRooms = roomDAOImp.getRoomsByRoomType(roomTypeObj.getRoomType());

		if (!(assignedRooms.isEmpty())) {
			session.setAttribute("errorMessage", "There are rooms assigned to the Room Type " + roomTypeObj.getRoomType()
					+ ". Please reassign them before deleting.");
		} else {
			// Delete the roomType
			roomDAOImp.deleteRoomType(id);

			session.setAttribute("warning", "Deleted RoomType: " + id);
		}

		return "redirect:/roomTypeManagement";
	}

	/** Post request to add entry to database
	 */
	// Handle Form Post
	@PostMapping("/createRoomType")
	public String createRoomType(@ModelAttribute("roomType") RoomType createRoomType, HttpSession session,
			Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		List<RoomType> checkRoomType = roomDAOImp.getRoomType(createRoomType.getRoomType());

		if (!(checkRoomType.isEmpty())) {
			session.setAttribute("errorMessage", "Room Type already exists");
		} else {
			// Create the roomType pass the object in.
			roomDAOImp.createRoomType(createRoomType);
			session.setAttribute("message", "RoomType created: " + createRoomType.getRoomType());
		}

		return "redirect:/roomTypeManagement";
	}

	@GetMapping("/roomTypeManagement")
	public String showRoomTypes(HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		model.addAttribute("message", session.getAttribute("message"));
		session.removeAttribute("message");
		model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
		session.removeAttribute("errorMessage");
		model.addAttribute("warning", session.getAttribute("warning"));
		session.removeAttribute("warning");
		
		model.addAttribute("amenitiesList", getAmenities());

		// Get a list of roomTypes from the database
		List<RoomType> roomTypes = roomDAOImp.getAllRoomTypes();

		// Add the list of roomTypes to the model to be returned to the view
		model.addAttribute("roomTypeList", roomTypes);

		return "roomTypeManagement";
	}

	
	
	@GetMapping("/editRoomType")
	public String editRoomType(@RequestParam(required = true) int id, HttpSession session, Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";

		RoomType updatedRoomType = roomDAOImp.getRoomTypeById(id);

		model.addAttribute("amenitiesList", getAmenities());

		// Get the roomType
		model.addAttribute("roomType", updatedRoomType);

		return "roomTypeManagementEdit";
	}

	/**
	 * Post request to edit entry from database
	 */
	@PostMapping("/editRoomType")
	public String updateRoomType(@ModelAttribute("roomType") RoomType updatedRoomType, HttpSession session,
			Model model) {

		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		model.addAttribute("amenitiesList", getAmenities());

		List<RoomType> checkRoomType = roomDAOImp.checkRoomTypeConflict(updatedRoomType.getRoomTypeId(),
				updatedRoomType.getRoomType());

		if (!checkRoomType.isEmpty()) {
			model.addAttribute("errorMessage", "Room Type already exists");

			return "roomTypeManagementEdit";
		} else {
			roomDAOImp.updateRoomType(updatedRoomType);

			session.setAttribute("message", "Updated information for RoomType " + updatedRoomType.getRoomType());

			return "redirect:/roomTypeManagement";
		}
	}

	@GetMapping("/uploadPhotos")
	public String getFormUpload(@RequestParam(required = true) String selectedRoomType, HttpSession session,
			Model model) {
		// checking if user has a valid session hash and access
		if (!user.hasValidSession(session) || session.getAttribute("manage").equals("no"))
			return "denied";
		List<RoomType> roomType = roomDAOImp.getRoomType(selectedRoomType);
		model.addAttribute("roomType", roomType.get(0));

		return "roomTypePhoto";
	}

	@PostMapping("/uploadPhotos")
	public String handleFormUpload(@RequestParam("uploadPhotos") MultipartFile uploadPhotos, @RequestParam("roomType") String roomTypeUpload, HttpSession session,
			Model model) throws IOException {
		if (!uploadPhotos.isEmpty()) {
			
			Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat("ddmmyy_HHmmss");
		    
		    String path = this.getClass().getClassLoader().getResource("").getPath();
		    String fullPath = URLDecoder.decode(path, "UTF-8");
		    String pathArr[] = fullPath.split("/WEB-INF/classes/");
		    
		    fullPath = pathArr[0] + "/static/images/";

		    String saveUrl = fullPath.replace("/C:", "C:") + roomTypeUpload + sdf.format(cal.getTime()).toString() + ".png";
		    
			File destination = new File(saveUrl); 
			
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(uploadPhotos.getBytes()));
			ImageIO.write(src, "png", destination);

			
			RoomType roomType = roomDAOImp.getRoomType(roomTypeUpload).get(0);
			
			String[] photos = roomType.getPhotos();
			if(!photos[0].equals("")) {
				String[] newPhotos = new String[photos.length + 1];
				
				for(int i = 0; i < photos.length; i++) {
					newPhotos[i] = photos[i];
				}
				newPhotos[photos.length] = saveUrl.split("static/")[1];
				roomDAOImp.updateRoomTypePhotos(roomType.getRoomType(), newPhotos);
			} else {
				String[] newPhotos = { saveUrl.split("static/")[1] };
				roomDAOImp.updateRoomTypePhotos(roomType.getRoomType(), newPhotos);
			}
		}
		
		return "redirect:/roomTypeManagement";
	}

	/**
	 * Get list of amenities for form
	 */
	@ModelAttribute("amenitiesList")
	public List<String> getAmenities() {
		List<String> amenitiesList = new ArrayList<String>();
		amenitiesList.add("Air Conditioner");
		amenitiesList.add("Heating");
		amenitiesList.add("King Size Bed");
		amenitiesList.add("Bath Tub");
		amenitiesList.add("Porch");
		amenitiesList.add("Room Service");
		amenitiesList.add("Wi-Fi");

		return amenitiesList;
	}
}
