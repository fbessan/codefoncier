package com.besco.innova.codefoncier;

/**
 * Created by fbessan on 13/11/2016.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


class FavorisViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtcircleView,txtarticle,txtsection,paragraphe,sousparagraphe;
    public View viewparagraphe,viewsousparagraphe;
    //public RelativeLayout norecord;
    public CardView card_view;

    FavorisViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        txtcircleView = (TextView) itemView.findViewById(R.id.circleView);
        txtarticle = (TextView) itemView.findViewById(R.id.article);
        txtsection = (TextView) itemView.findViewById(R.id.section);
        paragraphe = (TextView) itemView.findViewById(R.id.paragraphe);
        sousparagraphe = (TextView) itemView.findViewById(R.id.sousparagraphe);

        viewparagraphe = (View) itemView.findViewById(R.id.viewparagraphe);
        viewsousparagraphe = (View) itemView.findViewById(R.id.viewsousparagraphe);


        card_view = (CardView) itemView.findViewById(R.id.card_view);

    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}