package com.yildizozan.melikserif;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ozan Yıldız on 2016/8/5.
 */

public class Database extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "yurtbasiDB";

    // Database table name
    private static final String TABLE_NAME = "member";

    // Database columns
    private static String ID = "id";
    private static String PHONENUMBER = "phoneNumber";
    private static String PASSWORD = "password";
    private static String REGISTER = "register";
    private static String GENDER = "gender";
    private static String FIRSTNAME = "firstName";
    private static String MIDDLENAME = "middleName";
    private static String FAMILYNAME = "familyName";
    private static String BIRTHDATE = "birthDate";


    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY,"
                + PHONENUMBER + " TEXT,"
                + PASSWORD + " INT,"
                + REGISTER + " TEXT,"
                + GENDER + " TEXT,"
                + FIRSTNAME + " TEXT,"
                + MIDDLENAME + " TEXT,"
                + FAMILYNAME + " TEXT,"
                + BIRTHDATE + " TEXT" + ")";
        Log.e("CREATE DB: ", CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    public void addMember(Member member) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PHONENUMBER, member.getPhoneNumber());
        contentValues.put(PASSWORD, member.getPassword());
        contentValues.put(REGISTER, member.getRegister());
        contentValues.put(GENDER, member.getGender());
        contentValues.put(FIRSTNAME, member.getFirstName());
        contentValues.put(MIDDLENAME, member.getMiddleName());
        contentValues.put(FAMILYNAME, member.getFamilyName());
        contentValues.put(BIRTHDATE, member.getRegister());


        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public Member getMember() {
        Member memberInfo = new Member();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            memberInfo.setPhoneNumber(cursor.getString(1));
            memberInfo.setPassword(cursor.getString(2));
            memberInfo.setRegister(cursor.getString(3));
            memberInfo.setGender(cursor.getString(4));
            memberInfo.setFirstName(cursor.getString(5));
            memberInfo.setMiddleName(cursor.getString(6));
            memberInfo.setFamilyName(cursor.getString(7));
            memberInfo.setBirthDate(cursor.getString(8));
        }
        cursor.close();
        db.close();

        return memberInfo;
    }

    // Tabloda kaç kişi kayıtlı olduğunu döndürür
    public int getRowCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int rowCount = cursor.getCount();
        cursor.close();

        return rowCount;
    }

    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

    }
}
