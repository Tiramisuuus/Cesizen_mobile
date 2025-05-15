package com.example.cesizen_mobile.models;

import java.util.List;

public class GroupedEmotionsResponse {
    private String primary;
    private List<SecondaryEmotion> emotions;

    public String getPrimary() {
        return primary;
    }

    public List<SecondaryEmotion> getEmotions() {
        return emotions;
    }
}

