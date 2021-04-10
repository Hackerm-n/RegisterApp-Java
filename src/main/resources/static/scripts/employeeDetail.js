let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	document.getElementById('employeeSavedAlertModal').style.display = "none";
	document.getElementById('employeeNotSavedAlertModal').style.display = "none";

	document.getElementById("saveButton").addEventListener("click", onSave);
});

function onSave() {
	var firstName = document.getElementById("firstName");
	var lastName = document.getElementById("lastName");
	var password = document.getElementById("password");
	var confirmPassword = document.getElementById("confirmPassword");

	console.log('saving')

	if (firstName.value === '') {
		firstName.focus();
		saveFail();
	} else if (lastName.value === '') {
		lastName.focus();
		saveFail();
	} else if (password.value === '') {
		password.focus();
		saveFail();
	} else if (confirmPassword.value !== password.value) {
		confirmPassword.focus();
		saveFail();
	} else {
		saveSuccess();
	}
}

function saveSuccess() {
	document.getElementById('saveButton').disabled = true;
	var employeeSaved = document.getElementById('employeeSavedAlertModal');
	var employeeNotSaved = document.getElementById('employeeNotSavedAlertModal');
	employeeSaved.style.display = "block";
	employeeNotSaved.style.display = "none";
	var employeeId = document.getElementById("employeeEmployeeId");

	var employeeIdIsDefined = (employeeId != null || employeeId.trim() !== '' || employeeId != undefined);
	const saveActionUrl = ("/api/employee/");
	var saveEmployeeRequest =
    {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        password: document.getElementById("password").value,
        classification: document.getElementById("employeeType").value,
    };

    ajaxPost(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
        document.getElementById('saveButton').disabled = false;
        if (isSuccessResponse(callbackResponse)) {
            displayEmployeeSavedAlertModal();
            if(callbackResponse.data.employeeId !== undefined && callbackResponse.data.employeeId !== null) {
                employeeId.value = callbackResponse.data.employeeId;
            }
            else {
                window.location.replace(callbackResponse.data.redirectUrl);
            }
        }
    });

}

function saveFail() {
	var employeeSaved = document.getElementById('employeeSavedAlertModal');
	var employeeNotSaved = document.getElementById('employeeNotSavedAlertModal');
	employeeSaved.style.display = "none";
	employeeNotSaved.style.display = "block";
}

function displayEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModal, 1200);
}

function hideEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	getSavedAlertModalElement().style.display = "none";
}
// End save

//Getters and setters
function getSavedAlertModalElement() {
	return document.getElementById("employeeSavedAlertModal");
}
//End getters and setters
