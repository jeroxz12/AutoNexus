package com.example.acn4av_jeronimo_lago.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "autonexus.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableUser = "CREATE TABLE User (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    name VARCHAR(50)," +
                "    last_name VARCHAR(90)," +
                "    email VARCHAR(255)," +
                "    password VARCHAR(255)," +
                "    admin INTEGER);";
        String createTableCar = "CREATE TABLE Car (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    model VARCHAR(100)," +
                "    brand VARCHAR(100)," +
                "    price DECIMAL(18, 2)," +
                "    year INTEGER," +
                "    color VARCHAR(100));";
        String createTableConcessionary = "CREATE TABLE Concessionary (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    name VARCHAR(50)," +
                "    city VARCHAR(60)," +
                "    address VARCHAR(255)," +
                "    number VARCHAR(30));";
        String createTableCarPerConcessionary = "CREATE TABLE Car_Per_Concessionary (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    id_car INTEGER," +
                "    id_concessionary INTEGER," +
                "    FOREIGN KEY (id_car) REFERENCES Car(id)," +
                "    FOREIGN KEY (id_concessionary) REFERENCES Concessionary(id));";
        sqLiteDatabase.execSQL(createTableUser);
        sqLiteDatabase.execSQL(createTableCar);
        sqLiteDatabase.execSQL(createTableConcessionary);
        sqLiteDatabase.execSQL(createTableCarPerConcessionary);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
