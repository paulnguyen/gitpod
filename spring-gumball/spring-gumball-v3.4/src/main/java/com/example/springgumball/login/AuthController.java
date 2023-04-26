package com.example.springgumball.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model, @Valid User user, BindingResult bindingResult){
        return "auth/login";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/login";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String registerUser(Model model, @Valid User user, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            System.out.println( "User registration failed!" ) ;
            model.addAttribute("successMessage", "User registration failed!");
            model.addAttribute("bindingResult", bindingResult);
            return "redirect:" + "register?regfailed=true";
        }

        List<Object> userPresentObj = userService.isUserPresent(user);
        if((Boolean) userPresentObj.get(0)){
            System.out.println( "User already registered!" ) ;
            model.addAttribute("successMessage", userPresentObj.get(1));
            return "redirect:" + "register?userexists=true";
        }

        user.setRole(Role.USER);
        userService.saveUser(user);
        model.addAttribute("successMessage", "User registered successfully!");

        return "redirect:" + "login";
    }
}

