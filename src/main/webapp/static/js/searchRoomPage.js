
$(function() {

	$("input.checkboxItems")[0].checked = true;
	$("span.checkboxItems").hide();
	$("span.checkboxItems").first().show();


	if ($(".successMessage").length == 0) {
		$(".availRooms").hide();
		$(".bookRooms").hide();
		$(".availRoomsHead").hide();
		$(".bookRoomsHead").hide();
		$(".bookRoomsCell").hide();

		$(".totalCost").hide();
		$(".totalCostHead").hide();
	}



	for (var i = 0; i < $(".availRooms").length; i++) {
		if ($(".availRooms").eq(i).html() === "0") {
			$(".availRooms").eq(i).css("color", "DimGrey");
			$(".bookRooms").eq(i).css("color", "DimGrey");
			$(".bookRooms").eq(i).removeAttr("href");

		};
	};


	$("input.checkboxItems").change(function() {
		if ($("input.checkboxItems")[0].checked == true) {

			$("span.checkboxItems").hide();
			$("span.checkboxItems").first().show();
			$("span.checkboxItems").first().css("color", "black");

		}
		else {
			$("span.checkboxItems").show();
			$("span.checkboxItems").first().css("color", "lightgray");
		}

	});

});