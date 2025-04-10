package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;
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

        // Cargar los pedidos a través del repositorio
        loadPedidos();

        return view;
    }

    private void loadPedidos() {
        // Realizar la llamada para obtener pedidos no asignados usando el repositorio
        ordersRepository.obtenerPedidosNoAsignados(new OrdersServiceCallback() {
            @Override
            public void onSuccess(OrdersUnasignedResponse response) {
                if (response.getPedidos() != null) {
                    displayPedidos(response.getPedidos());
                }
            }

            @Override
            public void onError(Throwable error) {
                // Mostrar un mensaje de error si falla
                Toast.makeText(getContext(),
                        "Error al cargar los pedidos: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayPedidos(List<OrdersUnasignedResponse.Pedido> pedidos) {
        // Asegurarse de que pedidosLayout no sea null
        if (pedidosLayout == null) {
            return;
        }

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
            return;
        }

        // Iterar sobre la lista de pedidos y agregarlos al LinearLayout
        for (OrdersUnasignedResponse.Pedido pedido : pedidos) {
            // Crear un nuevo CardView para cada pedido
            MaterialCardView cardView = new MaterialCardView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16); // Margen inferior entre cards
            cardView.setLayoutParams(params);

            // Configurar el CardView
            cardView.setContentPadding(30, 30, 30, 30);
            cardView.setRadius(16f);
            cardView.setCardElevation(4f);
            cardView.setUseCompatPadding(true);

            // Crear un LinearLayout para contener los detalles del pedido
            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(16, 16, 16, 16);

            // Crear los TextViews para mostrar la información del pedido
            TextView tvEstante = new TextView(getContext());
            tvEstante.setText("Estante: " + pedido.getEstante());
            tvEstante.setTextSize(18);
            tvEstante.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
            tvEstante.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView tvGondola = new TextView(getContext());
            tvGondola.setText("Góndola: " + pedido.getGondola());
            tvGondola.setTextSize(16);
            tvGondola.setTextColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
            tvGondola.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Agregar los TextViews al layout
            layout.addView(tvEstante);
            layout.addView(tvGondola);

            // Agregar el layout al CardView
            cardView.addView(layout);

            // Finalmente, agregar el CardView al LinearLayout principal
            pedidosLayout.addView(cardView);
        }
    }

}
