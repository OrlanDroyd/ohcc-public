package com.gmail.orlandroyd.ohcc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.adapters.adapter_models.LibraryItem;
import com.gmail.orlandroyd.ohcc.adapters.view_holders.LibraryViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by OrlanDroyd on 10/4/2019.
 */
public class LibraryRecyclerViewAdapter extends RecyclerView.Adapter<LibraryViewHolder> {
    private List<LibraryItem> itemList;
    private Context context;

    public LibraryRecyclerViewAdapter(List<LibraryItem> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_library, null);
        LibraryViewHolder rcv = new LibraryViewHolder(layoutView, context);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        holder.thumbnail.setImageResource(itemList.get(position).getImgID());
        holder.tvTitle.setText(itemList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
