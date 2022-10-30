package com.example.i_school;

public class firebaseNotetmodel {
    private String title;
    private String description;

    public firebaseNotetmodel() {

    }

    public firebaseNotetmodel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
