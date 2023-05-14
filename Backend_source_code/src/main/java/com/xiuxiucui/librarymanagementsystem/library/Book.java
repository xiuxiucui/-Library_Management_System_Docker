package com.xiuxiucui.librarymanagementsystem.library;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    private  String ISBN13;
    private  String title;

    private int availability;

    private int total;

    public Book(String ISBN13, String title, int availability, int total) {
        this.ISBN13 = ISBN13;
        this.title = title;
        this.availability = availability;
        this.total = total;
    }
    protected Book(){


    }

    public String getISBN13() {
        return ISBN13;
    }

    public void setISBN13(String ISBN13) {
        this.ISBN13 = ISBN13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "book{" +
                "ISBN13='" + ISBN13 + '\'' +
                ", title='" + title + '\'' +
                ", availability=" + availability +
                ", total=" + total +
                '}';
    }
}
