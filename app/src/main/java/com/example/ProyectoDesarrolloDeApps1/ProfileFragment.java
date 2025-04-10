package com.example.ProyectoDesarrolloDeApps1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private static final String TAG = "ProfileFragment";
    private TextView userName;
    private TextView userEmail;
    private LinearLayout editProfileOption;
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
        logoutOption = view.findViewById(R.id.logoutOption);

        // Verificar si los elementos fueron encontrados
        if (editProfileOption == null) {
            Log.e(TAG, "editProfileOption no encontrado en el layout");
        }
        if (logoutOption == null) {
            Log.e(TAG, "logoutOption no encontrado en el layout");
        }

        // Configurar listeners
        if (editProfileOption != null) {
            editProfileOption.setOnClickListener(v -> {
                // Navegar al fragment de detalles del perfil
                getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProfileDetailsFragment())
                    .addToBackStack(null)
                    .commit();
            });
        }

        if (logoutOption != null) {
            logoutOption.setOnClickListener(v -> {
                // Cerrar sesión en Firebase
                mAuth.signOut();
                
                // Limpiar el token almacenado
                tokenRepository.clearToken();
                
                // Forzar el reinicio de la aplicación
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
                Runtime.getRuntime().exit(0);
            });
        }

        // TODO: Cargar los datos del usuario actual
        // Por ahora, dejamos los valores por defecto en el XML

        return view;
    }
}