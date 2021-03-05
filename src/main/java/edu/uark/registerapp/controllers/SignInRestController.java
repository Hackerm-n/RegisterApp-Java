package edu.uark.registerapp.controllers;

import javax.servlet.http.HttpServletRequest;

import java.util.UUID;

import edu.uark.registerapp.controllers.enums.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.commands.activeUsers.ActiveUserDeleteCommand;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class SignInRestController {

    @Autowired
    private ActiveUserDeleteCommand activeUserDeleteCommand;

    //sign in redirect
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ApiResponse redirectToSignInDoc() {
        ApiResponse response = new ApiResponse();
        response.setRedirectUrl(ViewNames.SIGN_IN.getRoute());
        return response;
    }

    //double check and make sure that route is good with jan
    @RequestMapping(value = "/api/signOut", method = RequestMethod.DELETE)
    public ApiResponse removeUser(HttpServletRequest request) {

        try{
            //remove any record in the activeuser table associated with the current session ID
            this.activeUserDeleteCommand.setSessionKey(request.getSession().getId());
            this.activeUserDeleteCommand.execute();
        } catch (Exception e) {}

        return redirectToSignInDoc();
    }
}
