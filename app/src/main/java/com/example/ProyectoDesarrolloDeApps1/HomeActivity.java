package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;
import android.view.View;
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
            loadFragment(new PedidosFragment());
        }
    }

    private void setupNavigation() {
        // Home
        findViewById(R.id.nav_home).setOnClickListener(v -> loadFragment(new PedidosFragment()));

        // Record
        findViewById(R.id.nav_cards).setOnClickListener(v -> {
            // Aquí puedes cargar el fragment de record cuando lo implementes
        });

        // QR
        findViewById(R.id.nav_qr).setOnClickListener(v -> {
            // Aquí puedes cargar el fragment de QR cuando lo implementes
        });

        // Favoritos


        // Profile
        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            // Cargar el fragment de perfil
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