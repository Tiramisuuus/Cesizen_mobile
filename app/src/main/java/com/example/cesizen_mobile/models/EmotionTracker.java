package com.example.cesizen_mobile.models;

import java.util.List;

public class EmotionTracker {
    private int id;
    private String name;
    private String description;
    private String createdAt;
    private List<String> emotions; // noms des émotions

    // Getters & Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCreatedAt() { return createdAt; }
    public List<String> getEmotions() { return emotions; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public void setEmotions(List<String> emotions) { this.emotions = emotions; }
}
