package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    private TextView userName;
    private TextView userEmail;
    private LinearLayout editProfileOption;
    private LinearLayout settingsOption;
    private LinearLayout logoutOption;

    public ProfileFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        // Inicializar las vistas
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        editProfileOption = view.findViewById(R.id.editProfileOption);
        settingsOption = view.findViewById(R.id.settingsOption);
        logoutOption = view.findViewById(R.id.logoutOption);

        // Configurar listeners
        editProfileOption.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Editar Perfil", Toast.LENGTH_SHORT).show();
            // Aquí implementarías la navegación a la pantalla de edición de perfil
        });

        settingsOption.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Configuración", Toast.LENGTH_SHORT).show();
            // Aquí implementarías la navegación a la pantalla de configuración
        });

        logoutOption.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Cerrar Sesión", Toast.LENGTH_SHORT).show();
            // Aquí implementarías la lógica para cerrar sesión
        });

        // TODO: Cargar los datos del usuario actual
        // Por ahora, dejamos los valores por defecto en el XML

        return view;
    }
}