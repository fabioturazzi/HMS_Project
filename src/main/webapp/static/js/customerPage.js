function passVal() {
	var x = document.getElementById("password").value;
	var y = document.getElementById("confPassword").value;
	// window.location.reload(true);
	
	var ok = true;
	if (x !== y) {
		//	alert("Passwords do not match");
		document.getElementById("passMismatch").style.display = "block";
		document.getElementById("greatsuccess").style.color ="blue";
		ok = false;

	}
	else {
		document.getElementById("passMismatch").style.display = "none";
		document.getElementById("greatsuccess").style.display ="block";
	}

	return ok;
}


