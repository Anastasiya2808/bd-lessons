package ru.ifmo.lessons.base;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class Article {
    private String title;
    private String text;
    private Date created_on;
    private int author_id;

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

}
