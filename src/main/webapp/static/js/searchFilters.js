$(function() {
	$("#searchInput").keyup(function() {
		$(".dataRows").each(function() {
			var self = $(this);
			var search = $("#searchInput").val().toLowerCase();
			var text = self.html().toLowerCase();
	
			if (text.indexOf(search) != -1) {
				self.show();
			}
			else {
				self.hide();
			}


		});
	});
});