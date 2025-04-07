package com.example.ProyectoDesarrolloDeApps1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.ChangePasswordResponse;
import com.example.ProyectoDesarrolloDeApps1.data.repository.authentication.AuthRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.authentication.ChangePasswordCallback;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RecuperarContrasenaActivity extends AppCompatActivity {

    @Inject
    AuthRepository authRepository;

    private EditText etCorreoRecuperacion;
    private Button btnEnviarCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recupero_pass);

        etCorreoRecuperacion = findViewById(R.id.etCorreoRecuperacion);
        btnEnviarCorreo = findViewById(R.id.btnEnviarCorreo);
        TextView tvVolverLogin = findViewById(R.id.tvVolverLogin);

        tvVolverLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RecuperarContrasenaActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnEnviarCorreo.setOnClickListener(v -> enviarSolicitudRecuperacion());
    }

    private void enviarSolicitudRecuperacion() {
        String correo = etCorreoRecuperacion.getText().toString().trim();

        if (correo.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa tu correo", Toast.LENGTH_SHORT).show();
            return;
        }

        ChangePasswordRequest request = new ChangePasswordRequest(correo);

        authRepository.changePassword(request, new ChangePasswordCallback() {
            @Override
            public void onSuccess(ChangePasswordResponse response) {
                runOnUiThread(() -> {
                    Toast.makeText(RecuperarContrasenaActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(RecuperarContrasenaActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }, 4000); // 10 segundos de espera
                });
            }

            @Override
            public void onError(Throwable error) {
                runOnUiThread(() -> Toast.makeText(RecuperarContrasenaActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }
}