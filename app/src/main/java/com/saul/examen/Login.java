package com.saul.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText user,  password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=findViewById(R.id.txtuser);

        password=findViewById(R.id.txtpass);


    }
 public void login(View v){
     String usuario=user.getText().toString();
     String pass=password.getText().toString();
     if(usuario.equals("Saul")&& pass.equals("1234") || usuario.equals("Diana")&& pass.equals("1234")){
         startActivity(new Intent(Login.this, MainActivity.class));
         finish();
     } else{
         Toast.makeText(Login.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
     }
 }
}