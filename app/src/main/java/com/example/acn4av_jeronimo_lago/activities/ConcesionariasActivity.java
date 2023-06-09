package com.example.acn4av_jeronimo_lago.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.acn4av_jeronimo_lago.MainActivity;
import com.example.acn4av_jeronimo_lago.R;
import com.example.acn4av_jeronimo_lago.R.id;
import com.example.acn4av_jeronimo_lago.adapters.ConcessionaryListAdapter;
import com.example.acn4av_jeronimo_lago.controller.ConcessionaryController;
import com.example.acn4av_jeronimo_lago.entities.Concessionary;

import java.util.ArrayList;
import java.util.List;

public class ConcesionariasActivity extends AppCompatActivity {

    RecyclerView rvConcesionarias;
    Button btnVolverAtras;
    ImageButton imgBtnAdd;
    ConcessionaryController concessionaryController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concesionarias);
        rvConcesionarias = findViewById(R.id.rvConcesionarias);
        btnVolverAtras = findViewById(R.id.btnAgregarConcesionaria3);
        imgBtnAdd = findViewById(R.id.imgBtnAdd);
        rvConcesionarias.setLayoutManager(new LinearLayoutManager(this));
        loadConcessionarysInList();
        imgBtnAdd.setOnClickListener( view -> {
            Intent i = new Intent(ConcesionariasActivity.this, FormConcesionariaActivity.class);
            startActivity(i);
            finish();
        });

        btnVolverAtras.setOnClickListener(view -> {
            Intent i = new Intent(ConcesionariasActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });

    }

    public void loadConcessionarysInList(){
        concessionaryController = new ConcessionaryController(getApplicationContext());
        concessionaryController.open();
        ArrayList<Concessionary> concessionaryList = concessionaryController.getAllConcessionaries();

        if (concessionaryList.size() > 0){
            ConcessionaryListAdapter adapter = new ConcessionaryListAdapter(concessionaryList);
            rvConcesionarias.setAdapter(adapter);
        }
        concessionaryController.close();
    }
}