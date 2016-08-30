package com.yildizozan.yurtbasi;

import android.util.Log;

import org.json.JSONObject;

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

    // Eğer sınıfıa jsonObject gönderilirse onu değişkenlere parçalayacak.
    public News(JSONObject jsonObject) {

        try {
            this.title = jsonObject.getString("title");
            this.content = jsonObject.getString("content");
            this.author = jsonObject.getString("author");
            this.date = jsonObject.getString("date");
        } catch (Exception e) {
            Log.e("News jsonParse", e.getMessage());
        }

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
