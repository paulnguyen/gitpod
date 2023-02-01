package com.example.springgumball;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
    https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html
 */

@Controller
class LoginController {
    @GetMapping("/login")
    String login() {
        return "login";
    }
}