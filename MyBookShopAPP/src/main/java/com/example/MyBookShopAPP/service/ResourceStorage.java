package com.example.MyBookShopAPP.service;

import com.example.MyBookShopAPP.model.book.file.BookFileEntity;
import com.example.MyBookShopAPP.repositories.jpa_services.BookFileJpaServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceStorage {

    private final BookFileJpaServices bookFileJpaServices;

    @Value("${upload.path}")
    String uploadPath;

    @Value("${download.path}")
    String downloadPath;

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

    public Path getBookFilePath(String hash) {
        BookFileEntity bookFile = bookFileJpaServices.findBookFileEntitiesByHash(hash);
        return Paths.get(bookFile.getPath());
    }

    public MediaType getBookFileMime(String hash) {

        BookFileEntity bookFile = bookFileJpaServices.findBookFileEntitiesByHash(hash);
        String mimeType =
                URLConnection.guessContentTypeFromName(Paths.get(bookFile.getPath()).getFileName().toString());
        if(mimeType != null) {
            return  MediaType.parseMediaType(mimeType);
        } else{
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public byte[] getBookFileByteArray(String hash) throws IOException {

        BookFileEntity bookFile = bookFileJpaServices.findBookFileEntitiesByHash(hash);
        Path path = Paths.get(downloadPath, bookFile.getPath());
        return Files.readAllBytes(path);
    }
}
