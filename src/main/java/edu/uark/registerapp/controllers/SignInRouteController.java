package edu.uark.registerapp.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;


@Controller
public class SignInRouteController {
    @Autowired
    private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;

    @Autowired
    private EmployeeSignInCommand employeeSignInCommand;

    protected static final String REDIRECT_PREPEND = "redirect:";

    //Main menu redirect
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showDocument(@RequestParam Map<String, String> allParams) {
        ModelAndView modelAndView =
                new ModelAndView(ViewNames.SIGN_IN.getViewName());

        try {
            this.activeEmployeeExistsQuery.execute();
        } catch (NotFoundException e) {
            return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.PLACE_HOLDER.getRoute()));
            //TODO: Change PLACE_HOLDER to EMPLOYEE_DETAIL
            //when employee detail page is done
        }

        if (allParams.containsKey(QueryParameterNames.EMPLOYEE_ID.getValue())) {
            modelAndView.addObject(
                    QueryParameterNames.EMPLOYEE_ID.getValue(),
                    allParams.get(QueryParameterNames.EMPLOYEE_ID.getValue()));
        }

        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView signIn(EmployeeSignIn employee, HttpServletRequest request) {
        ModelAndView modelAndView =
                new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()));
        try {
            this.employeeSignInCommand.setSessionKey(request.getSession().getId());
            this.employeeSignInCommand.setEmployeeSignIn(employee);
            this.employeeSignInCommand.execute();
        } catch (Exception e) {
			modelAndView =
				new ModelAndView(ViewNames.SIGN_IN.getViewName());

			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());
			modelAndView.addObject(
				ViewModelNames.EMPLOYEE_ID.getValue(),
				employee.getEmployeeId());

			return modelAndView;
        }
        return modelAndView;
    }

}
