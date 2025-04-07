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

import com.example.ProyectoDesarrolloDeApps1.data.repository.token.TokenRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    // Definimos las vistasx
    private EditText etEmail, etPassword;
    private Button btnIniciarSesion, btnCrearCuenta;
    private TextView tvOlvidasteContrasena;

    // Firebase Authentication
    private FirebaseAuth mAuth;

    @Inject
    TokenRepository tokenRepository;

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
                            // Si la validación es exitosa, obtener el token de Firebase
                            mAuth.getCurrentUser().getIdToken(true) // El parámetro "true" indica que se actualice el token si es necesario
                                    .addOnSuccessListener(idTokenResult -> {
                                        String firebaseIdToken = idTokenResult.getToken();
                                        if (firebaseIdToken != null) {
                                            // Guardar el token de Firebase en el TokenRepository
                                            tokenRepository.saveToken(firebaseIdToken);
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        // Manejar el error al obtener el token
                                        Toast.makeText(MainActivity.this, "Error al obtener el token de Firebase", Toast.LENGTH_SHORT).show();
                                    });

                            // Navegar a la pantalla home
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
            try {
                // Redirigir a la actividad de registro de usuario
                Intent intent = new Intent(MainActivity.this, RegistroUsuarioActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                // Registra el error y muestra un mensaje
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error al navegar: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
