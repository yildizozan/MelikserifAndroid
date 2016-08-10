package com.yildizozan.yurtbasi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ozan on 8/5/2016.
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
    private static String NAME = "name";
    private static String SURNAME = "surname";
    private static String REGISTER = "register";
    private static String PASSWORD = "password";
    private static String POSITION = "position";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY,"
                + PHONENUMBER + " TEXT,"
                + NAME + " TEXT,"
                + SURNAME + " TEXT,"
                + REGISTER + " TEXT,"
                + PASSWORD + " INT,"
                + POSITION + " INT" + ")";
        Log.e("CREATE DB: ", CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    public void addMember(Member member) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PHONENUMBER, member.getPhoneNumber());
        contentValues.put(NAME, member.getName());
        contentValues.put(SURNAME, member.getSurname());
        contentValues.put(REGISTER, member.getRegister());
        contentValues.put(PASSWORD, member.getPassword());
        contentValues.put(POSITION, member.getPosition());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public boolean updatePassword(Member member) {
        if (equals(member.getPhoneNumber() == "Required"))
            return false;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, member.getPassword());

        db.update(TABLE_NAME, contentValues, "phoneNumber = " + member.getPhoneNumber(), null);
        db.close();

        Log.e("UPDATE PASS", member.getPhoneNumber() + "-" +
                member.getName() + "-" +
                member.getSurname() + "-" +
                member.getRegister() + "-" +
                member.getPassword() + "-" +
                member.getPosition()
        );

        return true;
    }

    public Member getMember() {
        Member memberInfo = new Member();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            memberInfo.setPhoneNumber(cursor.getString(1));
            memberInfo.setName(cursor.getString(2));
            memberInfo.setSurname(cursor.getString(3));
            memberInfo.setRegister(cursor.getString(4));
            memberInfo.setPassword(cursor.getInt(5));
            memberInfo.setPosition(cursor.getInt(6));
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
