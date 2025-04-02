package com.example.ProyectoDesarrolloDeApps1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProyectoDesarrolloDeApps1.R;
import com.example.ProyectoDesarrolloDeApps1.models.Entrega;

import java.util.ArrayList;
import java.util.List;

public class HistorialEntregasAdapter extends RecyclerView.Adapter<HistorialEntregasAdapter.EntregaViewHolder> {

    private List<Entrega> entregas = new ArrayList<>();

    @NonNull
    @Override
    public EntregaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial_entrega, parent, false);
        return new EntregaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntregaViewHolder holder, int position) {
        Entrega entrega = entregas.get(position);
        holder.bind(entrega);
    }

    @Override
    public int getItemCount() {
        return entregas.size();
    }

    public void setEntregas(List<Entrega> entregas) {
        this.entregas = entregas;
        notifyDataSetChanged();
    }

    static class EntregaViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivEstado;
        private final TextView tvDireccion;
        private final TextView tvFecha;
        private final TextView tvEstado;

        public EntregaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEstado = itemView.findViewById(R.id.ivEstado);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvEstado = itemView.findViewById(R.id.tvEstado);
        }

        public void bind(Entrega entrega) {
            tvDireccion.setText(entrega.getDireccion());
            tvFecha.setText(entrega.getFecha());
            tvEstado.setText(entrega.getEstado());

            // Cambiar el color del estado según el valor
            if ("Entregado".equals(entrega.getEstado())) {
                tvEstado.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
                ivEstado.setImageResource(android.R.drawable.ic_menu_send);
            } else {
                tvEstado.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_orange_dark));
                ivEstado.setImageResource(android.R.drawable.ic_menu_upload);
            }
        }
    }
} 