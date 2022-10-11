package com.javachinna.service;


import com.javachinna.model.DatabaseFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface IFileService {
    public void addCVAndAssignToStudent(MultipartFile file,Long idStudent );
    void store(MultipartFile file, DatabaseFile f);
    Resource loadProductFiles(String filename, Integer  fileId);
    void init();
    public DatabaseFile storeFile(MultipartFile file,Long IdStudent,Integer idUniversity);
    public DatabaseFile uploadFile(MultipartFile file,Integer idUniversity);
    public DatabaseFile getFile(String fileId) throws FileNotFoundException;





}
