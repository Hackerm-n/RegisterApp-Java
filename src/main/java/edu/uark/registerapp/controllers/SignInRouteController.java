package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.UUID;

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

import javax.servlet.http.HttpServletRequest;


@Controller
public class SignInRouteController {

    //Main menu redirect
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ModelAndView redirectToMainMenu() {
        return new ModelAndView("redirect:" + "localhost:8080");
    }

    @RequestMapping(value = "/signInDoc", method = RequestMethod.GET)
    public ModelAndView showDocument(@RequestParam Map<String, String> allParams) {
        ModelAndView modelAndView =
                new ModelAndView(ViewNames.SIGN_IN.getViewName());

        //Need to see task 5 for how to determine if employees exist given Map<String, String> parameter
        boolean employeesExist = false;
        if (employeesExist) {
            //Serve up sign in view
            return modelAndView;
        } else {
            redirectToMainMenu();
        }
        return null;
    }



    @RequestMapping(value = "/signInDoc", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void signIn(EmployeeSignIn employee, HttpServletRequest request) {
        //Placeholder condition until I know how to verify
        if (employee.getPassword() == employee.getPassword()) {
            //Create an record in the activeuser table using:
                //The current session ID
                //The employee details associated with the provided credentials
            redirectToMainMenu();


        } else {
            System.out.println("Your sign in was not successful.");
            //serve up sign in view
        }
    }

}