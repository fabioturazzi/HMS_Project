function passVal() {
	var x = document.getElementById("password").value;
	var y = document.getElementById("confPassword").value;
	var ok = true;
	if (x !== y) {
		//	alert("Passwords do not match");
		document.getElementById("passMismatch").style.display = "block";
		ok = false;

	}
	else {
		document.getElementById("passMismatch").style.display = "none";
	}
	return ok;
}


