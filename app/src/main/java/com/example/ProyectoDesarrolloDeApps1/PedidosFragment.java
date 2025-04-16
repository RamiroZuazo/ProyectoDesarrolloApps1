package com.example.ProyectoDesarrolloDeApps1;

import android.content.Context;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat; // Importar esto para usar ContextCompat

import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.OrdersUnasignedResponse;
import com.example.ProyectoDesarrolloDeApps1.data.repository.orders.OrdersRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.orders.OrdersServiceCallback;
import com.example.ProyectoDesarrolloDeApps1.utils.ErrorHandler;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PedidosFragment extends Fragment {
    private static final String TAG = "PedidosFragment";
    private static final int TIMEOUT_DELAY = 5000; // 5 segundos

    @Inject
    OrdersRepository ordersRepository; // Inyectamos el repositorio, no el ApiService

    private LinearLayout pedidosLayout;
    private View scrollViewPedidos;
    private View errorLayout;
    private TextView errorMessage;
    private Button retryButton;
    private TextView backButton;
    private Handler timeoutHandler;
    private boolean isLoading = false;

    public PedidosFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        timeoutHandler = new Handler(Looper.getMainLooper());

        // Inicializar vistas
        pedidosLayout = view.findViewById(R.id.pedidosLayout);
        scrollViewPedidos = view.findViewById(R.id.scrollViewPedidos);
        errorLayout = view.findViewById(R.id.error_layout);
        
        // Inicializar vistas del error layout
        errorMessage = errorLayout.findViewById(R.id.tvErrorMessage);
        retryButton = errorLayout.findViewById(R.id.btnRetry);
        backButton = errorLayout.findViewById(R.id.tvBack);

        // Configurar listeners del error layout
        retryButton.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                showContent();
                loadPedidos();
            } else {
                showError("No hay conexión a Internet");
            }
        });

        backButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        if (pedidosLayout == null) {
            Log.e(TAG, "pedidosLayout es NULL - No se encontró la vista con ID pedidosLayout");
        } else {
            Log.d(TAG, "pedidosLayout encontrado correctamente");
        }

        // Verificar conexión antes de cargar
        if (isNetworkAvailable()) {
            loadPedidos();
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

    private boolean isNetworkAvailable() {
        if (getContext() == null) return false;
        
        ConnectivityManager connectivityManager = (ConnectivityManager) 
            getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (connectivityManager == null) return false;

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadPedidos() {
        Log.d(TAG, "Iniciando carga de pedidos...");
        showLoading();
        startLoadingTimeout();
        
        ordersRepository.obtenerPedidosNoAsignados(new OrdersServiceCallback() {
            @Override
            public void onSuccess(OrdersUnasignedResponse response) {
                cancelLoadingTimeout();
                Log.d(TAG, "API respondió exitosamente");
                if (response == null) {
                    Log.e(TAG, "La respuesta es NULL");
                    showError("No se pudieron cargar los pedidos");
                    return;
                }
                
                if (response.getPedidos() == null) {
                    Log.e(TAG, "La lista de pedidos es NULL");
                    showError("No hay pedidos disponibles");
                    return;
                }
                
                if (response.getPedidos().isEmpty()) {
                    showError("No hay pedidos disponibles en este momento");
                    return;
                }
                
                Log.d(TAG, "Pedidos recibidos: " + response.getPedidos().size());
                for (OrdersUnasignedResponse.Pedido pedido : response.getPedidos()) {
                    Log.d(TAG, "Pedido: ID=" + pedido.getId() + ", Estante=" + pedido.getEstante() + ", Góndola=" + pedido.getGondola());
                }
                
                showContent();
                displayPedidos(response.getPedidos());
            }

            @Override
            public void onError(Throwable error) {
                cancelLoadingTimeout();
                Log.e(TAG, "Error al cargar los pedidos: " + error.getMessage(), error);
                showError("Error de conexión: No se pudo conectar con el servidor");
            }
        });
    }

    private void showError(String message) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                errorMessage.setText(message);
                scrollViewPedidos.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
            });
        }
    }

    private void showContent() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                scrollViewPedidos.setVisibility(View.VISIBLE);
                errorLayout.setVisibility(View.GONE);
            });
        }
    }

    private void showLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                pedidosLayout.removeAllViews();
                TextView loadingText = new TextView(getContext());
                loadingText.setText("Cargando pedidos...");
                loadingText.setTextSize(18);
                loadingText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                loadingText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                pedidosLayout.addView(loadingText);
            });
        }
    }

    private void displayPedidos(List<OrdersUnasignedResponse.Pedido> pedidos) {
        // Asegurarse de que pedidosLayout no sea null
        if (pedidosLayout == null) {
            Log.e(TAG, "No se puede mostrar pedidos porque pedidosLayout es NULL");
            return;
        }

        Log.d(TAG, "Mostrando " + pedidos.size() + " pedidos");
        
        // Limpiar los pedidos anteriores
        pedidosLayout.removeAllViews();

        // Si no hay pedidos, mostrar un mensaje
        if (pedidos.isEmpty()) {
            TextView noPedidosText = new TextView(getContext());
            noPedidosText.setText("No hay pedidos disponibles");
            noPedidosText.setTextSize(18);
            noPedidosText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            noPedidosText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            pedidosLayout.addView(noPedidosText);
            Log.d(TAG, "No hay pedidos para mostrar");
            return;
        }

        // Iterar sobre la lista de pedidos y agregarlos al LinearLayout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (OrdersUnasignedResponse.Pedido pedido : pedidos) {
            // Inflar la vista de item_pedido
            View pedidoView = inflater.inflate(R.layout.item_pedido, pedidosLayout, false);
            
            // Obtener referencias a los TextViews dentro del layout inflado
            TextView tvId = pedidoView.findViewById(R.id.tvIdPedido);
            TextView tvEstante = pedidoView.findViewById(R.id.tvEstantePedido);
            TextView tvGondola = pedidoView.findViewById(R.id.tvGondolaPedido);
            
            if (tvId == null ||  tvEstante == null || tvGondola == null) {
                Log.e(TAG, "No se encontraron las vistas dentro de item_pedido.xml");
                continue;
            }
            
            // Establecer los datos del pedido en las vistas
            tvId.setText("Pedido #" + pedido.getId());
            tvEstante.setText("Estante: " + pedido.getEstante());
            tvGondola.setText("Góndola: " + pedido.getGondola());
            
            Log.d(TAG, "Agregando pedido a la vista: ID=" + pedido.getId() + 
                  ", Estante=" + pedido.getEstante() + 
                  ", Góndola=" + pedido.getGondola());

            // Agregar la vista inflada al layout principal
            pedidosLayout.addView(pedidoView);
        }
        
        // Forzar un redibujado del layout
        pedidosLayout.invalidate();
        
        Log.d(TAG, "Pedidos mostrados correctamente. Cantidad: " + pedidos.size());
    }
}
