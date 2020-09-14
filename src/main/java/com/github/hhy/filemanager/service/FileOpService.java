package com.github.hhy.filemanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileOpService {

    private String fileManagerRootPath;

    public FileOpService(@Value("${filemanager.root-path}") String fileManagerRootPath) {
        this.fileManagerRootPath = fileManagerRootPath;
    }

    public void openFile(String name) {
        final Path filePath = Paths.get(fileManagerRootPath).resolve(name);
        if (!Files.exists(filePath)) {
            log.warn("File " + name + " does not exist.");
            return;
        }
        log.info(filePath.toString());
        try {
            Desktop.getDesktop().open(filePath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
