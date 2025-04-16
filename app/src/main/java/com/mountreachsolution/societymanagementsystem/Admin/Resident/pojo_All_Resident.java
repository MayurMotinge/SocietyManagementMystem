package com.mountreachsolution.societymanagementsystem.Admin.Resident;

public class pojo_All_Resident {

    String name,mobile_no,email_is,wing,flat_no,username;

    public pojo_All_Resident(String name, String mobile_no, String email_is, String wing, String flat_no, String username) {
        this.name = name;
        this.mobile_no = mobile_no;
        this.email_is = email_is;
        this.wing = wing;
        this.flat_no = flat_no;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_is() {
        return email_is;
    }

    public void setEmail_is(String email_is) {
        this.email_is = email_is;
    }

    public String getWing() {
        return wing;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }

    public String getFlat_no() {
        return flat_no;
    }

    public void setFlat_no(String flat_no) {
        this.flat_no = flat_no;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
