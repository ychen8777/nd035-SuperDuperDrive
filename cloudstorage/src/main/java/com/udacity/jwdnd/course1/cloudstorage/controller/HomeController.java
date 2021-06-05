package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

    private NoteService noteService;
    //private User user;

    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/home")
    public String gotoHome(@ModelAttribute("note") Note note, Model model){
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = (User) auth.getPrincipal();
        //System.out.println("userid: " + user.getUserid());

        ArrayList<Note> noteList = (ArrayList<Note>) noteService.getNoteList(getUserid());
        model.addAttribute("noteList", noteList);

        return "home";
    }

    // Note

    @PostMapping(value = "addNote")
    public String addNote(@ModelAttribute("note") Note note, Model model){
        Note newNote = new Note(null, note.getNoteTitle(), note.getNoteDescription(), getUserid());
        noteService.addNote(newNote);

        return "redirect:/result";
    }
    








    public Integer getUserid(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return user.getUserid();
    }


}
