package com.example.acn4av_jeronimo_lago.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.acn4av_jeronimo_lago.R;
import com.example.acn4av_jeronimo_lago.controller.CarController;
import com.example.acn4av_jeronimo_lago.entities.Car;
import com.example.acn4av_jeronimo_lago.entities.Concessionary;

import java.util.Arrays;
import java.util.List;

public class FormAutoActivity extends AppCompatActivity {
    Spinner spinner ;
    EditText txtColor, txtModel, txtYear, txtPrice;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_auto);
        spinner = findViewById(R.id.spinner);
        txtColor = findViewById(R.id.etCarColor);
        txtModel = findViewById(R.id.etCarModel);
        txtPrice = findViewById(R.id.etCarPrice);
        txtYear = findViewById(R.id.etCarYear);
        btnSubmit = findViewById(R.id.btnSubmitFormAuto);
        List<String> datos = Arrays.asList("Audi", "BMW", "Chevrolet", "Fiat", "Ford", "Honda", "Hyundai", "Kia", "Mercedes Benz", "Nissan", "Peugeot", "Renault", "Toyota", "Volkswagen");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btnSubmit.setOnClickListener(view -> {
            Car car = new Car();
            car.setBrand(spinner.getSelectedItem().toString());
            car.setColor(txtColor.getText().toString());
            car.setModel(txtModel.getText().toString());
            car.setPrice(Double.parseDouble(txtPrice.getText().toString()));
            car.setYear(Integer.parseInt(txtYear.getText().toString()));
            if(validateInputFields(car)) {
                CarController controller = new CarController(getApplicationContext());
                controller.open();
                Intent i = getIntent();
                Integer id = i.getIntExtra("id", 0);

                if (id > 0) {
                    if (controller.addCarToConcessionary(car, id)) {
                        Intent intent = new Intent(FormAutoActivity.this, ConcesionariaDetailActivity.class);
                        intent.putExtra("ID", id);
                        controller.close();
                        Toast.makeText(getApplicationContext(), "Auto agregado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(intent);
                    }
                    ;
                } else {
                    Toast.makeText(getApplicationContext(), "Error al agregar auto", Toast.LENGTH_SHORT).show();
                }
                controller.close();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Los datos ingresados no son validos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public Boolean validateInputFields(Car car) {
        List<String> listOfInputValues= Arrays.asList(new String[]{car.getModel(), car.getBrand() , car.getColor(), car.getPrice().toString(), car.getYear().toString()});
        if(listOfInputValues.stream().allMatch(value -> value.length() > 1)){
            return true;
        }
        return false;
    }
}