package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.UUID;

import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;

import javax.servlet.http.HttpServletRequest;


@Controller
public class SignInRouteController {

    @Autowired
    private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;

    @Autowired
    private EmployeeSignInCommand employeeSignInCommand;

    //Main menu redirect
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ModelAndView redirectToMainMenu() {
        return new ModelAndView("redirect:" + "localhost:8080");
    }

    @RequestMapping(value = "/signInDoc", method = RequestMethod.GET)
    public ModelAndView showDocument(@RequestParam Map<String, String> allParams) {
        ModelAndView modelAndView =
                new ModelAndView(ViewNames.SIGN_IN.getViewName());

        try {
            //check if an employee exists
            this.activeEmployeeExistsQuery.execute();
        }
        catch (Exception e) {
            redirectToMainMenu();
        }

        return modelAndView;
    }


    @RequestMapping(value = "/signInDoc", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView signIn(EmployeeSignIn employee, HttpServletRequest request) {
        ModelAndView modelAndView =
                new ModelAndView(ViewNames.SIGN_IN.getViewName());

        try {
            this.employeeSignInCommand.setSessionKey(request.getSession().getId());
            this.employeeSignInCommand.setEmployeeSignIn(employee);
            this.employeeSignInCommand.execute();
        }
        catch (Exception e) {
            System.out.println("Your sign in was not successful.");
            return modelAndView;
        }
        return redirectToMainMenu();
    }

}