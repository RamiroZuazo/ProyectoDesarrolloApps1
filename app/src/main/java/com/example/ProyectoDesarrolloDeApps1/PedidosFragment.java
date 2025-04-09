package com.example.ProyectoDesarrolloDeApps1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ProyectoDesarrolloDeApps1.data.api.OrdersApiService;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.orders.OrdersUnasignedResponse;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class PedidosFragment extends Fragment {

    @Inject
    OrdersApiService ordersApiService;  // Inyección de OrdersApiService

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

        // Cargar los pedidos desde la API
        loadPedidos();

        return view;
    }

    private void loadPedidos() {
        // Realizar la llamada a la API para obtener los pedidos no asignados
        ordersApiService.obtenerPedidosNoAsignados().enqueue(new Callback<OrdersUnasignedResponse>() {
            @Override
            public void onResponse(Call<OrdersUnasignedResponse> call, Response<OrdersUnasignedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrdersUnasignedResponse ordersResponse = response.body();
                    if (ordersResponse.getPedidos() != null) {
                        // Llamar al método para mostrar los pedidos
                        displayPedidos(ordersResponse.getPedidos());
                    }
                } else {
                    // Mostrar un mensaje de error si la respuesta no es exitosa
                    Toast.makeText(getContext(), "Error al cargar los pedidos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersUnasignedResponse> call, Throwable t) {
                // Mostrar un mensaje si la llamada a la API falla
                Toast.makeText(getContext(), "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayPedidos(List<OrdersUnasignedResponse.Pedido> pedidos) {
        // Asegurarse de que pedidosLayout no sea null
        if (pedidosLayout == null) {
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
            cardView.setLayoutParams(params);

            // Aumentar el padding y los márgenes del CardView
            cardView.setContentPadding(30, 30, 30, 30);
            cardView.setRadius(16f);  // Opcional: cambiar el radio de las esquinas para hacerlo más estético

            // Crear un LinearLayout para contener los detalles del pedido
            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(40, 40, 40, 40);  // Aumentamos el padding

            // Crear los TextViews para mostrar la información del pedido
            TextView tvEstante = new TextView(getContext());
            tvEstante.setText("Estante: " + pedido.getEstante());
            tvEstante.setTextSize(20);  // Aumentamos el tamaño del texto
            tvEstante.setTextColor(getResources().getColor(android.R.color.black));

            TextView tvGondola = new TextView(getContext());
            tvGondola.setText("Góndola: " + pedido.getGondola());
            tvGondola.setTextSize(18);  // Aumentamos el tamaño del texto
            tvGondola.setTextColor(getResources().getColor(android.R.color.darker_gray));

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
