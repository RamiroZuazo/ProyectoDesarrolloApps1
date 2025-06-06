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

    // Definimos las vistas
    private EditText etEmail, etPassword;
    private Button btnIniciarSesion, btnCrearCuenta;
    private TextView tvOlvidasteContrasena;

    // Firebase Authentication
    private FirebaseAuth mAuth;

    @Inject
    TokenRepository tokenRepository;

    @Override
    protected void onStart() {
        super.onStart();

        // Verificamos si el usuario está autenticado
        if (mAuth.getCurrentUser() != null) {
            // Intentamos renovar el token
            mAuth.getCurrentUser().getIdToken(true)
                    .addOnSuccessListener(idTokenResult -> {
                        String firebaseIdToken = idTokenResult.getToken();
                        if (firebaseIdToken != null) {
                            // Guardamos el token renovado en el TokenRepository
                            tokenRepository.saveToken(firebaseIdToken);

                            // Ahora que el token está guardado, se puede redirigir a HomeActivity
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();  // Finaliza la actividad actual
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Si hubo un error al renovar el token
                        Toast.makeText(MainActivity.this, "Error al renovar el token de Firebase", Toast.LENGTH_SHORT).show();
                    });
        }
    }

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

        // Verificar si ya hay un token guardado
        String token = tokenRepository.getToken();
        if (token != null && mAuth.getCurrentUser() != null) {
            // Si el token está disponible y el usuario está autenticado, validamos que el usuario siga autenticado en Firebase
            mAuth.getCurrentUser().getIdToken(true)
                    .addOnSuccessListener(idTokenResult -> {
                        String firebaseIdToken = idTokenResult.getToken();
                        if (firebaseIdToken != null) {
                            // Guardar el token en el TokenRepository si es necesario
                            tokenRepository.saveToken(firebaseIdToken);

                            // Si el token sigue siendo válido, redirigir al usuario a la HomeActivity
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();  // Finaliza la actividad actual
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Si el token no es válido o hubo un error, redirigir al login
                        Toast.makeText(MainActivity.this, "Sesión caducada, por favor inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Si el token no está disponible o el usuario no está autenticado, permitir el inicio de sesión
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
                                // Verificar si el correo electrónico está verificado
                                if (mAuth.getCurrentUser() != null && !mAuth.getCurrentUser().isEmailVerified()) {
                                    // Si el correo no está verificado, mostrar un mensaje y no permitir el inicio de sesión
                                    Toast.makeText(MainActivity.this, "Por favor, verifica tu correo electrónico.", Toast.LENGTH_LONG).show();
                                    mAuth.signOut(); // Opcional: Cerrar sesión si el correo no está verificado
                                } else {
                                    // Si la validación es exitosa y el correo está verificado
                                    mAuth.getCurrentUser().getIdToken(true)
                                            .addOnSuccessListener(idTokenResult -> {
                                                String firebaseIdToken = idTokenResult.getToken();
                                                if (firebaseIdToken != null) {
                                                    // Guardar el token de Firebase en el TokenRepository
                                                    tokenRepository.saveToken(firebaseIdToken);

                                                    // Navegar a la pantalla home
                                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                    finish();  // Finaliza la actividad actual
                                                }
                                            })
                                            .addOnFailureListener(e -> {
                                                // Manejar el error al obtener el token
                                                Toast.makeText(MainActivity.this, "Error al obtener el token de Firebase", Toast.LENGTH_SHORT).show();
                                            });
                                }
                            } else {
                                // Si la validación falla
                                Toast.makeText(MainActivity.this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                            }
                        });
            });

        }

        // Acción de "¿Olvidaste tu contraseña?"
        tvOlvidasteContrasena.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecoverPasswordActivity.class);
            startActivity(intent);
        });

        // Acción de "Crear Cuenta"
        btnCrearCuenta.setOnClickListener(v -> {
            try {
                // Redirigir a la actividad de registro de usuario
                Intent intent = new Intent(MainActivity.this, RegisterUserActivity.class);
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
