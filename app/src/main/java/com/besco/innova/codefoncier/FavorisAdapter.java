package com.besco.innova.codefoncier;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.besco.innova.codefoncier.models.Article;

import java.util.List;



class FavorisAdapter extends RecyclerView.Adapter<FavorisViewHolder> {

    private List<Article> articleList;
    private Context context;
    private boolean likeArticle = false;

    FavorisAdapter(Context context, List<Article> articleList) {
        this.articleList = articleList;
        this.context = context;

    }

    @Override
    public FavorisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoris_item_view, null);
        FavorisViewHolder rcv = new FavorisViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final FavorisViewHolder holder, final int position) {

        Log.e("LOGME"," VALUELIST "+articleList.size());
        if(articleList.size() != 0) {
            holder.txtcircleView.setText(String.valueOf(articleList.get(position).getArticle_id()));
            holder.txtarticle.setText(articleList.get(position).getDescription());

            //Section
            if (!articleList.get(position).getSection().isEmpty()) {
                holder.txtsection.setVisibility(View.VISIBLE);
                holder.txtsection.setText(articleList.get(position).getSection());
                holder.txtsection.setSelected(true);

                //Paragraphe
                if (!articleList.get(position).getParagraphe().isEmpty()) {
                    holder.paragraphe.setVisibility(View.VISIBLE);
                    holder.viewparagraphe.setVisibility(View.VISIBLE);
                    holder.paragraphe.setText(articleList.get(position).getParagraphe());
                    holder.paragraphe.setSelected(true);

                    //Sous paragraphe
                    if (!articleList.get(position).getSousparagraphe().isEmpty()) {
                        holder.sousparagraphe.setVisibility(View.VISIBLE);
                        holder.viewsousparagraphe.setVisibility(View.VISIBLE);
                        holder.sousparagraphe.setText(articleList.get(position).getSousparagraphe());
                        holder.sousparagraphe.setSelected(true);
                    }
                }
            }
        }else{

            Toast toast = Toast.makeText(context, "Aucun résultat trouvé", Toast.LENGTH_LONG);
            toast.show();

            Snackbar snackbar = Snackbar.make(holder.card_view, "Aucun résultat trouvé", Snackbar.LENGTH_LONG);
            snackbar.show();

            /*Snackbar snackbar = Snackbar.make(holder.card_view, "Aucun résultat trouvé", Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
            snackbar.show();*/
            //showBasicSplash();
        }


    }

    @Override
    public int getItemCount() {
        return this.articleList.size();
    }

}