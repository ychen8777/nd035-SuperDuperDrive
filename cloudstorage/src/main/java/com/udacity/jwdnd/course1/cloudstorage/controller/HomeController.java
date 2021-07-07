package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    //private User user;
    private CredentialService credentialService;

    public HomeController(FileService fileService,NoteService noteService, CredentialService credentialService) {

        this.fileService = fileService;
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

    // File
    @PostMapping(value = "/files/add")
    public String addFile(@RequestParam("fileUpload") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            String contenttype = file.getContentType();
            String filesize = Long.toString(file.getSize());
            byte[] filedata = file.getBytes();

            fileService.uploadFile(filename, contenttype, filesize, getUserid(),filedata);

            return "redirect:/result/success";
        } catch(Exception e ) {
            System.out.println(e);
            return "redirect:/result/error";
        }
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
            credentialService.addCredential(credential.getUrl(), credential.getUsername(), credential.getDecryptedPassword(), getUserid());
            return "redirect:/result/success";
        } catch(Exception e ) {
            //System.out.println(e);
            return "redirect:/result/error";
        }
    }

    @PostMapping(value = "/credentials/update")
    public String updateCredential(@ModelAttribute("credential") Credential credential, Model model) {
        try {
            credentialService.updateCredential(credential.getCredentialid(), credential.getUrl(), credential.getUsername(),
                    credential.getDecryptedPassword());
            return "redirect:/result/success";
        } catch(Exception e ) {
            //System.out.println(e);
            return "redirect:/result/error";
        }
    }

    @GetMapping(value = "/credentials/delete/{id}")
    public String deleteCredential(@PathVariable("id") Integer credentialid, @ModelAttribute("credential") Credential credential, Model model) {
        try{
            credentialService.deleteCredential(credentialid);
            return "redirect:/result/success";
        } catch(Exception e) {
            return "redirect:/result/error";
        }
    }


    public Integer getUserid(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return user.getUserid();
    }


}
