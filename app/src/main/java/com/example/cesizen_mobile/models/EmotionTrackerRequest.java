package com.example.cesizen_mobile.models;

import java.util.List;

public class EmotionTrackerRequest {
    private String name;
    private String description;
    private List<Integer> secondaryEmotions;

    public EmotionTrackerRequest(String name, String description, List<Integer> secondaryEmotions) {
        this.name = name;
        this.description = description;
        this.secondaryEmotions = secondaryEmotions;
    }
}
