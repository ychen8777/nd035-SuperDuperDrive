package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;

    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    public int addCredential(String url, String username, String decryptedPassword, Integer userid) {
        // generate key and encrypt password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(decryptedPassword, encodedKey);

        // save new credential
        Credential credential = new Credential(null, url, username, encodedKey, encryptedPassword, userid);
        //return 0;
        return credentialMapper.insertCredential(credential);
    }

    public ArrayList<Credential> getCredentialList(Integer userid) {
        ArrayList<Credential> credentialList = (ArrayList<Credential>) credentialMapper.getCredentialList(userid);
        for (Credential credential : credentialList){
            credential.setDecryptedPassword(decryptPassword(credential.getPassword(), credential.getKey()));
        }
        return credentialList;
    }

    public String decryptPassword(String encryptedPassword, String key) {
        return encryptionService.decryptValue(encryptedPassword, key);
    }

    public int deleteCredential(Integer credentialid) {
        return credentialMapper.deleteCredential(credentialid);
    }



}
