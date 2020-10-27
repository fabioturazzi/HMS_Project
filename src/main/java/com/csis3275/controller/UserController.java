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
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csis3275.dao.CustomerDAOImpl;
import com.csis3275.dao.StaffDAOImpl;
import com.csis3275.model.Customer;
import com.csis3275.model.Staff;

import com.csis3275.model.User;

/*
 * @author: Hackermen
 * This Controller is responsible to deal requests and responses regarding Login tasks.
 */

@Controller
public class UserController {

	@Autowired
	CustomerDAOImpl customerDAOImp;

	@Autowired
	StaffDAOImpl staffDAOImp;

	@ModelAttribute("staff")
	public Staff setupAddFormStaff() {
		return new Staff();
	}

	@ModelAttribute("customer")
	public Customer setupAddFormCustomer() {
		return new Customer();
	}

	/*
	 * This method will serve GET handler to "/login" requests
	 */
	@GetMapping("/")
	public String login(HttpSession session, ModelMap model) {

		User user = new User();
		model.addAttribute("user", user);

		model.addAttribute("changePassMessage", session.getAttribute("changePassMessage"));
		session.removeAttribute("changePassMessage");

		session.removeAttribute("sessionHash");
		session.removeAttribute("userType");
		session.removeAttribute("manage");
		session.removeAttribute("username");

		session.invalidate();

		return "login";
	}

	/*
	 * This method will serve POST handler to "/login" requests
	 */
	@PostMapping("/")
	public String checkCredentials(HttpSession session, User user, ModelMap model) {

		List<Customer> authCustomer = customerDAOImp.getUsernamePassword(user.getUsernameForm());
		List<Staff> authStaff = staffDAOImp.getUsernamePassword(user.getUsernameForm());
		user.setPasswordForm(user.getPasswordForm());
		/*
		 * Checking and searching for a user in the database (Customer and Staff)
		 */

		if (authCustomer.size() > 0) {

			if (isAuthenticated(authCustomer.get(0).getUsername(), authCustomer.get(0).getPassword(),
					user.getUsernameForm(), user.getPasswordForm())) {

				model.addAttribute("message", "Hello " + authCustomer.get(0).getfName());
				session.setAttribute("sessionHash", session);

				// testing
				session.setAttribute("username", authCustomer.get(0).getUsername());

				// Check if user has management access
				hasManageAccess(session, authCustomer.get(0).getUserType());

				return "roomSearch";
			} else {
				model.addAttribute("message", "Username and/or Password do not match");
				session.removeAttribute("sessionHash");
				return "login";
			}
		}

		if (authStaff.size() > 0) {
			if (isAuthenticated(authStaff.get(0).getUsername(), authStaff.get(0).getPassword(), user.getUsernameForm(),
					user.getPasswordForm())) {

				model.addAttribute("message", "Hello " + authStaff.get(0).getfName());
				session.setAttribute("sessionHash", session);

				// testing
				session.setAttribute("username", authStaff.get(0).getUsername());

				// Check if user has management access
				hasManageAccess(session, authStaff.get(0).getUserType());

				// Get a list of customers from the database
				List<Customer> customers = customerDAOImp.getAllCustomers();

				// Add the list of customers to the model to be returned to the view
				model.addAttribute("customerList", customers);

				return "customerManagement";
			} else {
				model.addAttribute("message", "Username and/or Password does not match");
				session.removeAttribute("sessionHash");
				return "login";
			}
		}
		model.addAttribute("message", "Username and/or Password do not match");
		return "login";
	}

