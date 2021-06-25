package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    /*
        @Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid) " +
            "VALUES(#{noteid}, #{noteTitle}, #{noteDescription}, #{userid})")
     */


    @Insert("INSERT INTO CREDENTIALS (credentialid, url, username, encodedkey, password, userid) " +
            "VALUES(#{credentialid}, #{url}, #{username}, #{key}, #{password}, #{userid}")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(Integer credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentialList(Integer userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int deleteCredential(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} " +
    "WHERE credentialid = #{credentialid}")
    int updateCredential(Integer credentialid, String url, String username, String password);


}
