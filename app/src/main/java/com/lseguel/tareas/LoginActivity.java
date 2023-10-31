package com.lseguel.tareas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    HashMap<String, String> usuarios = new HashMap<>();
    EditText editTextUsuario, editTextPassword;
    Button botonIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarios.put("usuario1", "contraseña1");
        usuarios.put("usuario2", "contraseña2");

        editTextUsuario = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        botonIniciarSesion = findViewById(R.id.buttonLogin);

        botonIniciarSesion.setOnClickListener(v -> {
            String usuario = editTextUsuario.getText().toString();
            String password = editTextPassword.getText().toString();

            if (verificarCredenciales(usuario, password)) {

                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {

                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean verificarCredenciales(String usuario, String password) {
        return usuarios.containsKey(usuario) && Objects.equals(usuarios.get(usuario), password);
    }
}