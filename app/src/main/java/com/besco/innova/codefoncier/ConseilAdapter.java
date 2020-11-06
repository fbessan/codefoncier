package com.besco.innova.codefoncier;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.besco.innova.codefoncier.models.Conseil;

import java.util.List;



class ConseilAdapter extends RecyclerView.Adapter<ConseilViewHolder> {

    private List<Conseil> conseilList;
    private Context context;
    private boolean likeArticle = false;

    ConseilAdapter(Context context, List<Conseil> conseilList) {
        this.conseilList = conseilList;
        this.context = context;

    }

    @Override
    public ConseilViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.conseil_item_view, null);
        ConseilViewHolder rcv = new ConseilViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final ConseilViewHolder holder, final int position) {

        holder.now.setText(String.valueOf(conseilList.get(position).getNowdate()));
        holder.conseil.setText(conseilList.get(position).getLabel());
        holder.conseilid.setText(String.valueOf(conseilList.get(position).getConseil_id()));




    }

    @Override
    public int getItemCount() {
        return this.conseilList.size();
    }

}