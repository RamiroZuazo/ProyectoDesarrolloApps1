package com.example.ProyectoDesarrolloDeApps1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // Definimos las vistas
    private EditText etEmail, etPassword;
    private Button btnIniciarSesion, btnCrearCuenta;
    private TextView tvOlvidasteContrasena;

    // Firebase Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.inicios_sesion);

        // Inicializamos FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Inicializamos las vistas
        etEmail = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        tvOlvidasteContrasena = findViewById(R.id.tvOlvidasteContrasena);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        // Configuramos la ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Acción de iniciar sesión
        btnIniciarSesion.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validar campos no vacíos
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar en Firebase Authentication
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Si la validación es exitosa, navegar a la pantalla home
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();  // Finaliza la actividad actual
                        } else {
                            // Si la validación falla
                            Toast.makeText(MainActivity.this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Acción de "¿Olvidaste tu contraseña?"
        tvOlvidasteContrasena.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecuperarContrasenaActivity.class);
            startActivity(intent);
        });

        // Acción de "Crear Cuenta"
        btnCrearCuenta.setOnClickListener(v -> {
            // Redirigir a la actividad de registro de usuario
            Intent intent = new Intent(MainActivity.this, RegistroUsuarioActivity.class);
            startActivity(intent);
        });
    }
}
