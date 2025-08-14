package com.example.pastelpages.controller;
import com.example.pastelpages.model.ScrapbookPage;
import com.example.pastelpages.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    private PageService pageService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pages", pageService.getAllPages());
        return "index";
    }

    @GetMapping("/page/{id}")
    public String viewPage(@PathVariable Long id, Model model) {
        ScrapbookPage page = pageService.getPageById(id);
        model.addAttribute("page", page);
        model.addAttribute("stickers", List.of(
            "/stickers/bun.jpg", "/stickers/bun.jpg", 
            "/stickers/cute.jpg", "/stickers/bunny.jpg"
        ));
        return "page";
    }

    @PostMapping("/create")
    public String createPage(@RequestParam String title, 
                           @RequestParam String description,
                           RedirectAttributes redirectAttributes) {
        ScrapbookPage page = pageService.createPage(title, description);
        redirectAttributes.addFlashAttribute("message", "Page created successfully!");
        return "redirect:/page/" + page.getId();
    }

    @PostMapping("/page/{id}/add-image")
    public String addImage(@PathVariable Long id,
                         @RequestParam("image") MultipartFile file,
                         RedirectAttributes redirectAttributes) throws IOException {
        String imagePath = pageService.saveImage(file);
        if (imagePath != null) {
            ScrapbookPage page = pageService.getPageById(id);
            page.getImagePaths().add(imagePath);
            page.getImagePositions().add("100,100"); // Default position
            pageService.updatePage(id, page);
            redirectAttributes.addFlashAttribute("message", "Image added successfully!");
        }
        return "redirect:/page/" + id;
    }

    @PostMapping("/page/{id}/add-sticker")
    public String addSticker(@PathVariable Long id,
                           @RequestParam String stickerPath,
                           RedirectAttributes redirectAttributes) {
        ScrapbookPage page = pageService.getPageById(id);
        page.getStickerPaths().add(stickerPath);
        page.getStickerPositions().add("200,200"); // Default position
        pageService.updatePage(id, page);
        redirectAttributes.addFlashAttribute("message", "Sticker added successfully!");
        return "redirect:/page/" + id;
    }

    @PostMapping("/page/{id}/save-positions")
    @ResponseBody
    public String savePositions(@PathVariable Long id,
                              @RequestParam List<String> imagePositions,
                              @RequestParam List<String> stickerPositions) {
        ScrapbookPage page = pageService.getPageById(id);
        page.setImagePositions(imagePositions);
        page.setStickerPositions(stickerPositions);
        pageService.updatePage(id, page);
        return "Positions saved successfully!";
    }
}