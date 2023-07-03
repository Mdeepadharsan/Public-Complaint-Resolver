package com.example.publiccomplaintresolver;

public class Complaint_Data {
    String complaint_id;
    String complaint_description;
    String complaint_location;

    public Complaint_Data(String complaint_id, String complaint_description, String complaint_location) {
        this.complaint_id = complaint_id;
        this.complaint_description = complaint_description;
        this.complaint_location = complaint_location;
    }

    public String getComplaint_description() {
        return complaint_description;
    }

    public String getComplaint_id() {
        return complaint_id;
    }

    public String getComplaint_location() {
        return complaint_location;
    }

}
