package com.yildizozan.yurtbasi;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Ozan on 8/6/2016.
 */

public class Member {
    private String phoneNumber;
    private String name;
    private String surname;
    private String register;
    private int password;
    private int position;

    Member() {
        phoneNumber = null;
        name = null;
        surname = null;
        register = null;
        password = 0;
        position = 0;
    }

    Member(JSONObject jsonObject) {
        try {
            phoneNumber = jsonObject.getString("phoneNumber");
            name = jsonObject.getString("name");
            surname = jsonObject.getString("surname");
            register = jsonObject.getString("register");
            password = jsonObject.getInt("password");
            position = jsonObject.getInt("position");
        } catch (Exception e) {
            Log.e("MEMBER jsonPARSE", e.getMessage());
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
