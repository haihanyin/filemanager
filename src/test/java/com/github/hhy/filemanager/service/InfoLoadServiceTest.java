package com.github.hhy.filemanager.service;

import com.github.hhy.filemanager.dao.FileInfoDao;
import com.github.hhy.filemanager.domain.FileInfo;
import com.github.hhy.filemanager.dto.FileInfoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Import(InfoLoadService.class)
@DataJpaTest
class InfoLoadServiceTest {

    @Autowired
    private InfoLoadService infoLoadService;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Test
    void load() {

    }

    @Test
    void scanDirectory() {
        final Path rootPath = Paths.get("src/test/resources/scanDir");
        infoLoadService.scanDirectory(rootPath);

        assertThat(fileInfoDao.findAll().stream().map(FileInfo::toString).collect(Collectors.toList())).containsExactlyInAnyOrder(
                "FileInfo(id=1, filePath=subDir/file2, keyDate1=null, keyDate2=null, keyDate3=null, tags=null)",
                "FileInfo(id=2, filePath=subDir/deepDir/file3, keyDate1=null, keyDate2=null, keyDate3=null, tags=null)",
                "FileInfo(id=3, filePath=file1, keyDate1=null, keyDate2=null, keyDate3=null, tags=null)"
        );

        // scan again
        infoLoadService.scanDirectory(rootPath);
        assertThat(fileInfoDao.findAll().stream().map(FileInfo::toString).collect(Collectors.toList())).containsExactlyInAnyOrder(
                "FileInfo(id=1, filePath=subDir/file2, keyDate1=null, keyDate2=null, keyDate3=null, tags=null)",
                "FileInfo(id=2, filePath=subDir/deepDir/file3, keyDate1=null, keyDate2=null, keyDate3=null, tags=null)",
                "FileInfo(id=3, filePath=file1, keyDate1=null, keyDate2=null, keyDate3=null, tags=null)"
        );
    }

    @Test
    void toFileInfoDtoList() {
        final FileInfo fileInfo = new FileInfo();
        fileInfo.setId(1L);
        fileInfo.setFilePath("temppath");
        fileInfo.setTags("a,b,c");
        fileInfo.setKeyDate1(LocalDate.of(2020,9,11));
        fileInfo.setKeyDate2(LocalDate.of(2020,9,12));
        fileInfo.setKeyDate3(LocalDate.of(2020,9,13));

        final FileInfo fileInfo2 = new FileInfo();
        fileInfo2.setId(2L);
        fileInfo2.setFilePath("temppath2");

        final List<FileInfoDto> fileInfoDtos = infoLoadService.toFileInfoDtoList(Arrays.asList(fileInfo, fileInfo2));
        assertThat(fileInfoDtos.get(0).toString()).isEqualTo("FileInfoDto(id=1, filePath=temppath, keyDates=2020-09-11,2020-09-12,2020-09-13, tags=a,b,c)");
        assertThat(fileInfoDtos.get(1).toString()).isEqualTo("FileInfoDto(id=2, filePath=temppath2, keyDates=, tags=)");

    }

    @Test
    void save() {
        final FileInfo fileInfo = new FileInfo();
        fileInfo.setFilePath("tempPath");
        fileInfoDao.save(fileInfo);

        final FileInfoDto fileInfoDto = new FileInfoDto();
        fileInfoDto.setId(fileInfo.getId());
        fileInfoDto.setFilePath("updatedPath");
        fileInfoDto.setKeyDates("2020-09-11,2020-09-12");
        fileInfoDto.setTags("A,B,C,D");

        infoLoadService.update(fileInfoDto);

        final FileInfo updatedFileInfo = fileInfoDao.findById(fileInfo.getId()).get();
        assertThat(updatedFileInfo.getFilePath()).isEqualTo("tempPath");
        assertThat(updatedFileInfo.getKeyDate1()).isEqualTo("2020-09-11");
        assertThat(updatedFileInfo.getKeyDate2()).isEqualTo("2020-09-12");
        assertThat(updatedFileInfo.getKeyDate3()).isNull();
        assertThat(updatedFileInfo.getTags()).isEqualTo("a,b,c,d");
    }
}