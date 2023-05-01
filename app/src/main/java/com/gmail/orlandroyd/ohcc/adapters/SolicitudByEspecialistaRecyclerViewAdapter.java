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
public class SolicitudByEspecialistaRecyclerViewAdapter extends RecyclerView.Adapter<SolicitudByEspecialistaRecyclerViewAdapter.TramiteViewHolder> {
    private List<Solicitud> itemList;
    private Context context;
    private OnItemClicklistener listener;

    public SolicitudByEspecialistaRecyclerViewAdapter(List<Solicitud> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public TramiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tramite_especialista, null);
        TramiteViewHolder rcv = new TramiteViewHolder(layoutView, context);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull TramiteViewHolder holder, int position) {
        holder.tvDescripcion.setText(itemList.get(position).getDescripcion());

        String fechaCreada = getFecha(itemList.get(position).getFecha_creada());
        holder.tvFechaCreada.setText(fechaCreada);

        String fechaIniciada = itemList.get(position).getFecha_iniciada();
        if (fechaIniciada != null) {
            holder.tvFechaIniciada.setText(getFecha(fechaIniciada));
        }

        String fechaFinalizada = itemList.get(position).getFecha_respuesta();
        if (fechaFinalizada != null) {
            holder.tvFechaFinalizada.setText(getFecha(fechaFinalizada));
        }

        double evaluacion = itemList.get(position).getEvaluacion();
        if (evaluacion != -1) {
            if (evaluacion < 3) {
                holder.tvEvaluacion.setTextColor(context.getResources().getColor(R.color.colorM));
            } else if (evaluacion >= 3 && evaluacion < 4) {
                holder.tvEvaluacion.setTextColor(context.getResources().getColor(R.color.colorB));
            } else if (evaluacion >= 4) {
                holder.tvEvaluacion.setTextColor(context.getResources().getColor(R.color.colorE));
            }
            holder.tvEvaluacion.setText("" + evaluacion);
        }

        int idUsuario = itemList.get(position).getId_usuario();
        if (idUsuario != -1) {
            holder.tvIdUsuario.setText("" + idUsuario);
        }

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
                String respuesta = itemList.get(position).getRespuesta();
                if (respuesta != null || !respuesta.equals("null")) {
                    holder.tvDescripcion.setText("RESPUESTA: " + itemList.get(position).getRespuesta());
                }
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
        public AppCompatTextView tvFechaIniciada;
        public AppCompatTextView tvFechaFinalizada;
        public AppCompatTextView tvEvaluacion;
        public AppCompatTextView tvIdUsuario;
        public AppCompatTextView tvEstado;
        public AppCompatImageView imgEstado;
        public Context context;

        public TramiteViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion_card_tramite_especialista);
            tvFechaCreada = itemView.findViewById(R.id.tvFechaCreada_card_tramite_especialista);
            tvEstado = itemView.findViewById(R.id.tvEstado_card_tramite_especialista);
            imgEstado = itemView.findViewById(R.id.imgEstado_card_tramite_especialista);
            tvFechaIniciada = itemView.findViewById(R.id.tvFechaIniciada_card_tramite_especialista);
            tvFechaFinalizada = itemView.findViewById(R.id.tvFechaFinalizada_card_tramite_especialista);
            tvEvaluacion = itemView.findViewById(R.id.tvEvaluacion_card_tramite_especialista);
            tvIdUsuario = itemView.findViewById(R.id.tvIdUsuario_card_tramite_especialista);
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
