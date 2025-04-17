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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.RecordOrdersResponse;
import com.example.ProyectoDesarrolloDeApps1.data.repository.orders.OrdersRecordCallback;
import com.example.ProyectoDesarrolloDeApps1.data.repository.orders.OrdersRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.token.TokenRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrdersRecordFragment extends Fragment {
    private static final String TAG = "OrdersFragment";
    private static final int TIMEOUT_DELAY = 5000; // 5 segundos

    @Inject
    OrdersRepository ordersRepository;
    @Inject
    TokenRepository tokenRepository;
    private LinearLayout historialLayout;
    private View scrollViewHistorial;
    private View errorLayout;
    private TextView errorMessage;
    private Button retryButton;
    private TextView backButton;
    private Handler timeoutHandler;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        timeoutHandler = new Handler(Looper.getMainLooper());

        // Inicializar vistas
        historialLayout = view.findViewById(R.id.historialLayout);
        scrollViewHistorial = view.findViewById(R.id.scrollViewHistorial);
        errorLayout = view.findViewById(R.id.error_layout);

        // Inicializar vistas del error layout
        errorMessage = errorLayout.findViewById(R.id.tvErrorMessage);
        retryButton = errorLayout.findViewById(R.id.btnRetry);
        backButton = errorLayout.findViewById(R.id.tvBack);

        // Configurar listeners del error layout
        retryButton.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                showContent();
                loadHistorial();
            } else {
                showError("No hay conexión a Internet");
            }
        });

        backButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        // Verificar conexión antes de cargar
        if (isNetworkAvailable()) {
            loadHistorial();
        } else {
            showError("No hay conexión a Internet");
        }

        return view;
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

    private void loadHistorial() {
        showLoading();
        startLoadingTimeout();

        ordersRepository.obtenerHistorialPedidos(new OrdersRecordCallback() {
            @Override
            public void onSuccess(RecordOrdersResponse response) {
                cancelLoadingTimeout();
                if (response == null || response.getPedidos() == null) {
                    showError("No se pudo cargar el historial");
                    return;
                }

                showContent();
                displayHistorial(response);
            }

            @Override
            public void onError(Throwable error) {
                cancelLoadingTimeout();
                if (error.getMessage().contains("Token inválido") || error.getMessage().contains("mal formado") || error.getMessage().contains("expirado")) {
                    // Limpiar el token antes de redirigir
                    tokenRepository.clearToken();
                    // Si el error es por un token inválido, mal formado o expirado, redirigimos al inicio de sesión
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

    private void showError(String message) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                errorMessage.setText(message);
                scrollViewHistorial.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
            });
        }
    }

    private void showContent() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                scrollViewHistorial.setVisibility(View.VISIBLE);
                errorLayout.setVisibility(View.GONE);
            });
        }
    }

    private void showLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                historialLayout.removeAllViews();
                TextView loadingText = new TextView(getContext());
                loadingText.setText("Cargando historial...");
                loadingText.setTextSize(18);
                loadingText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                loadingText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                historialLayout.addView(loadingText);
            });
        }
    }

    private void displayHistorial(RecordOrdersResponse response) {
        if (getActivity() == null) return;

        getActivity().runOnUiThread(() -> {
            historialLayout.removeAllViews();

            if (response.getPedidos() == null || response.getPedidos().isEmpty()) {
                TextView emptyStateText = new TextView(getContext());
                emptyStateText.setText("No hay pedidos en el historial");
                emptyStateText.setTextSize(18);
                emptyStateText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                emptyStateText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                historialLayout.addView(emptyStateText);
            } else {
                for (RecordOrdersResponse.Pedido pedido : response.getPedidos()) {
                    // Infla la CardView por cada pedido
                    View cardView = LayoutInflater.from(getContext())
                            .inflate(R.layout.item_historial, historialLayout, false);

                    // Referencia a los Views
                    TextView tvIdPedido = cardView.findViewById(R.id.tvIdPedido);
                    TextView tvEstadoEntrega = cardView.findViewById(R.id.tvEstadoEntrega);
                    TextView tvDuracionEnvio = cardView.findViewById(R.id.tvDuracionEnvio);
                    TextView tvNombreDestinatario = cardView.findViewById(R.id.tvNombreDestinatario);
                    TextView tvDireccion = cardView.findViewById(R.id.tvDireccion);

                    // Llenado de datos
                    tvIdPedido.setText("Pedido #" + pedido.getId());
                    tvEstadoEntrega.setText(pedido.getEstado());

                    // Cálculo de duración
                    long inicio = pedido.getHoraInicio();
                    long fin = pedido.getHoraFin();
                    long duracionSegundos = Math.abs(fin - inicio);
                    String duracionFormateada = formatearDuracion(duracionSegundos);
                    tvDuracionEnvio.setText("Duración: " + duracionFormateada);

                    tvNombreDestinatario.setText("Cliente: " + pedido.getCliente());
                    tvDireccion.setText("Dirección: " + pedido.getDestino());

                    historialLayout.addView(cardView);
                }
            }
        });
    }

    /**
     * Convierte segundos a un formato "X min Y s"
     */
    private String formatearDuracion(long segundos) {
        if (segundos <= 0) {
            return "0 s";
        }

        long minutos = segundos / 60;
        long segRestantes = segundos % 60;

        if (minutos > 0) {
            return minutos + " min " + segRestantes + " s";
        } else {
            return segRestantes + " s";
        }
    }
}
