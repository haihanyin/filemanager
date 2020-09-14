package com.github.hhy.filemanager.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileOpServiceTest {

    @Test
    void openFile() {
        final FileOpService fileOpService = new FileOpService("C:\\Users\\kh83kh\\Downloads");
        fileOpService.openFile("epdf.pub_camel-in-action.pdf");
    }
}