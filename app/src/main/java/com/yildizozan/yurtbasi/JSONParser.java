package com.yildizozan.yurtbasi;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class JSONParser {
    private String jsonString;
    private Member member;
    private News news;

    JSONParser(String jsonString) {
        this.jsonString = jsonString.substring(4);
    }

    public boolean setMember() {
        try {
            JSONObject jsonRootObject = new JSONObject(this.jsonString);
            JSONObject jsonObject = jsonRootObject.getJSONObject("user");

            // If member not found
            if (jsonObject.getBoolean("error"))
                return false;

            member = new Member(jsonObject);

        } catch (Exception e) {
            Log.e("CTRL 9665", e.getMessage());
            return false;
        }

        return true;
    }

    public Member getMember() {
        return member;
    }

    public String getMemberString() {
        return  getMember().getPhoneNumber() + "\n" +
                getMember().getName() + "\n" +
                getMember().getSurname() + "\n" +
                getMember().getRegister() + "\n" +
                getMember().getPassword() + "\n" +
                getMember().getPosition();
    }

    /*
    *** Burada oluşturulan ArrayList'e gelen haberleri depolayacak
    *** Ardından da get ile ArrayList döndürecek.
     */
    public boolean setNews() {
        try {
            JSONObject jsonRootObject = new JSONObject(this.jsonString);
            JSONObject jsonObject = jsonRootObject.getJSONObject("news");

            this.news = new News(jsonObject);

        } catch (Exception e) {
            Log.e("CTRL 9666", e.getMessage());
            return false;
        }

        return true;
    }


    public News getNews() {
        return news;
    }

}
