package com.github.hhy.filemanager.service;

import com.github.hhy.filemanager.dto.FileInfoDto;
import com.github.hhy.filemanager.dao.FileInfoDao;
import com.github.hhy.filemanager.domain.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class InfoLoadService {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    private FileInfoDao fileInfoDao;

    public List<FileInfoDto> load(Path rootPath) {
        scanDirectory(rootPath);
        final List<FileInfoDto> fileInfoDtos = toFileInfoDtoList(fileInfoDao.findAll());
        return fileInfoDtos;
    }

    public List<FileInfoDto> toFileInfoDtoList(List<FileInfo> fileInfoList) {
        final List<FileInfoDto> fileInfoDtoList = fileInfoList.stream()
                .map(fileInfo -> {
                    final FileInfoDto fileInfoDto = new FileInfoDto();
                    fileInfoDto.setId(fileInfo.getId());
                    fileInfoDto.setFilePath(fileInfo.getFilePath());
                    final String keyDates = Stream.of(fileInfo.getKeyDate1(), fileInfo.getKeyDate2(), fileInfo.getKeyDate3())
                            .filter(Objects::nonNull)
                            .map(date -> date.format(DATE_TIME_FORMATTER))
                            .collect(Collectors.joining(","));
                    fileInfoDto.setKeyDates(keyDates);
                    fileInfoDto.setTags(fileInfo.getTags() != null ? fileInfo.getTags() : "");
                    return fileInfoDto;
                })
                .collect(Collectors.toList());
        return fileInfoDtoList;
    }

    public void scanDirectory(Path rootPath) {
        final Collection<File> files = FileUtils.listFiles(rootPath.toFile(), null, true);
        final Set<String> relativePaths = files.stream()
                .map(file -> rootPath.relativize(file.toPath()).toString().replace("\\", "/"))
                .collect(Collectors.toSet());
        final List<FileInfo> all = fileInfoDao.findAll();
        final Set<String> savedRelativePaths = all.stream().map(FileInfo::getFilePath).collect(Collectors.toSet());
        for (String relativePath : relativePaths) {
            if (!savedRelativePaths.contains(relativePath)) {
                final FileInfo fileInfo = new FileInfo();
                fileInfo.setFilePath(relativePath);
                fileInfoDao.save(fileInfo);
            }
        }
    }

    public void update(FileInfoDto fileInfoDto) {
        final Long id = fileInfoDto.getId();
        final Optional<FileInfo> optionalFileInfo = fileInfoDao.findById(id);
        if (optionalFileInfo.isPresent()) {
            final FileInfo fileInfo = optionalFileInfo.get();
            if (fileInfoDto.getKeyDates() != null && fileInfoDto.getKeyDates().length() > 0) {
                final String[] keyDates = fileInfoDto.getKeyDates().split(",");
                setDate(fileInfo, keyDates, 0, FileInfo::setKeyDate1);
                setDate(fileInfo, keyDates, 1, FileInfo::setKeyDate2);
                setDate(fileInfo, keyDates, 2, FileInfo::setKeyDate3);
            }
            fileInfo.setTags(fileInfoDto.getTags().toLowerCase());
            fileInfoDao.saveAndFlush(fileInfo);
        }
    }

    private void setDate(FileInfo fileInfo, String[] keyDates, int dateIdx, BiConsumer<FileInfo, LocalDate> setFunc) {
        if (keyDates.length > dateIdx) {
            try {
                setFunc.accept(fileInfo, LocalDate.parse(keyDates[dateIdx], DATE_TIME_FORMATTER));
            } catch (DateTimeParseException dtp) {
                log.warn("Cannot parse date " + keyDates[dateIdx]);
            }
        }
    }
}
