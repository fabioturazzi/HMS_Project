


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
	else if (title === "Manage Customers" || title === "Edit Customer" || title === "Manage Staff" || title === "Edit Staff") {
		$(".nav6").addClass("active");
	}

	if (title === "Manage Customers" || title === "Edit Customer") {
		$(".dataNav1").addClass("subNavActive");
		$(".userNav1").addClass("userNavActive");
	}
	else if (title === "Manage Staff" ||  title === "Edit Staff") {
		$(".dataNav1").addClass("subNavActive");
		$(".userNav2").addClass("userNavActive");
	}

});