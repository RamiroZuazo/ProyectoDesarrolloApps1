package com.example.ProyectoDesarrolloDeApps1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProyectoDesarrolloDeApps1.R;
import com.example.ProyectoDesarrolloDeApps1.data.api.model.Entrega;

import java.util.ArrayList;
import java.util.List;

public class EntregaAdapter extends RecyclerView.Adapter<EntregaAdapter.EntregaViewHolder> {

    private List<Entrega> entregas = new ArrayList<>();

    @NonNull
    @Override
    public EntregaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entrega, parent, false);
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
        private final TextView tvDireccion;
        private final TextView tvEstado;
        private final TextView tvFecha;

        public EntregaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }

        public void bind(Entrega entrega) {
            tvDireccion.setText(entrega.getDireccion());
            tvEstado.setText(entrega.getEstado());
            tvFecha.setText(entrega.getFecha());
        }
    }
} 