package edu.uark.registerapp.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.models.api.ApiResponse;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SignInRestController {

    //sign in redirect
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ModelAndView redirectToSignInDoc() {
        return new ModelAndView("redirect:" + "/signInDoc");
    }

    //double check and make sure that route is good with jan
    @RequestMapping(value = "/api/signOut", method = RequestMethod.DELETE)
    public ApiResponse removeUser(HttpServletRequest request) {

        request.getRequestedSessionId();
        //remove any record in the activeuser table associated with the current session ID


        //redirect to sign in view/document
        redirectToSignInDoc();
        ApiResponse response = new ApiResponse();
        response.setRedirectUrl("/signInDoc");
        return response;
    }
}
