package com.example.acn4av_jeronimo_lago.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.acn4av_jeronimo_lago.db.DbHelper;
import com.example.acn4av_jeronimo_lago.entities.User;

public class UserController {

    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public UserController(Context context) {
        dbHelper = new DbHelper(context);
    }
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Boolean addUser(User user){
        ContentValues values = new ContentValues();

        values.put("name", user.getName());
        values.put("last_name", user.getLastName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("admin", user.getAdmin());
        long insertedId = database.insert("User",null, values);

        return insertedId > 0;
    }
    public boolean loginUser(String email, String password) {
        String[] columns = {"email", "password"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = database.query("User", columns, selection, selectionArgs, null, null, null);
        boolean userExists = cursor.moveToFirst();
        cursor.close();
        return userExists;
    }

}
