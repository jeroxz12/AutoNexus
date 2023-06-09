package com.example.acn4av_jeronimo_lago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acn4av_jeronimo_lago.activities.ConcesionariasActivity;
import com.example.acn4av_jeronimo_lago.activities.FormAutoActivity;
import com.example.acn4av_jeronimo_lago.activities.RegisterUserActivity;
import com.example.acn4av_jeronimo_lago.controller.UserController;
import com.example.acn4av_jeronimo_lago.entities.User;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button  btnIniciarSesion;
    private TextView tvRegister;
    private EditText txtEmail, txtPassword;
    private UserController userController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        txtEmail = findViewById(R.id.txtUserEmail);
        txtPassword = findViewById(R.id.txtPassword);
        tvRegister = findViewById(R.id.tvRegister);

        tvRegister.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, RegisterUserActivity.class);
            startActivity(i);
        });
        btnIniciarSesion.setOnClickListener(view -> {
            String userEmail = txtEmail.getText().toString();
            String userPassword = txtPassword.getText().toString();
            userController = new UserController(getApplicationContext());
            userController.open();
            if (validateInputFields(userEmail,userPassword)) {
                if(userController.loginUser(userEmail,userPassword)){
                    Intent i = new Intent(MainActivity.this, ConcesionariasActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),"Bienvenido a AutoNexus!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),"El email o password ingresados son incorrectos.", Toast.LENGTH_SHORT).show();
                userController.close();
                return;
            }
            Toast.makeText(getApplicationContext(),"Los valores ingresados son invalidos, revisalos.", Toast.LENGTH_SHORT).show();
            userController.close();
        });
    }
    public Boolean validateInputFields(String email, String password) {
        List<String> listOfInputValues= Arrays.asList(new String[]{email,password});
        if(listOfInputValues.stream().allMatch(value -> value.length() > 5)){
            if(listOfInputValues.get(0).matches("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$")){
                return true;
            }
            Toast.makeText(getApplicationContext(),"El email ingresado es invalido.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
}