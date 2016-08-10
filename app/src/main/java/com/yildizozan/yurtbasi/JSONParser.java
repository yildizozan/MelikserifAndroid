package com.yildizozan.yurtbasi;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class JSONParser {
    private String mJSONString;
    private Member mMember;

    JSONParser(String jsonString) {
        this.mJSONString = jsonString.substring(4);
    }

    public boolean setMember() {
        try {
            JSONObject jsonRootObject = new JSONObject(mJSONString);
            JSONObject jsonObject = jsonRootObject.getJSONObject("user");

            // If member not found
            if (jsonObject.getBoolean("error") == true)
                return false;

            mMember = new Member(jsonObject);

        } catch (Exception e) {
            Log.e("ERR JSON PARSER 567", e.getMessage());
            return false;
        }

        return true;
    }

    public Member getMember() {
        return mMember;
    }

    public String getMemberString() {
        return  getMember().getPhoneNumber() + "\n" +
                getMember().getName() + "\n" +
                getMember().getSurname() + "\n" +
                getMember().getRegister() + "\n" +
                getMember().getPassword() + "\n" +
                getMember().getPosition();
    }
}
