package com.example.acn4av_jeronimo_lago.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.acn4av_jeronimo_lago.R;
import com.example.acn4av_jeronimo_lago.controller.ConcessionaryController;
import com.example.acn4av_jeronimo_lago.entities.Concessionary;

public class ConcesionariaDetailActivity extends AppCompatActivity {

TextView txtNombreConcesionaria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concesionaria_detail);
        txtNombreConcesionaria = findViewById(R.id.txtNombreConcesionaria);
        Intent receivedIntent = getIntent();
        Integer id = receivedIntent.getIntExtra("ID", 0);
        ConcessionaryController controller = new ConcessionaryController(getApplicationContext());
        controller.open();
        if(id != 0){
            Concessionary concessionaryFound = controller.getConcessionaryById(id);
            txtNombreConcesionaria.setText(concessionaryFound.getName());
        }

        controller.close();
    }

}