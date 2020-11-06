package com.besco.innova.codefoncier;

/**
 * Created by fbessan on 13/11/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.TextView;


class ConseilViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView conseil,now,conseilid;

    ConseilViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        conseilid = (TextView) itemView.findViewById(R.id.conseilid);
        now = (TextView) itemView.findViewById(R.id.now);
        conseil = (TextView) itemView.findViewById(R.id.conseil);


    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}