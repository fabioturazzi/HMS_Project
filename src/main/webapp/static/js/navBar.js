


$(function() {

	var title = $("#pageTitle").text();

	if (title === "Welcome to Hackermen Hotel Management System" || title === "Password Reset") {
		$(".nav1").addClass("active");
	}
	else if (title === "Customer Registration" || title === "Successful Registration") {
		$(".nav2").addClass("active");
	}
	else if (title === "Delete Account" || title === "View Profile") {
		$(".nav3").addClass("active");
	}
	else if (title === "Room Search") {
		$(".nav4").addClass("active");
	}
	else if (title === "My Bookings") {
		$(".nav5").addClass("active");
	}
	else if (title === "Manage Customers" || title === "Edit Customer" || title === "Manage Staff" || title === "Edit Staff") {
		$(".nav6").addClass("active");
		$(".userNav1").addClass("userNavActive");
	}

	if (title === "Manage Customers" || title === "Edit Customer") {
		$(".nav6").addClass("active");
		$(".userNav1").addClass("subNavActive");
	}
	else if (title === "Manage Staff" || title === "Edit Staff") {
		$(".nav6").addClass("active");
		$(".userNav2").addClass("subNavActive");
	}
	else if (title === "Manage Rooms" || title === "Edit Room") {
		$(".nav6").addClass("active");
		$(".roomNav1").addClass("subNavActive");
	}
	else if (title === "Manage Room Types" || title === "Edit Room Type") {
		$(".nav6").addClass("active");
		$(".roomNav2").addClass("subNavActive");
	}
	else if (title === "Manage Bookings" || title === "Edit Booking") {
		$(".nav6").addClass("active");
		$(".roomNav3").addClass("subNavActive");
	}

});