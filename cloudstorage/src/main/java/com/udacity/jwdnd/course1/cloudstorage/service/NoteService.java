package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    //private List<Note> noteList;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
        //this.noteList = new ArrayList<Note>();
    }

    public Note getNote(Integer noteid){
        return noteMapper.getNote(noteid);
    }

    public List<Note> getNoteList(Integer userid) {
        return noteMapper.getNoteList(userid);
    }

    public int addNote(Note note) {
        return noteMapper.insertNote(note);
    }

    public int updateNote(Integer noteid, String noteTitle, String noteDescription) {
        return noteMapper.updateNote(noteid, noteTitle, noteDescription);
    }

    public int deleteNote(Integer noteid){
        return noteMapper.deleteNote(noteid);
    }








}
