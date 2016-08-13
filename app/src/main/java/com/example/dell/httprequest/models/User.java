package com.example.dell.httprequest.models;

/**
 * Created by DELL on 8/11/2016.
 */
public class User {

    public User(String id, String name, String sex, String password, String email, String phone, int
                   status, String date, String photo){
        setId(id);
        setName(name);
        setSex(sex);
        setPassword(password);
        setEmail(email);
        setPhone(phone);
        setStatus(status);
        setDate(date);
        setPhoto(photo);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    private String Id;
    private String Name;
    private String Sex;
    private String Password;
    private String Email;
    private String Phone;
    private int Status ;
    private String Date;
    private String Photo;

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
