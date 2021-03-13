package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import javassist.bytecode.ExceptionTable;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		// TODO: Logic to determine if the user associated with the current session
		//  is able to create an employee
		
		final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);
		
		ModelAndView employeeDetailModelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.PLACE_HOLDER.getViewName()),
				queryParameters); //TODO change placeholder to employee detail

		if(activeEmployeeExists() || (activeUserEntity.isPresent() && activeUserEntity.get().getClassification() >= 501)) {
			employeeDetailModelAndView.addObject(
					ViewModelNames.IS_ELEVATED_USER.getValue(),
					true);
			return employeeDetailModelAndView;
		}

		else if (!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse(); //TODO make sure this redirects to SignIn
		}

		else
		{
			return 	new ModelAndView(REDIRECT_PREPEND.concat
				(ViewNames.MAIN_MENU.getRoute()));
		}
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	public ModelAndView startWithEmployee(
		@PathVariable final UUID employeeId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {

		final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);

		if (!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse();
		} else if (!this.isElevatedUser(activeUserEntity.get())) {
			return this.buildNoPermissionsResponse();
		}

		// TODO: Query the employee details using the request route parameter
		// TODO: Serve up the page
		return new ModelAndView(ViewModelNames.EMPLOYEE_TYPES.getValue());
	}

	// Helper methods
	private boolean activeUserExists() {
		ActiveEmployeeExistsQuery activeEmployeeExistsQuery = new ActiveEmployeeExistsQuery();
		try
		{
			activeEmployeeExistsQuery.execute();
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}

	private boolean activeEmployeeExists() {
		try
		{
			this.activeEmployeeExistsQuery.execute();
		}
		catch(NotFoundException e)
		{
			return false;
		}
		return true;
	}

	@Autowired
	ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
}
