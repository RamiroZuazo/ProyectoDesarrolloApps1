package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.Entrega;
import com.example.ProyectoDesarrolloDeApps1.data.repository.EntregaRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NuevaEntregaActivity extends AppCompatActivity {

    @Inject
    EntregaRepository entregaRepository;

    private EditText etDireccion;
    private EditText etCliente;
    private EditText etDescripcion;
    private Button btnCrearEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_entrega);

        // Inicializar vistas
        etDireccion = findViewById(R.id.etDireccion);
        etCliente = findViewById(R.id.etCliente);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnCrearEntrega = findViewById(R.id.btnCrearEntrega);

        // Configurar botón de crear entrega
        btnCrearEntrega.setOnClickListener(v -> crearEntrega());
    }

    private void crearEntrega() {
        String direccion = etDireccion.getText().toString().trim();
        String cliente = etCliente.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        // Validar campos
        if (direccion.isEmpty() || cliente.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implementar la creación real de la entrega
        // Por ahora, solo mostramos un mensaje de éxito
        Toast.makeText(this, "Entrega creada exitosamente", Toast.LENGTH_SHORT).show();
        finish();
    }
} 