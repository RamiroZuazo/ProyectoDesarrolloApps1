package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProyectoDesarrolloDeApps1.adapters.HistorialEntregasAdapter;
import com.example.ProyectoDesarrolloDeApps1.models.Entrega;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistorialEntregasActivity extends AppCompatActivity {

    private RecyclerView rvHistorialEntregas;
    private LinearLayout llEmptyState;
    private HistorialEntregasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial_entregas);

        // Inicializar vistas
        rvHistorialEntregas = findViewById(R.id.rvHistorialEntregas);
        llEmptyState = findViewById(R.id.llEmptyState);
        ImageButton btnBack = findViewById(R.id.btnBack);

        // Configurar RecyclerView
        rvHistorialEntregas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistorialEntregasAdapter();
        rvHistorialEntregas.setAdapter(adapter);

        // Configurar botón de retroceso
        btnBack.setOnClickListener(v -> finish());

        // Cargar datos de ejemplo (reemplazar con datos reales)
        cargarDatosEjemplo();
    }

    private void cargarDatosEjemplo() {
        List<Entrega> entregas = new ArrayList<>();
        // Agregar algunas entregas de ejemplo
        entregas.add(new Entrega("Av. Corrientes 1234", "15/03/2024", "Entregado"));
        entregas.add(new Entrega("Av. Santa Fe 567", "14/03/2024", "En proceso"));
        entregas.add(new Entrega("Av. Cabildo 890", "13/03/2024", "Entregado"));

        if (entregas.isEmpty()) {
            llEmptyState.setVisibility(View.VISIBLE);
            rvHistorialEntregas.setVisibility(View.GONE);
        } else {
            llEmptyState.setVisibility(View.GONE);
            rvHistorialEntregas.setVisibility(View.VISIBLE);
            adapter.setEntregas(entregas);
        }
    }
} 