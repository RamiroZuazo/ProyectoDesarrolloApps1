package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileDetailsFragment extends Fragment {

    private LinearLayout changePasswordOption;
    private TextView userEmail;

    public ProfileDetailsFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.profile_details_layout, container, false);

        // Inicializar las vistas
        changePasswordOption = view.findViewById(R.id.changePasswordOption);
        userEmail = view.findViewById(R.id.userEmail);

        // Obtener el usuario actual
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();

            if (email != null) {
                userEmail.setText(email);
            }
        }

        // Configurar listener para cambiar contraseña
        changePasswordOption.setOnClickListener(v -> {
            // Navegar al fragmento de cambio de contraseña
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new CambioContrasenaFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
} 