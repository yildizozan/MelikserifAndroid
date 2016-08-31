package com.yildizozan.melikserif;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class JSONParser {
    private String jsonString;
    private Member member;
    private ArrayList<News> newses = new ArrayList<News>();

    JSONParser(String jsonString) {
        this.jsonString = jsonString.substring(4);
    }

    public boolean setMember() {
        try {
            JSONObject jsonRootObject = new JSONObject(this.jsonString);
            JSONObject jsonObject = jsonRootObject.getJSONObject("person");

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

    /*
    *** Burada oluşturulan ArrayList'e gelen haberleri depolayacak
    *** Ardından da get ile ArrayList döndürecek.
     */
    public boolean setNews() {
        try {
            JSONArray jsonArray = new JSONArray(this.jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                newses.add(new News(jsonObject));

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONParser", e.getMessage());
            return false;
        }

        return true;
    }

    public ArrayList<News> getNews() {
        return newses;
    }

}
