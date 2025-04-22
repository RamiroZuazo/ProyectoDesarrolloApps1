package com.example.ProyectoDesarrolloDeApps1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.RegisterRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.RegisterResponse;
import com.example.ProyectoDesarrolloDeApps1.data.repository.authentication.AuthRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.authentication.AuthServiceCallback;
import com.example.ProyectoDesarrolloDeApps1.data.repository.token.TokenRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterUserActivity extends AppCompatActivity {

    @Inject
    AuthRepository authRepository;

    @Inject
    TokenRepository tokenRepository;

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
            } else if (contrasena.length() < 6) {
                // Verificar que la contraseña tenga al menos 6 caracteres
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser(RegisterRequest request) {
        authRepository.registerUser(request, new AuthServiceCallback() {
            @Override
            public void onSuccess(RegisterResponse response) {
                // Guardar el token recibido
                if (response.getToken() != null) {
                    tokenRepository.saveToken(response.getToken());
                }

                // Mostrar el mensaje de éxito y la instrucción para verificar el correo
                Toast.makeText(RegisterUserActivity.this,
                        "Registro exitoso. Por favor revisa tu correo electrónico para verificar tu cuenta :).", Toast.LENGTH_LONG).show();

                // Usar Handler para retrasar la redirección a la pantalla de login por 10 segundos
                new Handler().postDelayed(() -> {
                    // Redirigir a la pantalla de login
                    Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }, 10000); // 10000 milisegundos = 10 segundos
            }

            @Override
            public void onError(Throwable error) {
                // Mostrar el error del servidor en un Toast
                Toast.makeText(RegisterUserActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
