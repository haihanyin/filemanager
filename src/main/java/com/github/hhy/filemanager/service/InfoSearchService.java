package com.github.hhy.filemanager.service;

import com.github.hhy.filemanager.dao.FileInfoDao;
import com.github.hhy.filemanager.domain.FileInfo;
import com.github.hhy.filemanager.dto.FileInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfoSearchService {

    @Autowired
    private InfoLoadService infoLoadService;

    @Autowired
    private FileInfoDao fileInfoDao;

    public List<FileInfoDto> searchByTags(String... tags) {
        final List<String> lowerCaseTags = Arrays.stream(tags).map(String::toLowerCase).collect(Collectors.toList());

        final List<FileInfo> fileInfoList = fileInfoDao.findAll().stream()
                .filter(fileInfo -> fileInfo.getTags() != null && containsAll(fileInfo.getTags(), lowerCaseTags))
                .collect(Collectors.toList());

        return infoLoadService.toFileInfoDtoList(fileInfoList);
    }

    public List<FileInfoDto> searchByKeyDates(Integer year, Integer month, Integer day) {
        final List<FileInfo> fileInfoList = fileInfoDao.findAll().stream()
                .filter(fileInfo ->
                        isDateMatched(fileInfo.getKeyDate1(), year, month, day) ||
                                isDateMatched(fileInfo.getKeyDate2(), year, month, day) ||
                                isDateMatched(fileInfo.getKeyDate3(), year, month, day))
                .collect(Collectors.toList());
        return infoLoadService.toFileInfoDtoList(fileInfoList);
    }

    public List<FileInfoDto> searchByFileName(String fileNameText) {
        final String lowercaseFileNameText = fileNameText.toLowerCase();
        final List<FileInfo> fileInfoList = fileInfoDao.findAll().stream()
                .filter(fileInfo -> fileInfo.getFilePath().toLowerCase().contains(lowercaseFileNameText))
                .collect(Collectors.toList());
        return infoLoadService.toFileInfoDtoList(fileInfoList);
    }

    private boolean isDateMatched(LocalDate date, Integer year, Integer month, Integer day) {
        if (date == null) {
            return false;
        }
        if (year == null) { // if year is not specified, always matched
            return true;
        }
        if (month == null) { // only year is specified
            return date.getYear() == year;
        }
        if (day == null) {
            return date.getYear() == year && date.getMonth().getValue() == month;
        }
        return date.equals(LocalDate.of(year, month, day));
    }

    private boolean containsAll(String fileInfoTags, List<String> lowerCaseTags) {
        for (String lowerCaseTag : lowerCaseTags) {
            if (!fileInfoTags.contains(lowerCaseTag)) {
                return false;
            }
        }
        return true;
    }
}
