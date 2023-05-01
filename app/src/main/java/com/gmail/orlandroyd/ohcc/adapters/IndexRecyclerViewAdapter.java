package com.gmail.orlandroyd.ohcc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.adapters.adapter_models.IndexItem;
import com.gmail.orlandroyd.ohcc.adapters.view_holders.IndexViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by OrlanDroyd on 10/4/2019.
 */
public class IndexRecyclerViewAdapter extends RecyclerView.Adapter<IndexViewHolder> {
    private List<IndexItem> itemList;
    private Context context;

    public IndexRecyclerViewAdapter(List<IndexItem> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public IndexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_index, null);
        IndexViewHolder rcv = new IndexViewHolder(layoutView, context);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull IndexViewHolder holder, int position) {
        holder.tvTitle.setText(itemList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
