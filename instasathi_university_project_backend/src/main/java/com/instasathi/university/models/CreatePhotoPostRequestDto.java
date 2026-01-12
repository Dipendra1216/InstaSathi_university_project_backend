package com.instasathi.university.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreatePhotoPostRequestDto {
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 500)
    private String caption;

    @NotBlank
    private String location;

    private String tags;   // comma separated
    private String people; // comma separated

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getPeople() { return people; }
    public void setPeople(String people) { this.people = people; }
}
