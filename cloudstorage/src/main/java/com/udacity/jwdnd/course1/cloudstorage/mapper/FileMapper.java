package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    /*
        public UserFile(Integer fileId, String filename, String contenttype, String filesize, Integer userid, Blob filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }
     */

    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{fileId}, #{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(UserFile file);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    UserFile getFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<UserFile> getFileList(Integer userid);

    @Select("SELECT filename FROM FILES WHERE userid = #{userid}")
    List<String> getFileNames(Integer userid);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);
}
