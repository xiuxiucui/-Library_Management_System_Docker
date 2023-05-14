package com.xiuxiucui.librarymanagementsystem.library;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person {

    @Id
    private String username;

    private String sha224_password;

    private String user_group;

    private String first_name;

    private String last_name;

    private int contact_no;

    protected Person(){

    }

    public Person(String username, String sha224_password, String user_group, String first_name, String last_name, int contact_no) {
        this.username = username;
        this.sha224_password = sha224_password;
        this.user_group = user_group;
        this.first_name = first_name;
        this.last_name = last_name;
        this.contact_no = contact_no;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSha224_password() {
        return sha224_password;
    }

    public void setSha224_password(String sha224_password) {
        this.sha224_password = sha224_password;
    }

    public String getUser_group() {
        return user_group;
    }

    public void setUser_group(String user_group) {
        this.user_group = user_group;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getContact_no() {
        return contact_no;
    }

    public void setContact_no(int contact_no) {
        this.contact_no = contact_no;
    }


}
