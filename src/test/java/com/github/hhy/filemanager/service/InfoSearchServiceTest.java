package com.github.hhy.filemanager.service;

import com.github.hhy.filemanager.dao.FileInfoDao;
import com.github.hhy.filemanager.domain.FileInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Import({InfoSearchService.class, InfoLoadService.class})
@DataJpaTest
class InfoSearchServiceTest {

    @Autowired
    private InfoSearchService infoSearchService;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Test
    void searchByTags() {
        final FileInfo fileInfo = new FileInfo();
        final String filePath = UUID.randomUUID().toString();
        fileInfo.setFilePath(filePath);
        fileInfo.setTags("a,b,c");
        fileInfoDao.save(fileInfo);

        assertThat(infoSearchService.searchByTags("b")).hasSize(1);
        assertThat(infoSearchService.searchByTags("b", "a")).hasSize(1);
        assertThat(infoSearchService.searchByTags("d")).hasSize(0);
    }

    @Test
    void searchByKeyDates() {
        final FileInfo fileInfo = new FileInfo();
        final String filePath = UUID.randomUUID().toString();
        fileInfo.setFilePath(filePath);
        fileInfo.setKeyDate1(LocalDate.of(2020, 9, 11));
        fileInfoDao.save(fileInfo);

        assertThat(infoSearchService.searchByKeyDates(2020, null, null)).hasSize(1);
        assertThat(infoSearchService.searchByKeyDates(2020, 9, null)).hasSize(1);
        assertThat(infoSearchService.searchByKeyDates(2020, 9, 11)).hasSize(1);
        assertThat(infoSearchService.searchByKeyDates(2015, null, null)).hasSize(0);
    }

    @Test
    void searchByFileName() {
        final FileInfo fileInfo = new FileInfo();
        fileInfo.setFilePath("helloworld");
        fileInfoDao.save(fileInfo);

        assertThat(infoSearchService.searchByFileName("hello")).hasSize(1);
        assertThat(infoSearchService.searchByFileName("LOWO")).hasSize(1);
        assertThat(infoSearchService.searchByFileName("haihan")).hasSize(0);
    }
}