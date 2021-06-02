package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String gotoHome(){
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = (User) auth.getPrincipal();
        //System.out.println("userid: " + user.getUserid());

        return "home";
    }


}
