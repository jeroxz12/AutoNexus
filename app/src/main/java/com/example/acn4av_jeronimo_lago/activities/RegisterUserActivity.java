package com.example.acn4av_jeronimo_lago.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acn4av_jeronimo_lago.MainActivity;
import com.example.acn4av_jeronimo_lago.R;
import com.example.acn4av_jeronimo_lago.controller.UserController;
import com.example.acn4av_jeronimo_lago.entities.User;

import java.util.Arrays;
import java.util.List;

public class RegisterUserActivity extends AppCompatActivity {

    public EditText etNombreUsuario, etApellidoUsuario, etEmailUsuario, etPasswordUsuario, etConfirmPasswordUsuario;
    public Button btnRegistrarUsuario;

    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        etApellidoUsuario = findViewById(R.id.etCarModel);
        etEmailUsuario = findViewById(R.id.etCarPrice);
        etPasswordUsuario = findViewById(R.id.etCarYear);
        etConfirmPasswordUsuario = findViewById(R.id.etConfirmPasswordUsuario);
        btnRegistrarUsuario = findViewById(R.id.btnSubmitFormAuto);
        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User userToAdd = new User();
                userToAdd.setName(etNombreUsuario.getText().toString());
                userToAdd.setLastName(etApellidoUsuario.getText().toString());
                userToAdd.setEmail(etEmailUsuario.getText().toString());
                userToAdd.setPassword(etPasswordUsuario.getText().toString());
                userController = new UserController(getApplicationContext());
                userController.open();

                if( etConfirmPasswordUsuario.getText().toString().equals(userToAdd.getPassword())){
                    if(validateInputFields(userToAdd)){
                        if(userController.addUser(userToAdd)){

                            Toast.makeText(getApplicationContext(),"Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            userController.close();
                            Intent i = new Intent(RegisterUserActivity.this, MainActivity.class);
                            startActivity(i);
                            return;
                        }

                    }
                    Toast.makeText(getApplicationContext(),"Los valores ingresados no son validos.", Toast.LENGTH_SHORT).show();
                    userController.close();

                    return;
                }
                Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                userController.close();
            }
        });
    }


    public Boolean validateInputFields(User user) {
        List<String> listOfInputValues= Arrays.asList(new String[]{user.getName(), user.getLastName(), user.getEmail(), user.getPassword(), etConfirmPasswordUsuario.getText().toString()});
        if(listOfInputValues.stream().allMatch(value -> value.length() > 3)){
            if(listOfInputValues.get(2).matches("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$")){
                return true;
            }
            Toast.makeText(getApplicationContext(),"El email ingresado es invalido.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
}