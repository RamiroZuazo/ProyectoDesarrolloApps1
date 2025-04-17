package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Configurar los listeners de navegación
        setupNavigation();

        // Cargar el fragment de pedidos al inicio
        if (savedInstanceState == null) {
            loadFragment(new OrdersUnasignedFragment());
        }
    }

    private void setupNavigation() {
        // Botón Home
        findViewById(R.id.nav_home).setOnClickListener(v -> {
            loadFragment(new OrdersUnasignedFragment());
        });

        // Botón Historial (o Record)
        findViewById(R.id.nav_favorites).setOnClickListener(v -> {
            // Aquí cargamos el fragment de historial de pedidos
            loadFragment(new OrdersRecordFragment()); // o HistorialPedidosFragment(), según tu caso
        });

        // Botón QR (ejemplo)
        findViewById(R.id.nav_qr).setOnClickListener(v -> {
            // Cargar un fragment para QR
            // loadFragment(new QrFragment());
        });

        // Botón Favoritos (opcional)
        // findViewById(R.id.nav_favorites).setOnClickListener(...);

        // Botón Perfil
        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            loadFragment(new ProfileFragment());
        });
    }

    // Método para reemplazar el fragmento
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
