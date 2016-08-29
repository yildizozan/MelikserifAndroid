package com.yildizozan.yurtbasi;

import java.io.Serializable;

/**
 * Created by Ozan on 8/19/2016.
 */

public class News implements Serializable {

    private String title;
    private String content;
    private String author;
    private String date;

    public News(String title, String content, String author, String date) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
    }

    public News(News news) {
        this.title = news.getTitle();
        this.content =news.getContent();
        this.author = news.getAuthor();
        this.date = getDate();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
