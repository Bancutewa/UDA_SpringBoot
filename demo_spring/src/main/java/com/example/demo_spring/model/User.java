package com.example.demo_spring.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class User {
    private int id;
    private String name;
    private String classSchool;
    private String phone;
    private String email;
    private String imgURL;

    public User(int id, String name, String classSchool, String phone, String email, String imgURL) {
        this.id = id;
        this.name = name;
        this.classSchool = classSchool;
        this.phone = phone;
        this.email = email;
        this.imgURL = imgURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassSchool() {
        return classSchool;
    }

    public void setClassSchool(String classSchool) {
        this.classSchool = classSchool;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getImgURL() {
        return imgURL;
    }
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
