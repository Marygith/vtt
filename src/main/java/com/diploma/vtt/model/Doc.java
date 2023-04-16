package com.diploma.vtt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Doc {

    @Id
    private Integer id;

    public Doc() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String body;

    private String title;

    private String author;

    public Doc(Integer id, String body, String title, String author) {
        this.id = id;
        this.body = body;
        this.title = title;
        this.author = author;
    }


    @Override
    public String toString() {
        return id +' '+ body +' '+ title +' '+ author;
    }

}
