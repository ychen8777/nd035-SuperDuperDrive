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

    @Insert("INSERT INTO FILES (fileID, filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{fileID}, #{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileID")
    int insertFile(UserFile file);

    @Select("SELECT * FROM FILES WHERE fileID = #{fileID}")
    UserFile getFile(Integer fileID);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<UserFile> getFileList(Integer userid);

    @Delete("DELETE FROM FILES WHERE fileID = #{fileID}")
    int deleteFile(Integer fileID);
}
