package com.example.acn4av_jeronimo_lago.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.acn4av_jeronimo_lago.db.DbHelper;
import com.example.acn4av_jeronimo_lago.entities.Car;

public class CarController {
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public CarController(Context context) {
        dbHelper = new DbHelper(context);
    }
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    public boolean addCarToConcessionary(Car car, int concessionaryId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues carValues = new ContentValues();
        carValues.put("model", car.getModel());
        carValues.put("brand", car.getBrand());
        carValues.put("price", car.getPrice());
        carValues.put("year", car.getYear());
        carValues.put("color", car.getColor());

        long carId = database.insert("Car", null, carValues);

        if (carId == -1) {
            return false; // Error al insertar el auto en la tabla "Car"
        }

        ContentValues carPerConcessionaryValues = new ContentValues();
        carPerConcessionaryValues.put("id_car", carId);
        carPerConcessionaryValues.put("id_concessionary", concessionaryId);

        long carPerConcessionaryId = database.insert("Car_Per_Concessionary", null, carPerConcessionaryValues);

        return carPerConcessionaryId != -1;
    }

    public boolean deleteCar(Integer id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String[] whereArgs = {String.valueOf(id)};

        // Eliminar el registro de la tabla "car"
        int deleted = database.delete("car", "id=?", whereArgs);

        // Eliminar los registros relacionados en la tabla "car_per_concessionary"
        int delte = database.delete("car_per_concessionary", "id_car=?", whereArgs);
        return delte != -1 && deleted != -1;
    }
}