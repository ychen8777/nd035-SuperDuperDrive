package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.ArrayList;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int uploadFile(String filename, String contenttype, String filesize, Integer userid, byte[] filedata) {
        UserFile newFile = new UserFile(null, filename, contenttype, filesize, userid, filedata);
        return fileMapper.insertFile(newFile);
    }

    public boolean fileExist(Integer userid, String filename) {
        ArrayList<String> fileNameList = (ArrayList<String>) fileMapper.getFileNames(userid);
        return fileNameList.contains(filename);
    }

    public ArrayList<UserFile> getFileList(Integer userid) {
        ArrayList<UserFile> fileList = (ArrayList<UserFile>) fileMapper.getFileList(userid);
        return fileList;
    }



}
