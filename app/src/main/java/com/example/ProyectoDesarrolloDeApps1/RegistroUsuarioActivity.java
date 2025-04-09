package com.example.ProyectoDesarrolloDeApps1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.RegisterRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.RegisterResponse;
import com.example.ProyectoDesarrolloDeApps1.data.repository.authentication.AuthRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.authentication.AuthServiceCallback;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegistroUsuarioActivity extends AppCompatActivity {

    @Inject
    AuthRepository authRepository;

    private EditText etNombreCompleto, etEmail, etContrasena, etRepetirContrasena, etTelefono;
    private Button btnRegistrar;
    private TextView tvIrAlLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuario);

        // Inicializar los campos
        etNombreCompleto = findViewById(R.id.etNombreCompleto);
        etEmail = findViewById(R.id.etEmail);
        etContrasena = findViewById(R.id.etContrasena);
        etRepetirContrasena = findViewById(R.id.etRepetirContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        btnRegistrar = findViewById(R.id.btnRegistrarse);
        tvIrAlLogin = findViewById(R.id.tvIrAlLogin);

        // Configurar el botón de registro
        btnRegistrar.setOnClickListener(v -> {
            String nombreCompleto = etNombreCompleto.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String contrasena = etContrasena.getText().toString().trim();
            String repetirContrasena = etRepetirContrasena.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();

            // Validar campos
            if (nombreCompleto.isEmpty() || email.isEmpty() || contrasena.isEmpty() || repetirContrasena.isEmpty() || telefono.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (!contrasena.equals(repetirContrasena)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else {
                // Crear el objeto RegisterRequest
                RegisterRequest registerRequest = new RegisterRequest(email, contrasena, nombreCompleto, telefono);

                // Llamar al repositorio de Auth para registrar el usuario
                registerUser(registerRequest);
            }
        });

        // Redirigir a la pantalla de login
        tvIrAlLogin.setOnClickListener(v -> {
            // Cambiar a la pantalla de login
            Intent intent = new Intent(RegistroUsuarioActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser(RegisterRequest request) {
        authRepository.registerUser(request, new AuthServiceCallback() {
            @Override
            public void onSuccess(RegisterResponse response) {
                Toast.makeText(RegistroUsuarioActivity.this,
                        "Registro exitoso. Bienvenido " + response.getUser().getName(), Toast.LENGTH_SHORT).show();

                // Ir a la pantalla de login o pantalla principal
                Intent intent = new Intent(RegistroUsuarioActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable error) {
                Toast.makeText(RegistroUsuarioActivity.this,
                        "Error en el registro: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
