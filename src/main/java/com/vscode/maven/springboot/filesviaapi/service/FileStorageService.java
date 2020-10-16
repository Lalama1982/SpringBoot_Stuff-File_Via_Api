package com.vscode.maven.springboot.filesviaapi.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService() {
        this.fileStorageLocation = Paths
                .get("E:\\codeWork\\VSCodeJava_Stuff\\SpringBoot\\filesviaapi\\TestFiles\\DownLoadTo").toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            // throw Execption
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println("storeFile ::: START");

        System.out.println("fileName >> " + fileName);

        System.out.println("storeFile ::: END");

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                // throw exception
            }

            // Renaming the file with date-time
            String[] parts = fileName.split(Pattern.quote("."));
            // System.out.println("parts >> " + parts[0]);
            String fileNameWithoutExt = parts[0];
            String fileExt = parts[1];
            System.out.println("fileNameWithoutExt >> " + fileNameWithoutExt);
            System.out.println("fileExt >> " + fileExt);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            System.out.println("timeStamp >> " + timeStamp);
            fileName = fileNameWithoutExt.concat("-").concat(timeStamp).concat(".").concat(fileExt);
            System.out.println("fileName [NEW] >> " + fileName);

            // Copy file to the target location (or Replacing if already existing)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            // throw new FileStorageException("Could not store file " + fileName + ". Please
            // try again!", ex);
        }
        return fileName;
    }

    public Resource returnFileContents(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                // throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            // throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
        return null;
    }

}
