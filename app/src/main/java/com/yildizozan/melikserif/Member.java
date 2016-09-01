package com.yildizozan.melikserif;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class Member implements Serializable {
    private String phoneNumber;
    private String password;
    private String register;
    private String gender;
    private String firstName;
    private String middleName;
    private String familyName;
    private String birthDate;

    Member() {
        this.phoneNumber = null;
        this.password = null;
        this.register = null;
        this.gender = null;
        this.firstName = null;
        this.middleName = null;
        this.familyName = null;
        this.birthDate = null;
    }

    Member(Member member) {
        this.phoneNumber = member.getPhoneNumber();
        this.password = member.getPassword();
        this.register = member.getRegister();
        this.gender = member.getGender();
        this.firstName = member.getFirstName();
        this.middleName = member.getMiddleName();
        this.familyName = member.getFamilyName();
        this.birthDate = member.getBirthDate();
    }

    Member(JSONObject jsonObject) {

        try {
            this.phoneNumber = jsonObject.getString("phoneNumber");
            this.password = jsonObject.getString("password");
            this.register = jsonObject.getString("register");
            this.gender = jsonObject.getString("gender");
            this.firstName = jsonObject.getString("firstName");
            this.middleName = jsonObject.getString("middleName");
            this.familyName = jsonObject.getString("familyName");
            this.birthDate = jsonObject.getString("birthDate");

        } catch (JSONException e) {
            Log.e("Member jsonPARSE", e.getMessage());
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
