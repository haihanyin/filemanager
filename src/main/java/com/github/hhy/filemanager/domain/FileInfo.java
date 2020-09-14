package com.github.hhy.filemanager.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class FileInfo {
    @Id
    @GeneratedValue
    private Long id;
    private String filePath;
    private LocalDate keyDate1;
    private LocalDate keyDate2;
    private LocalDate keyDate3;
    private String tags;
}
