package com.mountreachsolution.societymanagementsystem.Residence;

public class pojo_All_Maintenance {

    String id,date,title,cost,description;

    public pojo_All_Maintenance(String id, String date, String title, String cost, String description) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.cost = cost;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