	@GetMapping("/resetPassword")
	public String resetPassword(HttpSession session, ModelMap model) {

		User user = new User();
		model.addAttribute("user", user);

		return "resetPassword";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(HttpSession session, User user, ModelMap model) {

		List<Customer> resetCustomer = customerDAOImp.getCustomer(user.getUsername());
		List<Staff> resetStaff = staffDAOImp.getStaff(user.getUsername());
		
		/*
		 * Checking and searching for a user in the database (Customer and Staff)
		 */

		if (!resetCustomer.isEmpty()) {

			session.setAttribute("user", resetCustomer.get(0));
			return "redirect:/resetPasswordConfirm";
		} else if (!resetStaff.isEmpty()) {

			session.setAttribute("user", resetStaff.get(0));
			return "redirect:/resetPasswordConfirm";
		} else {
			model.addAttribute("message", "Username doesn't exist");
			return "resetPassword";
		}

	}

	@GetMapping("/resetPasswordConfirm")
	public String resetPasswordConfirm(HttpSession session, ModelMap model) {

		User user = (User) session.getAttribute("user");

		model.addAttribute("message", session.getAttribute("message"));
		session.removeAttribute("message");

		user.setPassword("");
		user.setPassAnswer("");

		model.addAttribute("user", user);

		return "resetPasswordConfirm";
	}

	@PostMapping("/resetPasswordConfirm")
	public String resetPasswordConfirm(HttpSession session, @ModelAttribute("user") User user, ModelMap model) {

		List<Customer> resetCustomer = customerDAOImp.getPasswordQuestion(user.getUsername(), user.getPassQuestion(),
				user.getPassAnswer());
		List<Staff> resetStaff = staffDAOImp.getPasswordQuestion(user.getUsername(), user.getPassQuestion(),
				user.getPassAnswer());

		if (!resetCustomer.isEmpty()) {
			// change password
			customerDAOImp.updatePassword(user);

			session.removeAttribute("user");
			session.setAttribute("changePassMessage", "Password successfully changed for " + user.getUsername());

			return "redirect:/";

		} else if (!resetStaff.isEmpty()) {
			// change password
			staffDAOImp.updatePassword(user);

			session.removeAttribute("user");
			session.setAttribute("changePassMessage", "Password successfully changed for " + user.getUsername());

			return "redirect:/";

		} else {
			session.setAttribute("message", "Answer incorrect");
			return "redirect:/resetPasswordConfirm";
		}

	}

	@GetMapping("/resetPassFromProf")
	public String resetPasswordFromProf(HttpSession session, ModelMap model) {

			model.addAttribute("message", session.getAttribute("message"));

		session.removeAttribute("message");

		String username = session.getAttribute("username").toString();

		// Get a customer to delete and redirect to delete confirmation page
		List<Customer> userPassUpd = customerDAOImp.getCustomer(username);
		userPassUpd.get(0).setPassword("");

		model.addAttribute("user", userPassUpd.get(0));

		return "resetPasswordInProfile";
	}

	@PostMapping("/resetPassFromProf")
	public String resetPasswordFromProf(HttpSession session, @ModelAttribute("user") User user, ModelMap model) {

		List<Customer> resetCustomer = customerDAOImp.getCustomerWithPass(user.getUsername(), user.getPasswordForm());

		if (!resetCustomer.isEmpty()) {
			// change password
			customerDAOImp.updatePasswordNoQ(user);

			session.setAttribute("changePassMessageProfile", "Password successfully changed for " + user.getUsername());

			return "redirect:/profile";

		} else {
			session.setAttribute("message", "Old password does not match");
			return "redirect:/resetPassFromProf";
		}

	}

	/*
	 * This method check if the username and password provided matches the one
	 * stored inside the DB
	 */
	private boolean isAuthenticated(String userDb, String passDb, String username, String password) {
		boolean isAuth = false;

		if (userDb.equals(username) && passDb.equals(password)) {
			isAuth = true;
		} else {
			isAuth = false;
		}
		return isAuth;
	}

	/*
	 * This method check if the session is valid based on User Authentication.
	 */
	public boolean hasValidSession(HttpSession session) {
		boolean isValid = false;

		if (session.getAttribute("sessionHash") == session) {
			isValid = true;
		} else {
			isValid = false;
		}

		return isValid;
	}

	/*
	 * Check management Access
	 */
	public void hasManageAccess(HttpSession session, String userType) {
		if (userType.equals("Staff")) {
			session.setAttribute("manage", "yes");
			session.setAttribute("userType", userType);
		} else {
			session.setAttribute("manage", "no");
			session.setAttribute("userType", userType);
		}
	}
}
