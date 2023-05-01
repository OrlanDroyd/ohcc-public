package com.gmail.orlandroyd.ohcc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.adapters.adapter_models.GlossaryItem;
import com.gmail.orlandroyd.ohcc.adapters.view_holders.GlossaryViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by OrlanDroyd on 10/4/2019.
 */
public class GlossaryRecyclerViewAdapter extends RecyclerView.Adapter<GlossaryViewHolder> implements Filterable {
    private List<GlossaryItem> itemList;
    private List<GlossaryItem> itemListFull;
    private Context context;

    public GlossaryRecyclerViewAdapter(List<GlossaryItem> itemList, Context context) {
        this.itemList = itemList;
        itemListFull = new ArrayList<>(itemList);
        this.context = context;
    }

    @NonNull
    @Override
    public GlossaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_glossary, null);
        GlossaryViewHolder rcv = new GlossaryViewHolder(layoutView, context);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull GlossaryViewHolder holder, int position) {
        holder.tvTitle.setText(itemList.get(position).getTitle());
        holder.tvContent.setText(itemList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GlossaryItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GlossaryItem item : itemListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
