package com.github.hhy.filemanager.controller;

import com.github.hhy.filemanager.dto.FileInfoDto;
import com.github.hhy.filemanager.service.InfoLoadService;
import com.github.hhy.filemanager.service.InfoSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RequestMapping("/filemanager")
@Controller
public class FileManagerController {

    @Autowired
    private InfoLoadService infoLoadService;

    @Autowired
    private InfoSearchService infoSearchService;

    @Value("${filemanager.root-path}")
    private String fileManagerRootPath;

    @GetMapping()
    public String index(Model model) {
        final Path rootPath = Paths.get(fileManagerRootPath);
        if (Files.exists(rootPath)) {
            final List<FileInfoDto> fileInfoDtos = infoLoadService.load(rootPath);
            model.addAttribute("fileInfoDtoList", fileInfoDtos);
        }
        return "index";
    }

    @GetMapping("/searchByTags")
    public String searchWithTags(@RequestParam String[] tags, Model model) {
        final List<FileInfoDto> fileInfoDtos = infoSearchService.searchByTags(tags);
        model.addAttribute("fileInfoDtoList", fileInfoDtos);
        return "index";
    }

    @GetMapping("/searchByDate")
    public String searchWithDate(@RequestParam Integer year, @RequestParam Integer month, @RequestParam Integer day, Model model) {
        final List<FileInfoDto> fileInfoDtos = infoSearchService.searchByKeyDates(year, month, day);
        model.addAttribute("fileInfoDtoList", fileInfoDtos);
        return "index";
    }

    @GetMapping("/searchByName")
    public String searchWithName(@RequestParam String name, Model model) {
        final List<FileInfoDto> fileInfoDtos = infoSearchService.searchByFileName(name);
        model.addAttribute("fileInfoDtoList", fileInfoDtos);
        return "index";
    }
}
