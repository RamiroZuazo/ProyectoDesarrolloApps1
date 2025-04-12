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
import androidx.core.content.ContextCompat; // Importar esto para usar ContextCompat

import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.OrdersUnasignedResponse;
import com.example.ProyectoDesarrolloDeApps1.data.repository.orders.OrdersRepository;
import com.example.ProyectoDesarrolloDeApps1.data.repository.orders.OrdersServiceCallback;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PedidosFragment extends Fragment {
    private static final String TAG = "PedidosFragment";

    @Inject
    OrdersRepository ordersRepository; // Inyectamos el repositorio, no el ApiService

    private LinearLayout pedidosLayout;

    public PedidosFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        // Inicializar el LinearLayout donde se mostrarán los pedidos
        pedidosLayout = view.findViewById(R.id.pedidosLayout);
        
        if (pedidosLayout == null) {
            Log.e(TAG, "pedidosLayout es NULL - No se encontró la vista con ID pedidosLayout");
        } else {
            Log.d(TAG, "pedidosLayout encontrado correctamente");
        }

        // Cargar los pedidos a través del repositorio
        loadPedidos();

        return view;
    }

    private void loadPedidos() {
        Log.d(TAG, "Iniciando carga de pedidos...");
        // Realizar la llamada para obtener pedidos no asignados usando el repositorio
        ordersRepository.obtenerPedidosNoAsignados(new OrdersServiceCallback() {
            @Override
            public void onSuccess(OrdersUnasignedResponse response) {
                Log.d(TAG, "API respondió exitosamente");
                if (response == null) {
                    Log.e(TAG, "La respuesta es NULL");
                    return;
                }
                
                if (response.getPedidos() == null) {
                    Log.e(TAG, "La lista de pedidos es NULL");
                    return;
                }
                
                Log.d(TAG, "Pedidos recibidos: " + response.getPedidos().size());
                for (OrdersUnasignedResponse.Pedido pedido : response.getPedidos()) {
                    Log.d(TAG, "Pedido: ID=" + pedido.getId() + ", Estante=" + pedido.getEstante() + ", Góndola=" + pedido.getGondola());
                }
                
                displayPedidos(response.getPedidos());
            }

            @Override
            public void onError(Throwable error) {
                // Mostrar un mensaje de error si falla
                Log.e(TAG, "Error al cargar los pedidos: " + error.getMessage(), error);
                Toast.makeText(getContext(),
                        "Error al cargar los pedidos: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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
