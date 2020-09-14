package com.github.hhy.filemanager.controller;

import com.github.hhy.filemanager.service.FileOpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@RequestMapping("/filemanager-api")
@RestController
public class FileManagerRestController {

    @Autowired
    private FileOpService fileOpService;

    @GetMapping("/openFile")
    public void openFile(@RequestParam String name) {
        final String decodedName = new String(Base64.getDecoder().decode(name));
        log.info("Open file : " + decodedName);
        fileOpService.openFile(decodedName);
    }
}
