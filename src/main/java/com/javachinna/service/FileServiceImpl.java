package com.javachinna.service;


import com.javachinna.fileUpload.FileStorageException;
import com.javachinna.model.DatabaseFile;
import com.javachinna.model.PartnerInstitution;
import com.javachinna.model.Profession;
import com.javachinna.model.User;
import com.javachinna.repo.DatabaseFileRepository;
import com.javachinna.repo.IPartnerRepository;
import com.javachinna.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements IFileService{
    private final String uploadFolderPath="/Users/Drissi Omar/Desktop/uploadfolder";
    private final Path rootLocationProduct = Paths.get("product-dir");

    @Autowired
    DatabaseFileRepository filerepo;
    @Autowired
    UserRepository userrepo;
    @Autowired
    IPartnerRepository partnerRepository;


    @Override
    public void addCVAndAssignToStudent(MultipartFile file, Long idStudent) {

    }

    @Override
    public void store(MultipartFile file, DatabaseFile f) {

    }

    @Override
    public Resource loadProductFiles(String filename, Integer fileId) {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public DatabaseFile storeFile(MultipartFile file,Long IdStudent,Integer idUniversity) {

        User student = userrepo.findById(IdStudent).orElse(null);
        PartnerInstitution university=partnerRepository.findById(idUniversity).orElse(null);
        assert student != null;
        int b = student.getProfession().compareTo(Profession.STUDENT);

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

          DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());
            if(b==0){
                dbFile.setUser(student);
                dbFile.setPartnerInstitution(university);
                 filerepo.save(dbFile);
            }
            return filerepo.save(dbFile);

            //return filerepo.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }



    }

    @Override
    public DatabaseFile uploadFile(MultipartFile file, Integer idUniversity) {
        PartnerInstitution university=partnerRepository.findById(idUniversity).orElse(null);
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());

            dbFile.setPartnerInstitution(university);
            filerepo.save(dbFile);

            return filerepo.save(dbFile);

            //return filerepo.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

    }

    public DatabaseFile getFile(String fileId) throws FileNotFoundException {
        return filerepo.findById(String.valueOf(Integer.valueOf(fileId)))
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }




}



