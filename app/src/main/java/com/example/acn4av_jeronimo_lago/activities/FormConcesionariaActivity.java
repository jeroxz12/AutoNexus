package com.example.acn4av_jeronimo_lago.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
    private TextView txtFormTitle;
    private ConcessionaryController concessionaryController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_concesionaria);
        txtFormTitle = findViewById(R.id.txtFormTitle);
        btnConcessionaryFormSubmit = findViewById(R.id.btnSubmitFormConcesionaria);
        etConcessionaryName = findViewById(R.id.etNombreConcesionaria);
        etConcessionaryAddress = findViewById(R.id.etCalleConsecionaria);
        etConcessionaryCity = findViewById(R.id.etCiudadConcesionaria);
        etConcessionaryNumber = findViewById(R.id.etNumeroConcesionaria);
        Intent received = getIntent();
        Integer id = received.getIntExtra("id", 0);
        if( id > 0){
            txtFormTitle.setText("Editar Concesionaria");
            btnConcessionaryFormSubmit.setText("Editar");
            concessionaryController = new ConcessionaryController(getApplicationContext());
            concessionaryController.open();
            Concessionary concessionaryFound = concessionaryController.getConcessionaryById(id);
            etConcessionaryName.setText(concessionaryFound.getName());
            etConcessionaryCity.setText(concessionaryFound.getCity());
            etConcessionaryAddress.setText(concessionaryFound.getAddres());
            etConcessionaryNumber.setText(concessionaryFound.getNumber());
            concessionaryController.close();
        } else{
            txtFormTitle.setText("Agregar Concesionaria");
            btnConcessionaryFormSubmit.setText("Agregar");
        }


        btnConcessionaryFormSubmit.setOnClickListener( view -> {
            Concessionary concessionary = new Concessionary();
            concessionary.setName(etConcessionaryName.getText().toString());
            concessionary.setCity(etConcessionaryCity.getText().toString());
            concessionary.setAddres(etConcessionaryAddress.getText().toString());
            concessionary.setNumber(etConcessionaryNumber.getText().toString());
            if(validateInputFields(concessionary)){
                concessionaryController = new ConcessionaryController(getApplicationContext());
                concessionaryController.open();
                concessionary.setId(id);
                if( id > 0 ? concessionaryController.updateConcessionary(concessionary) : concessionaryController.addConcessionary(concessionary)){
                    Toast.makeText(getApplicationContext(), id > 0 ? "Concesionaria editada exitosamente!" : "Concesionaria agregada exitosamente!", Toast.LENGTH_SHORT).show();
                    concessionaryController.close();
                    Intent i ;
                    if( id > 0 ) {
                        i = new Intent(getApplicationContext(), ConcesionariaDetailActivity.class);
                        i.putExtra("ID", id);
                    }
                    else {
                        i = new Intent(getApplicationContext(), ConcesionariasActivity.class);
                    }
                    startActivity(i);
                    finish();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Error al" + (id > 0 ? "editar" : "agregar" + "una concesionaria. Intenta de nuevo"), Toast.LENGTH_SHORT).show();
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