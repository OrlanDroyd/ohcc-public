package com.gmail.orlandroyd.ohcc.adapters.view_holders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.activity.PdfFullScreenActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by OrlanDroyd on 10/4/2019.
 */
public class IndexViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvTitle;
    public Context context;

    public IndexViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        tvTitle = itemView.findViewById(R.id.tvTitleDocument_card_index);
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        String title = tvTitle.getText().toString();
        Intent intent = new Intent(context, PdfFullScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("TITLE", title);
        intent.putExtra("MODE", 0);
        context.startActivity(intent);
    }
}
