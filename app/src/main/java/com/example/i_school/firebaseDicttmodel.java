package com.example.i_school;

public class firebaseDicttmodel {

    private String title;
    private String Description;

    firebaseDicttmodel(){

    }

    public firebaseDicttmodel(String title, String description) {
        this.title = title;
        Description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}