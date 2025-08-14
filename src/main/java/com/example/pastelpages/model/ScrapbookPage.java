package com.example.pastelpages.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ScrapbookPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    
    @ElementCollection
    private List<String> imagePaths = new ArrayList<>();
    
    @ElementCollection
    private List<String> stickerPaths = new ArrayList<>();
    
    // Positions of elements (x,y coordinates)
    @ElementCollection
    private List<String> imagePositions = new ArrayList<>();
    
    @ElementCollection
    private List<String> stickerPositions = new ArrayList<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getImagePaths() { return imagePaths; }
    public void setImagePaths(List<String> imagePaths) { this.imagePaths = imagePaths; }
    public List<String> getStickerPaths() { return stickerPaths; }
    public void setStickerPaths(List<String> stickerPaths) { this.stickerPaths = stickerPaths; }
    public List<String> getImagePositions() { return imagePositions; }
    public void setImagePositions(List<String> imagePositions) { this.imagePositions = imagePositions; }
    public List<String> getStickerPositions() { return stickerPositions; }
    public void setStickerPositions(List<String> stickerPositions) { this.stickerPositions = stickerPositions; }
}