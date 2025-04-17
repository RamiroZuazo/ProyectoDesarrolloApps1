package com.example.ProyectoDesarrolloDeApps1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private static final int TIMEOUT_DELAY = 5000; // 5 segundos

    @Inject
    TokenRepository tokenRepository;

    @Inject
    UserRepository userRepository;

    private FirebaseAuth mAuth;
    private TextView userName;
    private TextView userEmail;
    private LinearLayout changePasswordOption;
    private LinearLayout logoutOption;
    private View scrollViewProfile;
    private View errorLayout;
    private TextView errorMessage;
    private Button retryButton;
    private TextView backButton;
    private Handler timeoutHandler;
    private boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        timeoutHandler = new Handler(Looper.getMainLooper());
        mAuth = FirebaseAuth.getInstance();

        // Inicializar vistas
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        changePasswordOption = view.findViewById(R.id.changePasswordOption);
        logoutOption = view.findViewById(R.id.logoutOption);
        scrollViewProfile = view.findViewById(R.id.scrollViewProfile);
        errorLayout = view.findViewById(R.id.error_layout);

        // Inicializar vistas del error layout
        errorMessage = errorLayout.findViewById(R.id.tvErrorMessage);
        retryButton = errorLayout.findViewById(R.id.btnRetry);
        backButton = errorLayout.findViewById(R.id.tvBack);

        setupListeners();

        // Verificar conexión antes de cargar
        if (isNetworkAvailable()) {
            loadUserData();
        } else {
            showError("No hay conexión a Internet");
        }

        return view;
    }

    private void setupListeners() {
        // Configurar listener para cambiar contraseña
        changePasswordOption.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new CambioContrasenaFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Configurar listener para cerrar sesión
        logoutOption.setOnClickListener(v -> {
            mAuth.signOut();
            tokenRepository.clearToken();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        // Configurar listeners del error layout
        retryButton.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                showContent();
                loadUserData();
            } else {
                showError("No hay conexión a Internet");
            }
        });

        backButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timeoutHandler.removeCallbacksAndMessages(null);
    }

    private boolean isNetworkAvailable() {
        if (getContext() == null) return false;

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) return false;

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void startLoadingTimeout() {
        isLoading = true;
        timeoutHandler.postDelayed(() -> {
            if (isLoading) {
                showError("El servidor está tardando en responder. Por favor, inténtalo de nuevo.");
            }
        }, TIMEOUT_DELAY);
    }

    private void cancelLoadingTimeout() {
        isLoading = false;
        timeoutHandler.removeCallbacksAndMessages(null);
    }

    private void loadUserData() {
        showLoading();
        startLoadingTimeout();

        userRepository.getUserData(new UserServiceCallback<User>() {
            @Override
            public void onSuccess(User user) {
                cancelLoadingTimeout();
                Log.d(TAG, "Usuario recibido exitosamente");

                if (user == null) {
                    showError("No se pudo cargar la información del usuario");
                    return;
                }

                showContent();
                displayUserData(user);
            }

            @Override
            public void onError(Throwable error) {
                cancelLoadingTimeout();
                Log.e(TAG, "Error al obtener los datos del usuario: " + error.getMessage());

                // Si el error es debido a un token inválido, mal formado o expirado, redirigir al inicio de sesión
                if (error.getMessage().contains("Token inválido") || error.getMessage().contains("mal formado") || error.getMessage().contains("expirado")) {
                    // Limpiar el token antes de redirigir
                    tokenRepository.clearToken();
                    Log.e(TAG, "Token inválido, mal formado o expirado, redirigiendo a login.");
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish(); // Finaliza la actividad actual
                } else {
                    showError("Error de conexión: No se pudo conectar con el servidor");
                }
            }
        });
    }

    private void displayUserData(User user) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
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
            });
        }
    }

    private void showError(String message) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                errorMessage.setText(message);
                scrollViewProfile.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
            });
        }
    }

    private void showContent() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                scrollViewProfile.setVisibility(View.VISIBLE);
                errorLayout.setVisibility(View.GONE);
            });
        }
    }

    private void showLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                userName.setText("Cargando...");
                userEmail.setText("Cargando...");
            });
        }
    }
}