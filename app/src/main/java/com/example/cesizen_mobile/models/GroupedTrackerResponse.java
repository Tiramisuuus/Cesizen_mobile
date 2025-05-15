package com.example.cesizen_mobile.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.List;

public class GroupedTrackerResponse {
    private int id;
    private String name;
    private String description;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("groupedEmotions")
    private Map<String, List<String>> groupedEmotions;

    public int getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getCreatedAt() { return createdAt; }

    public Map<String, List<String>> getGroupedEmotions() { return groupedEmotions; }
}
