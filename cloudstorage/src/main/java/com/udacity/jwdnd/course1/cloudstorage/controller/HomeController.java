package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class HomeController {

    private NoteService noteService;
    //private User user;
    private CredentialService credentialService;

    public HomeController(NoteService noteService, CredentialService credentialService) {

        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String gotoHome(@ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model){
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = (User) auth.getPrincipal();
        //System.out.println("userid: " + user.getUserid());

        // list notes
        ArrayList<Note> noteList = (ArrayList<Note>) noteService.getNoteList(getUserid());
        model.addAttribute("noteList", noteList);

        // list credentials
        ArrayList<Credential> credentialList = (ArrayList<Credential>) credentialService.getCredentialList(getUserid());
        model.addAttribute("credentials", credentialList);

        return "home";
    }

    // Note
    @PostMapping(value = "/notes/add")
    public String addNote(@ModelAttribute("note") Note note, Model model){
        try {

            Note newNote = new Note(null, note.getNoteTitle(), note.getNoteDescription(), getUserid());
            noteService.addNote(newNote);

            return "redirect:/result/success";
        } catch(Exception e ) {
            return "redirect:/result/error";
        }
    }

    @GetMapping(value = "/notes/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer noteid, @ModelAttribute("note") Note note, Model model) {
        Note theNote = noteService.getNote(noteid);
        model.addAttribute("theNote", theNote);

        return "home";
    }

    @PostMapping(value = "/notes/update")
    public String updateNote(@ModelAttribute("note") Note note, Model model) {
        try {
            noteService.updateNote(note.getNoteid(), note.getNoteTitle(), note.getNoteDescription());

            return "redirect:/result/success";
        } catch(Exception e) {
            return "redirect:/result/error";
        }
    }

    @GetMapping(value = "/notes/delete/{id}")
    public String deleteNote(@PathVariable("id") Integer noteid, @ModelAttribute("note") Note note, Model model) {
        try {
            noteService.deleteNote(noteid);
            return "redirect:/result/success";
        }
        catch(Exception e) {
            return "redirect:/result/error";
        }
    }

    // Credentials
    @PostMapping(value = "/credentials/add")
    public String addCredential(@ModelAttribute("credential") Credential credential, Model model) {
        try {
            credentialService.addCredential(credential.getUrl(), credential.getUsername(), credential.getPassword(), getUserid());
            return "redirect:/result/success";
        } catch(Exception e ) {
            System.out.println(e);
            return "redirect:/result/error";
        }
    }


    public Integer getUserid(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return user.getUserid();
    }


}
