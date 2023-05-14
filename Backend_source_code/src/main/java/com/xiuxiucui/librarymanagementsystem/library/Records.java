package com.xiuxiucui.librarymanagementsystem.library;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class Records {

    @Id
    @GeneratedValue
    private int id;
    private String type;

    @OneToOne(fetch = FetchType.LAZY)
    private Book book;

    @OneToOne(fetch = FetchType.LAZY)
    private Person person;
    private  int quantity;

    private LocalDateTime time;

    private String return_status;

    protected Records(){


    }


    public Records(int id, String type, Book book, Person person, int quantity, LocalDateTime time, String return_status) {
        this.id = id;
        this.type = type;
        this.book = book;
        this.person = person;
        this.quantity = quantity;
        this.time = time;
        this.return_status = return_status;
    }

    public Records( String type, Book book, Person person, int quantity, LocalDateTime time, String return_status) {
        this.type = type;
        this.book = book;
        this.person = person;
        this.quantity = quantity;
        this.time = time;
        this.return_status = return_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getReturn_status() {
        return return_status;
    }

    public void setReturn_status(String return_status) {
        this.return_status = return_status;
    }
}
