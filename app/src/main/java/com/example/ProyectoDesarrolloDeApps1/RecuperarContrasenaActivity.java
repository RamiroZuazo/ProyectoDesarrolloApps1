package com.example.ProyectoDesarrolloDeApps1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecuperarContrasenaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recupero_pass);

        // Encontrar el TextView de "Ya tienes cuenta"
        TextView tvVolverLogin = findViewById(R.id.tvVolverLogin);
        
        // Configurar el click listener para volver al inicio de sesión
        tvVolverLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RecuperarContrasenaActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Cierra la actividad actual
        });
    }
}
