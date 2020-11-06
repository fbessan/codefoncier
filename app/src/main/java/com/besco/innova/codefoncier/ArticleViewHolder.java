package com.besco.innova.codefoncier;

/**
 * Created by fbessan on 13/11/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;


class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtcircleView,txtarticle,txtsection,paragraphe,sousparagraphe;
    public ImageView ivFavoris,ivPartager;
    public View viewparagraphe,viewsousparagraphe;
    ArticleViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        txtcircleView = (TextView) itemView.findViewById(R.id.circleView);
        txtarticle = (TextView) itemView.findViewById(R.id.article);
        txtsection = (TextView) itemView.findViewById(R.id.section);
        paragraphe = (TextView) itemView.findViewById(R.id.paragraphe);
        sousparagraphe = (TextView) itemView.findViewById(R.id.sousparagraphe);

        viewparagraphe = (View) itemView.findViewById(R.id.viewparagraphe);
        viewsousparagraphe = (View) itemView.findViewById(R.id.viewsousparagraphe);

        ivFavoris = (ImageView) itemView.findViewById(R.id.image_1);
        ivPartager = (ImageView) itemView.findViewById(R.id.image_2);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}