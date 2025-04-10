package com.example.ProyectoDesarrolloDeApps1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import com.example.ProyectoDesarrolloDeApps1.data.repository.token.TokenRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    private TextView userName;
    private TextView userEmail;
    private LinearLayout editProfileOption;
    private LinearLayout settingsOption;
    private LinearLayout logoutOption;

    @Inject
    TokenRepository tokenRepository;

    private FirebaseAuth mAuth;

    public ProfileFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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
            // Cerrar sesión en Firebase
            mAuth.signOut();
            
            // Limpiar el token almacenado
            tokenRepository.clearToken();
            
            // Navegar a la pantalla de inicio de sesión
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        // TODO: Cargar los datos del usuario actual
        // Por ahora, dejamos los valores por defecto en el XML

        return view;
    }
}