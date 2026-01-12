package com.instasathi.university.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PhotoPostResponseDto {
    public String id;
    public String title;
    public String caption;
    public String location;
    public String creatorName;
    public String creatorId;
    public String imageUrl;
    public Instant uploadDate;
    public long likes;
    public long views;
    public double rating;
    public List<String> tags = new ArrayList<>();
    public List<String> people = new ArrayList<>();
    public List<Object> comments = new ArrayList<>();
}
