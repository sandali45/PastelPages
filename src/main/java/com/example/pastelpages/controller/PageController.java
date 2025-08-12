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

   
}