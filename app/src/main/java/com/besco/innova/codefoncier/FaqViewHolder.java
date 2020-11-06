package com.besco.innova.codefoncier;

/**
 * Created by fbessan on 13/11/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;


class FaqViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView question,reponse;

    FaqViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        question = (TextView) itemView.findViewById(R.id.question);
        reponse = (TextView) itemView.findViewById(R.id.reponse);


    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}