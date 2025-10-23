package com.example.nguyenthimyduyen_22721461_btth_tuan03.model;

import java.util.List;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String dob;
    private String email;
    private String mobile;
    private String gender;
    private String address;
    private String city;
    private String pincode;
    private String state;
    private String country;
    private List<String> hobbies;
    private String board10;
    private String percentage10;
    private String year10;

    private String board12;
    private String percentage12;
    private String year12;
    private String course;

    public Student() {
    }

    public Student(int id, String firstName, String lastName, String dob, String email, String mobile, String gender, String address, String city, String pincode, String state, String country, List<String> hobbies, String board10, String percentage10, String year10, String board12, String percentage12, String year12, String course) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
        this.country = country;
        this.hobbies = hobbies;
        this.board10 = board10;
        this.percentage10 = percentage10;
        this.year10 = year10;
        this.board12 = board12;
        this.percentage12 = percentage12;
        this.year12 = year12;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public String getBoard10() {
        return board10;
    }

    public void setBoard10(String board10) {
        this.board10 = board10;
    }

    public String getPercentage10() {
        return percentage10;
    }

    public void setPercentage10(String percentage10) {
        this.percentage10 = percentage10;
    }

    public String getYear10() {
        return year10;
    }

    public void setYear10(String year10) {
        this.year10 = year10;
    }

    public String getBoard12() {
        return board12;
    }

    public void setBoard12(String board12) {
        this.board12 = board12;
    }

    public String getPercentage12() {
        return percentage12;
    }

    public void setPercentage12(String percentage12) {
        this.percentage12 = percentage12;
    }

    public String getYear12() {
        return year12;
    }

    public void setYear12(String year12) {
        this.year12 = year12;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
