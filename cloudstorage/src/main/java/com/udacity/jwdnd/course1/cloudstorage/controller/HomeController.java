package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
    public String gotoHome(@ModelAttribute("file") UserFile file, @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model){
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = (User) auth.getPrincipal();
        //System.out.println("userid: " + user.getUserid());

        Integer userid = getUserid();

        // list files
        ArrayList<UserFile> fileList = (ArrayList<UserFile>) fileService.getFileList(userid);
        model.addAttribute("fileList", fileList);

        // list notes
        ArrayList<Note> noteList = (ArrayList<Note>) noteService.getNoteList(userid);
        model.addAttribute("noteList", noteList);

        // list credentials
        ArrayList<Credential> credentialList = (ArrayList<Credential>) credentialService.getCredentialList(userid);
        model.addAttribute("credentials", credentialList);

        return "home";
    }

    // File
    @PostMapping(value = "/files/add")
    public String addFile(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes attributes) {
        try {
            String filename = file.getOriginalFilename();
            String contenttype = file.getContentType();
            String filesize = Long.toString(file.getSize());
            byte[] filedata = file.getBytes();

            if (fileService.fileExist(this.getUserid(), filename)) {
                attributes.addFlashAttribute("filenameErrorMessage", filename);
                return "redirect:/home";
            }

            fileService.uploadFile(filename, contenttype, filesize, getUserid(),filedata);

            return "redirect:/result/success";
        } catch(Exception e ) {
            System.out.println(e);
            return "redirect:/result/error";
        }
    }

    @GetMapping(value = "/files/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id") Integer fileId) {

        UserFile file = fileService.getFile(fileId);

        // check if file exists
        if (file == null) {
            //System.out.println("file error");
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        // check if user is the owner
        if (!fileService.isOwner(fileId, getUserid())) {
            //System.out.println("user error");
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        String filename = file.getFilename();
        String contenttype = file.getContenttype();
        byte[] filedata = file.getFiledata();

        InputStream inputStream = new ByteArrayInputStream(filedata);
        InputStreamResource inputResource = new InputStreamResource(inputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.parseMediaType(contenttype).toString());
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return ResponseEntity.ok().headers(httpHeaders).body(inputResource);

    }

    @GetMapping( value = "/files/delete/{id}")
    public String deleteFile(@PathVariable("id") Integer fileId) {

        UserFile file = fileService.getFile(fileId);

        if (file == null) {
            return "redirect:/home";
        }

        if (!fileService.isOwner(fileId, getUserid())) {
            return "redirect:/home";
        }

        try {
            fileService.deleteFile(fileId);
            return "redirect:/result/success";
        }
        catch(Exception e) {
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
    public String deleteNote(@PathVariable("id") Integer noteid) {

        Note note = noteService.getNote(noteid);

        if (note == null) {
            return "redirect:/home";
        }

        if (!noteService.isOwner(noteid, getUserid())) {
            return "redirect:/home";
        }

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
    public String deleteCredential(@PathVariable("id") Integer credentialid) {

        Credential credential = credentialService.getCredential(credentialid);

        if (credential == null) {
            return "redirect:/home";
        }

        if (!credentialService.isOwner(credentialid, getUserid())){
            return "redirect:/home";
        }

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
