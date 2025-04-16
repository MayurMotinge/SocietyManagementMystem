package com.mountreachsolution.societymanagementsystem.Admin.Complaint_Feedback;

public class pojo_All_Complaint_Feedback {

    String id,name,wing,flat_no,type,complaint_feedback;

    public pojo_All_Complaint_Feedback(String id, String name, String wing, String flat_no, String type, String complaint_feedback) {
        this.id = id;
        this.name = name;
        this.wing = wing;
        this.flat_no = flat_no;
        this.type = type;
        this.complaint_feedback = complaint_feedback;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComplaint_feedback() {
        return complaint_feedback;
    }

    public void setComplaint_feedback(String complaint_feedback) {
        this.complaint_feedback = complaint_feedback;
    }
}
