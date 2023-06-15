package com.example.acn4av_jeronimo_lago.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.acn4av_jeronimo_lago.R;

public class MapActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    //private GoogleMap googleMap;
    private String city;
    private String address;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }
}