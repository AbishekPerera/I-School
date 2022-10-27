package com.example.i_school;

public class firebaseRocketmodel {

    private Integer time;
    private String date;
    private String title;
    private String description;

    public firebaseRocketmodel(){//for fire base

    }

    public firebaseRocketmodel(Integer time, String date, String title, String description) {
        //for us
        this.time = time;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
