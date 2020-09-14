package com.github.hhy.filemanager.dao;

import com.github.hhy.filemanager.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoDao extends JpaRepository<FileInfo, Long> {
}
