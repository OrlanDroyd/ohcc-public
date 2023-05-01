package com.gmail.orlandroyd.ohcc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.networking.api.model.Usuario;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by OrlanDroyd on 20/4/2019.
 */
public class DirectivoRecyclerViewAdapter extends RecyclerView.Adapter<DirectivoRecyclerViewAdapter.DirectivoViewHolder> {
    private List<Usuario> itemList;
    private Context context;
    private OnItemClicklistener listener;

    public DirectivoRecyclerViewAdapter(List<Usuario> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public DirectivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_especialista, null);
        DirectivoViewHolder rcv = new DirectivoViewHolder(layoutView, context);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull DirectivoViewHolder holder, int position) {
        Usuario usuario = itemList.get(position);

        String fullName = usuario.getName() + " " + usuario.getLastName1() + " " + usuario.getLastName2();
        holder.tvFullName.setText(fullName);
        holder.tvNombreUsuario.setText(itemList.get(position).getUser_name());

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    class DirectivoViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvFullName;
        public AppCompatTextView tvNombreUsuario;
        public Context context;

        public DirectivoViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName_card_especialista);
            tvNombreUsuario = itemView.findViewById(R.id.tvNombreUsuario_card_especialista);
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
        void OnItemClick(Usuario usuario);
    }

    public void setOnItemClickListener(OnItemClicklistener listener) {
        this.listener = listener;
    }
}
