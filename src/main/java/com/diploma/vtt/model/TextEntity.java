package com.diploma.vtt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
public class TextEntity {

    @Id
    private Integer id;

    public TextEntity() {

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

    public TextEntity(Integer id, String body, String title, String author) {
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
