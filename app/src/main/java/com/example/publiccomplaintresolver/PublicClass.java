package com.example.publiccomplaintresolver;

public class PublicClass {
    String name;
    String email_id;
    String password;
    String confirm_password;
    String mobile_no;
    String age;
    String street_name;
    String area_name;
    String city_name;
    String pincode;
    String occupation;
    String aadhaar_no;
    String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAadhaar_no() {
        return aadhaar_no;
    }

    public void setAadhaar_no(String aadhaar_no) {
        this.aadhaar_no = aadhaar_no;
    }

    public PublicClass(String name, String email_id, String password, String confirm_password, String mobile_no, String age, String street_name, String area_name, String city_name, String pincode, String occupation, String aadhaar_no,String gender) {
        this.name = name;
        this.email_id = email_id;
        this.password = password;
        this.confirm_password = confirm_password;
        this.mobile_no = mobile_no;
        this.age = age;
        this.street_name = street_name;
        this.area_name = area_name;
        this.city_name = city_name;
        this.pincode = pincode;
        this.occupation = occupation;
        this.aadhaar_no = aadhaar_no;
    }
}
