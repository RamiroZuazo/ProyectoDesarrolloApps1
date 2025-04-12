package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ProyectoDesarrolloDeApps1.data.repository.token.TokenRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.users.UserRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.users.UserServiceCallback;
import com.example.ProyectoDesarrolloDeApps1.model.User;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileDetailsFragment extends Fragment {

    private static final String TAG = "ProfileDetailsFragment";
    private TextView userName;  // Añadido para mostrar el nombre del usuario
    private TextView userEmail;
    private LinearLayout changePasswordOption;
    private LinearLayout deleteAccountOption;

    @Inject
    TokenRepository tokenRepository;

    @Inject
    UserRepository userRepository;

    public ProfileDetailsFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.profile_details_layout, container, false);

        // Inicializar las vistas
        userName = view.findViewById(R.id.userName);  // Inicializamos el TextView para el nombre
        userEmail = view.findViewById(R.id.userEmail);
        changePasswordOption = view.findViewById(R.id.changePasswordOption);
        deleteAccountOption = view.findViewById(R.id.deleteAccountOption);

        // Obtener datos del usuario desde el repositorio
        loadUserDataFromRepository();

        // Configurar listener para cambiar contraseña
        changePasswordOption.setOnClickListener(v -> {
            // Navegar al fragmento de cambio de contraseña
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new CambioContrasenaFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Configurar listener para eliminar cuenta
        deleteAccountOption.setOnClickListener(v -> {
            // Lógica para eliminar cuenta (opcional)
            Toast.makeText(getContext(), "Eliminar cuenta - Funcionalidad no implementada", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void loadUserDataFromRepository() {
        userRepository.getUserData(new UserServiceCallback<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "Usuario recibido exitosamente");

                if (user != null) {
                    Log.d(TAG, "Nombre: " + user.getName());
                    Log.d(TAG, "Email: " + user.getEmail());

                    // Actualiza los campos en la UI
                    if (user.getName() != null && !user.getName().isEmpty()) {
                        userName.setText(user.getName());  // Asignamos el nombre al TextView
                    } else {
                        userName.setText("Nombre no disponible");
                    }

                    if (user.getEmail() != null) {
                        userEmail.setText(user.getEmail());
                    } else {
                        userEmail.setText("Email no disponible");
                    }
                } else {
                    Log.e(TAG, "El usuario es NULL");
                    userName.setText("Usuario no encontrado");
                    userEmail.setText("Email no encontrado");
                }
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "Error al obtener los datos del usuario: " + error.getMessage());
                Toast.makeText(getContext(), "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
