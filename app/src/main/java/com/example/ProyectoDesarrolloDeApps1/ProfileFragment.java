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
import androidx.fragment.app.FragmentTransaction;
import com.example.ProyectoDesarrolloDeApps1.data.repository.token.TokenRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.users.UserRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.users.UserServiceCallback;
import com.example.ProyectoDesarrolloDeApps1.model.User;
import com.google.firebase.auth.FirebaseAuth;

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
    TokenRepository tokenRepository;  // Inyectar el TokenRepository

    @Inject
    UserRepository userRepository;  // Inyectar el UserRepository

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

        // Obtener datos del usuario desde el repositorio
        loadUserDataFromRepository();

        // Configurar listeners
        if (editProfileOption != null) {
            editProfileOption.setOnClickListener(v -> {
                // Navegar a detalles del perfil
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new ProfileDetailsFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }

        if (logoutOption != null) {
            logoutOption.setOnClickListener(v -> {
                // Cerrar sesión en Firebase
                mAuth.signOut();

                // Limpiar el token almacenado
                tokenRepository.clearToken();  // Usar el método del TokenRepository

                // Forzar el reinicio de la aplicación
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
                Runtime.getRuntime().exit(0);
            });
        }

        return view;
    }

    private void loadUserDataFromRepository() {
        userRepository.getUserData(new UserServiceCallback<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "Usuario recibido exitosamente");
                Log.d(TAG, "Nombre: " + user);
                if (user != null) {
                    Log.d(TAG, "Nombre: " + user.getName());
                    Log.d(TAG, "Email: " + user.getEmail());

                    // Actualiza los campos en la UI
                    if (user.getName() != null && !user.getName().isEmpty()) {
                        userName.setText(user.getName());
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
