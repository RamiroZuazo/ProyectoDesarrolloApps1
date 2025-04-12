package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordResponse;
import com.example.ProyectoDesarrolloDeApps1.data.repository.users.ChangePasswordCallBack;
import com.example.ProyectoDesarrolloDeApps1.data.repository.users.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CambioContrasenaFragment extends Fragment {
    private static final String TAG = "CambioContrasenaFragment";
    private EditText etContrasenaActual, etNuevaContrasena, etConfirmarNuevaContrasena;
    private Button btnGuardarContrasena, btnCancelar;
    
    @Inject
    UserRepository userRepository;

    public CambioContrasenaFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.cambio_contrasena, container, false);
        
        // Inicializar vistas
        etContrasenaActual = view.findViewById(R.id.etContrasenaActual);
        etNuevaContrasena = view.findViewById(R.id.etNuevaContrasena);
        etConfirmarNuevaContrasena = view.findViewById(R.id.etConfirmarNuevaContrasena);
        btnGuardarContrasena = view.findViewById(R.id.btnGuardarContrasena);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        
        // Configurar listeners
        btnGuardarContrasena.setOnClickListener(v -> cambiarContrasena());
        btnCancelar.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        
        return view;
    }
    
    private void cambiarContrasena() {
        String contrasenaActual = etContrasenaActual.getText().toString().trim();
        String nuevaContrasena = etNuevaContrasena.getText().toString().trim();
        String confirmarNuevaContrasena = etConfirmarNuevaContrasena.getText().toString().trim();
        
        // Validar campos
        if (contrasenaActual.isEmpty() || nuevaContrasena.isEmpty() || confirmarNuevaContrasena.isEmpty()) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (nuevaContrasena.length() < 8) {
            Toast.makeText(getContext(), "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!nuevaContrasena.equals(confirmarNuevaContrasena)) {
            Toast.makeText(getContext(), "Las contraseñas nuevas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Verificar que la contraseña actual sea correcta (usando Firebase Auth)
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            // Mostrar indicador de carga
            btnGuardarContrasena.setEnabled(false);
            btnGuardarContrasena.setText("Actualizando...");
            
            // Crear objeto de solicitud
            ChangePasswordRequest request = new ChangePasswordRequest(contrasenaActual, nuevaContrasena);
            
            // Llamar al servicio para cambiar la contraseña
            userRepository.changePassword(request, new ChangePasswordCallBack() {
                @Override
                public void onSuccess(ChangePasswordResponse response) {
                    if (getActivity() == null || !isAdded()) return;
                    
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack(); // Volver atrás
                    });
                }

                @Override
                public void onError(Throwable error) {
                    if (getActivity() == null || !isAdded()) return;
                    
                    getActivity().runOnUiThread(() -> {
                        Log.e(TAG, "Error al cambiar contraseña: " + error.getMessage());
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        resetearBotones();
                    });
                }
            });
        } else {
            Toast.makeText(getContext(), "Error: No se pudo obtener la información del usuario", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void resetearBotones() {
        if (getActivity() == null) return;
        
        getActivity().runOnUiThread(() -> {
            btnGuardarContrasena.setEnabled(true);
            btnGuardarContrasena.setText("Guardar Cambios");
        });
    }
} 