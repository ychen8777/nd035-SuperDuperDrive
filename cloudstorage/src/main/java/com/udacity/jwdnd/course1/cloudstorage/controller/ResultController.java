package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ResultController {

    @GetMapping("/result/success")
    public String successResult(Model model){
        String status = "success";
        model.addAttribute("status", status);
        return "result";
    }

    @GetMapping("/result/noSuccess")
    public String noSuccessResult(Model model){
        String status = "noSuccess";
        model.addAttribute("status", status);
        return "result";
    }

    @GetMapping("/result/error")
    public String errorResult(Model model){
        String status = "error";
        model.addAttribute("status", status);
        return "result";
    }

}
