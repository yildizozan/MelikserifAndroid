package com.yildizozan.melikserif;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class Member implements Serializable {
    private String phoneNumber;
    private String name;
    private String surname;
    private String register;
    private int password;
    private int position;

    Member() {
        this.phoneNumber = null;
        this.name = null;
        this.surname = null;
        this.register = null;
        this.password = 0;
        this.position = 0;
    }

    Member(Member member) {
        this.phoneNumber = member.getPhoneNumber();
        this.name = member.getName();
        this.surname = member.getSurname();
        this.register = member.getRegister();
        this.password = member.getPassword();
        this.position = member.getPosition();
    }

    Member(JSONObject jsonObject) {

        try {
            setPhoneNumber(jsonObject.getString("phoneNumber"));
            setName(jsonObject.getString("name"));
            setSurname(jsonObject.getString("surname"));
            setRegister(jsonObject.getString("register"));
            setPassword(jsonObject.getInt("password"));
            setPosition(jsonObject.getInt("position"));
        } catch (Exception e) {
            Log.e("Member jsonPARSE", e.getMessage());
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

    public String getMemberResult() {
        return  getPhoneNumber() + "\n" +
                getName() + "\n" +
                getSurname() + "\n" +
                getRegister() + "\n" +
                getPassword() + "\n" +
                getPosition();
    }
}
