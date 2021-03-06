document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});


// In this function I will have to:
// Validate that the Employee ID is numeric and not blank
// Validate that the password is not blank
function validateForm() {
	employeeId = getEmployeeId();
	password = getPassword();

	if((isNumeric(employeeId) && employeeId.length() > 0) && password.length() > 0) {
	    return true;
	}
	return false;
}

function isNumeric(input) {
    return !isNaN(input);
}

function getEmployeeId() {
    return document.getElementById("employeeId");
}

function getPassword() {
    return document.getElementById("password");
}