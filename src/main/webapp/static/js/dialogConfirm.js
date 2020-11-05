/**
 * 
 */

$(function() {
	$(".submit-delete").on("click", function(e) {
		var link = this;
		console.log("clicked");
		e.preventDefault();

		$("<div>Are you sure you want to continue?</div>").dialog({
			dialogClass: "no-close",
			title: "Delete Confirmation",
			buttons: {
				"Delete": function() {
					window.location = link.href;
				},
				"Cancel": function() {
					$(this).dialog("close");
				}
			}
		});
	});

	$(".confirm-booking").on("click", function(e) {
		var link = this;
		console.log("clicked");
		e.preventDefault();

		$("<div>Do you want to submit your booking?</div>").dialog({
			dialogClass: "no-close",
			title: "Booking Confirmation",
			buttons: {
				"Confirm!": function() {
					$(".booking").submit();
				},
				"Cancel": function() {
					$(this).dialog("close");
				}
			}
		});
	});
});