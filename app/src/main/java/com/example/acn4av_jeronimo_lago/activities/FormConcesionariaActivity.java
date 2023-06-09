package com.example.acn4av_jeronimo_lago.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.acn4av_jeronimo_lago.R;
import com.example.acn4av_jeronimo_lago.controller.ConcessionaryController;
import com.example.acn4av_jeronimo_lago.entities.Concessionary;
import com.example.acn4av_jeronimo_lago.entities.User;

import java.util.Arrays;
import java.util.List;

public class FormConcesionariaActivity extends AppCompatActivity {

    private EditText etConcessionaryName, etConcessionaryCity, etConcessionaryAddress, etConcessionaryNumber;
    private Button btnConcessionaryFormSubmit;
    private ConcessionaryController concessionaryController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_concesionaria);
        etConcessionaryName = findViewById(R.id.etNombreConcesionaria);
        etConcessionaryAddress = findViewById(R.id.etCalleConsecionaria);
        etConcessionaryCity = findViewById(R.id.etCiudadConcesionaria);
        etConcessionaryNumber = findViewById(R.id.etNumeroConcesionaria);
        btnConcessionaryFormSubmit = findViewById(R.id.btnSubmitFormConcesionaria);
        btnConcessionaryFormSubmit.setOnClickListener( view -> {
            Concessionary concessionary = new Concessionary();
            concessionary.setName(etConcessionaryName.getText().toString());
            concessionary.setCity(etConcessionaryCity.getText().toString());
            concessionary.setAddres(etConcessionaryAddress.getText().toString());
            concessionary.setNumber(etConcessionaryNumber.getText().toString());
            if(validateInputFields(concessionary)){
                concessionaryController = new ConcessionaryController(getApplicationContext());
                concessionaryController.open();
                if( concessionaryController.addConcessionary(concessionary)){
                    Toast.makeText(getApplicationContext(), "Concesionaria agregada exitosamente!", Toast.LENGTH_SHORT).show();
                    concessionaryController.close();
                    Intent i = new Intent(FormConcesionariaActivity.this, ConcesionariasActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
                Toast.makeText(getApplicationContext(),"Error al agregar una concesionaria. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                concessionaryController.close();
                return;
            }
            Toast.makeText(getApplicationContext(),"Los valores ingresados no son validos.", Toast.LENGTH_SHORT).show();

        });


    }
    public Boolean validateInputFields(Concessionary concessionary) {
        List<String> listOfInputValues= Arrays.asList(new String[]{concessionary.getName(), concessionary.getCity() , concessionary.getAddres(), concessionary.getNumber()});
        if(listOfInputValues.stream().allMatch(value -> value.length() > 1)){
            return true;
        }
        return false;
    }
}