package com.example.acn4av_jeronimo_lago.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acn4av_jeronimo_lago.R;

import com.example.acn4av_jeronimo_lago.adapters.CarListAdapter;
import com.example.acn4av_jeronimo_lago.controller.ConcessionaryController;
import com.example.acn4av_jeronimo_lago.entities.Car;
import com.example.acn4av_jeronimo_lago.entities.Concessionary;

import java.util.ArrayList;
import java.util.List;

public class ConcesionariaDetailActivity extends AppCompatActivity {

TextView txtNombreConcesionaria;
ImageView btnLaunchEditarConcesionaria;
RecyclerView rvCars;
Button btnAddCar, btnLaunchMaps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concesionaria_detail);
        txtNombreConcesionaria = findViewById(R.id.txtNombreConcesionaria);
        btnLaunchEditarConcesionaria = findViewById(R.id.btnLaunchEditarConcesionaria);
        btnAddCar = findViewById(R.id.btnAddCar);
        btnLaunchMaps = findViewById(R.id.btnLaunchMaps);

        Intent receivedIntent = getIntent();
        Integer id = receivedIntent.getIntExtra("ID", 0);
        btnLaunchEditarConcesionaria.setOnClickListener(view -> {
            Intent i = new Intent(ConcesionariaDetailActivity.this, FormConcesionariaActivity.class);
            i.putExtra("id", id);
            finish();
            startActivity(i);
        });
        btnAddCar.setOnClickListener( view -> {
            Intent i = new Intent(ConcesionariaDetailActivity.this, FormAutoActivity.class);
            i.putExtra("id", id);
            finish();
            startActivity(i);
        });
        btnLaunchMaps.setOnClickListener(view -> {


        });
        ConcessionaryController controller = new ConcessionaryController(getApplicationContext());
        controller.open();
        if(id != 0){
            Concessionary concessionaryFound = controller.getConcessionaryById(id);
            txtNombreConcesionaria.setText(concessionaryFound.getName());
        }
         rvCars = findViewById(R.id.recyclerViewCars);

        ArrayList<Car> carList = controller.getCarsByConcessionaryId(id);

        CarListAdapter adapter = new CarListAdapter(carList, id);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCars.setLayoutManager(layoutManager);

        rvCars.setAdapter(adapter);

        controller.close();
    }
}