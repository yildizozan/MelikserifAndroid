package com.yildizozan.yurtbasi;

/**
 * Created by Ozan on 8/10/2016.
 */

public class Date {
    private int day;
    private int month;
    private int year;

    Date() {
        day = 0;
        month = 0;
        year = 0;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
