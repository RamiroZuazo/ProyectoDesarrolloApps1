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

        // Cargar el fragment de pedidos al inicio
        if (savedInstanceState == null) {
            loadFragment(new PedidosFragment());
        }
    }

    // Método para reemplazar el fragmento
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Reemplaza el contenido del FrameLayout
        transaction.commit(); // Ejecutar la transacción
    }
}
