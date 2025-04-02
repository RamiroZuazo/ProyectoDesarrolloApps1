package com.example.ProyectoDesarrolloDeApps1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.example.ProyectoDesarrolloDeApps1.adapters.EntregaAdapter;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.Entrega;
import com.example.ProyectoDesarrolloDeApps1.data.repository.EntregaRepository;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {

    @Inject
    EntregaRepository entregaRepository;

    private RecyclerView recyclerViewEntregas;
    private EntregaAdapter adapter;
    private Spinner spinnerConfiguracion;
    private ImageView btnConfiguracion;
    private Button btnNuevaEntrega;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inicializar vistas
        recyclerViewEntregas = findViewById(R.id.recyclerViewEntregas);
        spinnerConfiguracion = findViewById(R.id.spinnerConfiguracion);
        btnConfiguracion = findViewById(R.id.btnConfiguracion);
        btnNuevaEntrega = findViewById(R.id.btnNuevaEntrega);

        // Configurar RecyclerView
        recyclerViewEntregas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EntregaAdapter();
        recyclerViewEntregas.setAdapter(adapter);

        // Configurar Spinner
        configurarSpinner();

        // Configurar botón de configuración
        btnConfiguracion.setOnClickListener(v -> spinnerConfiguracion.performClick());

        // Configurar botón de nueva entrega
        btnNuevaEntrega.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NuevaEntregaActivity.class);
            startActivity(intent);
        });

        // Cargar entregas
        cargarEntregas();
    }

    private void configurarSpinner() {
        // Crear el adaptador con las opciones
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
            this,
            R.layout.item_spinner_configuracion,
            new String[]{"", "Historial", "Cerrar Sesión"}
        );
        spinnerAdapter.setDropDownViewResource(R.layout.item_spinner_configuracion);
        spinnerConfiguracion.setAdapter(spinnerAdapter);

        // Configurar el listener
        spinnerConfiguracion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) { // Historial
                    Intent intent = new Intent(HomeActivity.this, HistorialEntregasActivity.class);
                    startActivity(intent);
                    spinnerConfiguracion.setSelection(0); // Resetear a la opción vacía
                } else if (position == 2) { // Cerrar Sesión
                    cerrarSesion();
                    spinnerConfiguracion.setSelection(0); // Resetear a la opción vacía
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
    }

    private void cargarEntregas() {
        entregaRepository.getEntregasAsignadas(new EntregaRepository.EntregaCallback() {
            @Override
            public void onSuccess(List<Entrega> entregas) {
                adapter.setEntregas(entregas);
            }

            @Override
            public void onError(String error) {
                // Manejar el error
                Toast.makeText(HomeActivity.this, "Error al cargar las entregas: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cerrarSesion() {
        mAuth.signOut();
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}