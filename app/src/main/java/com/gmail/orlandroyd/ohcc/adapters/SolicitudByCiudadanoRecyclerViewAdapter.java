package com.gmail.orlandroyd.ohcc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesgood.views.JustifiedTextView;
import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.networking.api.model.Solicitud;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by OrlanDroyd on 10/4/2019.
 */
public class SolicitudByCiudadanoRecyclerViewAdapter extends RecyclerView.Adapter<SolicitudByCiudadanoRecyclerViewAdapter.TramiteViewHolder> {
    private List<Solicitud> itemList;
    private Context context;
    private OnItemClicklistener listener;

    public SolicitudByCiudadanoRecyclerViewAdapter(List<Solicitud> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public TramiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tramite, null);
        TramiteViewHolder rcv = new TramiteViewHolder(layoutView, context);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull TramiteViewHolder holder, int position) {
        holder.tvDescripcion.setText(itemList.get(position).getDescripcion());

        String fecha = getFecha(itemList.get(position).getFecha_creada());
        holder.tvFechaCreada.setText(fecha);

        int id_estado = itemList.get(position).getId_estado();
        switch (id_estado) {
            case 1:
                holder.tvEstado.setText(context.getString(R.string.estado1));
                holder.imgEstado.setImageResource(R.drawable.ic_en_progreso_24dp);
                break;
            case 2:
                holder.tvEstado.setText(context.getString(R.string.estado2));
                holder.imgEstado.setImageResource(R.drawable.ic_priority_high_black_24dp);
                break;
            case 3:
                holder.tvEstado.setText(context.getString(R.string.estado3));
                holder.imgEstado.setImageResource(R.drawable.ic_doble_check_24dp);
                holder.tvDescripcion.setText("RESPUESTA: " + itemList.get(position).getRespuesta());
                break;
            case 4:
                holder.imgEstado.setImageResource(R.drawable.ic_cancel_24dp);
                holder.tvEstado.setText(context.getString(R.string.estado4));
                break;
        }


    }

    private String getFecha(String fecha_creada) {
        String anio = "" + fecha_creada.charAt(0) + fecha_creada.charAt(1) + fecha_creada.charAt(2) + fecha_creada.charAt(3);
        String mes = "" + fecha_creada.charAt(5) + fecha_creada.charAt(6);
        String dia = "" + fecha_creada.charAt(8) + fecha_creada.charAt(9);
        return dia + "/" + mes + "/" + anio;
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    class TramiteViewHolder extends RecyclerView.ViewHolder {
        public JustifiedTextView tvDescripcion;
        public AppCompatTextView tvFechaCreada;
        public AppCompatTextView tvEstado;
        public AppCompatImageView imgEstado;
        public Context context;

        public TramiteViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion_card_tramite);
            tvFechaCreada = itemView.findViewById(R.id.tvFechaCreada_card_tramite);
            tvEstado = itemView.findViewById(R.id.tvEstado_card_tramite);
            imgEstado = itemView.findViewById(R.id.imgEstado_card_tramite);
            this.context = context;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(itemList.get(position));
                    }
                }
            });
        }
    }


    public interface OnItemClicklistener {
        void OnItemClick(Solicitud solicitud);
    }

    public void setOnItemClickListener(OnItemClicklistener listener) {
        this.listener = listener;
    }
}
