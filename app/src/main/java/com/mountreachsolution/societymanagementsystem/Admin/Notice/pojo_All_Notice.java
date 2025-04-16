package com.mountreachsolution.societymanagementsystem.Admin.Notice;

public class pojo_All_Notice {

    String id,date,time,title,description;

    public pojo_All_Notice(String id, String date, String time, String title, String description) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

