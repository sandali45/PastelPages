package com.example.pastelpages.service;
import com.example.pastelpages.model.ScrapbookPage;
import com.example.pastelpages.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class PageService {
    @Autowired
    private PageRepository pageRepository;
    
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private static final String STICKER_DIR = "src/main/resources/static/stickers/";

    public List<ScrapbookPage> getAllPages() {
        return pageRepository.findAll();
    }

    public ScrapbookPage getPageById(Long id) {
        return pageRepository.findById(id).orElse(null);
    }

    public ScrapbookPage createPage(String title, String description) {
        ScrapbookPage page = new ScrapbookPage();
        page.setTitle(title);
        page.setDescription(description);
        return pageRepository.save(page);
    }

    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName));
            return "/uploads/" + fileName;
        }
        return null;
    }

    public ScrapbookPage updatePage(Long id, ScrapbookPage updatedPage) {
        ScrapbookPage page = pageRepository.findById(id).orElse(null);
        if (page != null) {
            page.setTitle(updatedPage.getTitle());
            page.setDescription(updatedPage.getDescription());
            page.setImagePaths(updatedPage.getImagePaths());
            page.setStickerPaths(updatedPage.getStickerPaths());
            page.setImagePositions(updatedPage.getImagePositions());
            page.setStickerPositions(updatedPage.getStickerPositions());
            return pageRepository.save(page);
        }
        return null;
    }
}