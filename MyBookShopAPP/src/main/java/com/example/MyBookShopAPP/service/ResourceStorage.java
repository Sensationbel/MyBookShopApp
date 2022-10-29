package com.example.MyBookShopAPP.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class ResourceStorage {

    @Value("${upload.path}")
    String uploadPath;

    public String saveNewBookImage(MultipartFile file, String slug, String folder) throws IOException {

        String resourceURI = null;
        String finishPath = uploadPath + folder;

        if(!file.isEmpty()){
            if(! new File(finishPath).exists()){
                Files.createDirectories((Paths.get(finishPath)));
                log.info("created image folder in: " + finishPath);
            }
            String fileName = slug + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            Path path = Paths.get(finishPath, fileName);
            resourceURI =folder + fileName;
            file.transferTo(path);
            log.info(fileName + " uploaded Ok!");
        }
        return resourceURI;
    }
}
