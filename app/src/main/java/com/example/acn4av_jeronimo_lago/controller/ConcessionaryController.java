package com.example.acn4av_jeronimo_lago.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.acn4av_jeronimo_lago.db.DbHelper;
import com.example.acn4av_jeronimo_lago.entities.Car;
import com.example.acn4av_jeronimo_lago.entities.Concessionary;
import com.example.acn4av_jeronimo_lago.entities.User;

import java.util.ArrayList;
import java.util.List;

 public  class ConcessionaryController {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;


    public ConcessionaryController(Context context) {

        dbHelper = new DbHelper(context);
    }
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Concessionary> getAllConcessionaries() {
        ArrayList<Concessionary> concessionaries = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] columns = {"id", "name", "city", "address", "number"};
        Cursor cursor = database.query("Concessionary", columns, null, null, null, null, null);
        int columnIndexId = cursor.getColumnIndex("id");
        int columnIndexName = cursor.getColumnIndex("name");
        int columnIndexCity = cursor.getColumnIndex("city");
        int columnIndexAddress = cursor.getColumnIndex("address");
        int columnIndexNumber = cursor.getColumnIndex("number");
        int columnIndexAdmin = cursor.getColumnIndex("admin");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(columnIndexId);
            String name = cursor.getString(columnIndexName);
            String city = cursor.getString(columnIndexCity);
            String address = cursor.getString(columnIndexAddress);
            String number = cursor.getString(columnIndexNumber);
            Concessionary concessionary = new Concessionary();
            concessionary.setName(name);
            concessionary.setId(id);
            concessionary.setAddres(address);
            concessionary.setNumber(number);
            concessionary.setCity(city);
            concessionaries.add(concessionary);
        }

        cursor.close();
        return concessionaries;
    }

    public Boolean addConcessionary(Concessionary concessionary){
        ContentValues values = new ContentValues();

        values.put("name", concessionary.getName());
        values.put("city", concessionary.getCity());
        values.put("address", concessionary.getAddres());
        values.put("number", concessionary.getNumber());
        long insertedId = database.insert("Concessionary",null, values);

        return insertedId > 0;
    }
    public Boolean deleteConcessionary(Integer id){
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            String selection = "id=?";
            String[] selectionArgs = {String.valueOf(id)};

            int rowsDeleted = database.delete("Concessionary", selection, selectionArgs);

            return rowsDeleted > 0;

    }
     public Concessionary getConcessionaryById(int id) {
         SQLiteDatabase database = dbHelper.getReadableDatabase();

         String[] columns = {"id", "name", "city", "address", "number"};
         String selection = "id=?";
         String[] selectionArgs = {String.valueOf(id)};

         ArrayList<Concessionary> concessionaries = new ArrayList<>();

         Cursor cursor = database.query("Concessionary", columns, selection, selectionArgs, null, null, null);

         int columnIndexId = cursor.getColumnIndex("id");
         int columnIndexName = cursor.getColumnIndex("name");
         int columnIndexCity = cursor.getColumnIndex("city");
         int columnIndexAddress = cursor.getColumnIndex("address");
         int columnIndexNumber = cursor.getColumnIndex("number");
         Concessionary concessionary = null;

         while (cursor.moveToNext()) {
             int idFound = cursor.getInt(columnIndexId);
             String name = cursor.getString(columnIndexName);
             String city = cursor.getString(columnIndexCity);
             String address = cursor.getString(columnIndexAddress);
             String number = cursor.getString(columnIndexNumber);
             concessionary = new Concessionary();
             concessionary.setName(name);
             concessionary.setId(idFound);
             concessionary.setAddres(address);
             concessionary.setNumber(number);
             concessionary.setCity(city);
             concessionaries.add(concessionary);
         }

         cursor.close();


         if (concessionary != null) {
             List<Car> cars = getCarsByConcessionaryId(id);
             concessionary.setCars(cars);
         }

         return concessionary;
     }
     private List<Car> getCarsByConcessionaryId(int concessionaryId) {
         SQLiteDatabase database = dbHelper.getReadableDatabase();

         String[] columns = {"Car.id", "model", "brand", "price", "year", "color"};
         String selection = "Car_Per_Concessionary.id_concessionary=?";
         String[] selectionArgs = {String.valueOf(concessionaryId)};

         String table = "Car_Per_Concessionary INNER JOIN Car ON Car_Per_Concessionary.id_car = Car.id";

         Cursor cursor = database.query(table, columns, selection, selectionArgs, null, null, null);

         List<Car> cars = new ArrayList<>();
         int columnIndexId = cursor.getColumnIndex("Car.id");
         int columnIndexModel = cursor.getColumnIndex("model");
         int columnIndexBrand = cursor.getColumnIndex("brand");
         int columnIndexPrice = cursor.getColumnIndex("price");
         int columnIndexYear = cursor.getColumnIndex("year");
         int columnIndexColor = cursor.getColumnIndex("color");

         while (cursor.moveToNext()) {
             int id = cursor.getInt(columnIndexId);
             String model = cursor.getString(columnIndexModel);
             String brand = cursor.getString(columnIndexBrand);
             double price = cursor.getDouble(columnIndexPrice);
             int year = cursor.getInt(columnIndexYear);
             String color = cursor.getString(columnIndexColor);
             Car car = new Car();
             car.setId(id);
             car.setModel(model);
             car.setBrand(brand);
                car.setPrice(price);
                car.setYear(year);
                car.setColor(color);
             cars.add(car);
         }

         cursor.close();
         return cars;
     }

    public Boolean updateConcessionary(Concessionary concessionary){
        ContentValues values = new ContentValues();

        values.put("name", concessionary.getName());
        values.put("city", concessionary.getCity());
        values.put("address", concessionary.getAddres());
        values.put("number", concessionary.getNumber());
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(concessionary.getId())};
        int rowsUpdated = database.update("Concessionary", values, selection, selectionArgs);

        return rowsUpdated > 0;
    }
 }
