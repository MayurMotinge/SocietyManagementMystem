package com.mountreachsolution.societymanagementsystem.Admin.Maintenance;

public class pojo_Maintenance_Officer {

    String id,name,mobileno,email_id,username,password;

    public pojo_Maintenance_Officer(String id, String name, String mobileno, String email_id, String username, String password) {
        this.id = id;
        this.name = name;
        this.mobileno = mobileno;
        this.email_id = email_id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
