package com.github.hhy.filemanager.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FileInfoDto {
    private Long id;
    private String filePath;
    private String keyDates;
    private String tags;
}
