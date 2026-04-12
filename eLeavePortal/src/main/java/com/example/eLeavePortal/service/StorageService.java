package com.example.eLeavePortal.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
    @Value("${app.storage.pfp}") private String pfpPath;
    @Value("${app.storage.files}") private String filePath;

    /* ================= SAVE ================= */
    public String savePfp(MultipartFile file) {
        return save(file, Paths.get(pfpPath), "/pfp/");
    }

    public String saveFile(MultipartFile file) {
        return save(file, Paths.get(filePath), "/files/");
    }

    private String save(MultipartFile file, Path root, String urlPrefix) {
        try {
            Files.createDirectories(root);

            String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID().toString() + (ext != null ? "." + ext : "");
            Path destination = root.resolve(filename);

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            
            return urlPrefix + filename;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* ================= LOAD FROM DB URL ================= */
    public Resource loadFromDbPath(String dbPath) {
        try {
            if (dbPath.startsWith("/pfp/")) {
                String filename = dbPath.substring("/pfp/".length());
                return new UrlResource(Paths.get(pfpPath).resolve(filename).toUri());
            }
            if (dbPath.startsWith("/files/")) {
                String filename = dbPath.substring("/files/".length());
                return new UrlResource(Paths.get(filePath).resolve(filename).toUri());
            }
            throw new RuntimeException("Invalid path: " + dbPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /* ================= DELETE ================= */
    public void delete(String dbPath) throws IOException {
        if (dbPath.startsWith("/pfp/")) {
            String filename = dbPath.substring("/pfp/".length());
            Files.deleteIfExists(Paths.get(pfpPath).resolve(filename));
        }
        else if (dbPath.startsWith("/files/")) {
            String filename = dbPath.substring("/files/".length());
            Files.deleteIfExists(Paths.get(filePath).resolve(filename));
        }
    }
}
