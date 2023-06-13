package com.example.acn4av_jeronimo_lago.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acn4av_jeronimo_lago.R;
import com.example.acn4av_jeronimo_lago.controller.ConcessionaryController;
import com.example.acn4av_jeronimo_lago.entities.Concessionary;

public class ConcesionariaDetailActivity extends AppCompatActivity {

TextView txtNombreConcesionaria;
ImageView btnLaunchEditarConcesionaria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concesionaria_detail);
        txtNombreConcesionaria = findViewById(R.id.txtNombreConcesionaria);
        btnLaunchEditarConcesionaria = findViewById(R.id.btnLaunchEditarConcesionaria);
        Intent receivedIntent = getIntent();
        Integer id = receivedIntent.getIntExtra("ID", 0);
        btnLaunchEditarConcesionaria.setOnClickListener(view -> {
            Intent i = new Intent(ConcesionariaDetailActivity.this, FormConcesionariaActivity.class);
            i.putExtra("id", id);
            finish();
            startActivity(i);

        });
        ConcessionaryController controller = new ConcessionaryController(getApplicationContext());
        controller.open();
        if(id != 0){
            Concessionary concessionaryFound = controller.getConcessionaryById(id);
            txtNombreConcesionaria.setText(concessionaryFound.getName());
        }

        controller.close();
    }

}