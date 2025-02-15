package com.example.demo_spring.model;

import jakarta.persistence.*;

@Table(name = "USER_DEMO")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String classSchool;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String imgURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public User(int id, String name, String classSchool, String phone, String email, String imgURL, Company company) {
        this.id = id;
        this.name = name;
        this.classSchool = classSchool;
        this.phone = phone;
        this.email = email;
        this.imgURL = imgURL;
        this.company = company;
    }

    public User() {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

//    @Override
//    public String toString() {
//        return "User{name='" + name + "', email='" + email + "', classSchool='" + classSchool + "', phone='" + phone + "', imgURL='" + imgURL + "', company='" + (company != null ? company.getName() : "null") + "'}";
//    }
}
