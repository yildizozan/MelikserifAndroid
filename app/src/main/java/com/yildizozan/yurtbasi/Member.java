package com.yildizozan.yurtbasi;

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
        setPhoneNumber(null);
        setName(null);
        setSurname(null);
        setRegister(null);
        setPassword(0);
        setPosition(0);
    }

    Member(Member member) {
        this.setPhoneNumber(member.getPhoneNumber());
        this.setName(member.getName());
        this.setSurname(member.getSurname());
        this.setRegister(member.getRegister());
        this.setPassword(member.getPassword());
        this.setPosition(member.getPosition());
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


    public String getMemberResult() {
        return  getPhoneNumber() + "\n" +
                getName() + "\n" +
                getSurname() + "\n" +
                getRegister() + "\n" +
                getPassword() + "\n" +
                getPosition();
    }
}
