package com.gmail.orlandroyd.ohcc.adapters.view_holders;

import android.content.Context;
import android.view.View;

import com.codesgood.views.JustifiedTextView;
import com.gmail.orlandroyd.ohcc.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by OrlanDroyd on 10/4/2019.
 */
public class GlossaryViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView tvTitle;
    public JustifiedTextView tvContent;
    public Context context;

    public GlossaryViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle_card_glossary);
        tvContent = itemView.findViewById(R.id.tvContent_card_glossary);
        this.context = context;
    }

}
