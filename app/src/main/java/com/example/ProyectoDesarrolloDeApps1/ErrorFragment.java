package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ErrorFragment extends Fragment {

    private View.OnClickListener retryListener;
    private String errorMessage;

    public ErrorFragment() {
        // Requiere un constructor vacío
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            errorMessage = getArguments().getString("error_message", 
                "Ha ocurrido un error inesperado. Por favor, inténtalo de nuevo más tarde.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout de error
        return inflater.inflate(R.layout.error_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvErrorMessage = view.findViewById(R.id.tvErrorMessage);
        Button btnRetry = view.findViewById(R.id.btnRetry);
        TextView tvBack = view.findViewById(R.id.tvBack);

        // Establecer el mensaje de error
        tvErrorMessage.setText(errorMessage);

        // Configurar el botón de reintentar
        btnRetry.setOnClickListener(v -> {
            if (retryListener != null) {
                retryListener.onClick(v);
            }
        });

        // Configurar el botón de volver
        tvBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
    }

    public void setRetryListener(View.OnClickListener listener) {
        this.retryListener = listener;
    }
}
