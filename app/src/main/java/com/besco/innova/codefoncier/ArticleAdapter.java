package com.besco.innova.codefoncier;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.besco.innova.codefoncier.models.Article;


import java.util.List;



class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private List<Article> articleList;
    private Context context;
    private boolean likeArticle = false;

    ArticleAdapter(Context context, List<Article> articleList) {
        this.articleList = articleList;
        this.context = context;

    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_view, null);
        ArticleViewHolder rcv = new ArticleViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, final int position) {

        holder.txtcircleView.setText(String.valueOf(articleList.get(position).getArticle_id()));
        holder.txtarticle.setText(articleList.get(position).getDescription());

        //Section
        if(!articleList.get(position).getSection().isEmpty()) {
            holder.txtsection.setVisibility(View.VISIBLE);
            holder.txtsection.setText(articleList.get(position).getSection());
            holder.txtsection.setSelected(true);

            //Paragraphe
            if(!articleList.get(position).getParagraphe().isEmpty()) {
                holder.paragraphe.setVisibility(View.VISIBLE);
                holder.viewparagraphe.setVisibility(View.VISIBLE);
                holder.paragraphe.setText(articleList.get(position).getParagraphe());
                holder.paragraphe.setSelected(true);

                //Sous paragraphe
                if(!articleList.get(position).getSousparagraphe().isEmpty()) {
                    holder.sousparagraphe.setVisibility(View.VISIBLE);
                    holder.viewsousparagraphe.setVisibility(View.VISIBLE);
                    holder.sousparagraphe.setText(articleList.get(position).getSousparagraphe());
                    holder.sousparagraphe.setSelected(true);
                }
            }
        }
        if(articleList.get(position).getFavoris().equals("1")){
            holder.ivFavoris.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
        }

        Log.e("LOGFAVORIS"," VALUE "+articleList.get(position).getArticle_id() +" _ "+articleList.get(position).getFavoris());
        holder.ivFavoris.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(!likeArticle) {

                    //Mettre Favoris a 1
                    List<Article> articlesave = Article.find(Article.class, "articleid = ?", String.valueOf(articleList.get(position).getArticle_id()));
                    Log.e("LOGFAVORIS"," VALUE "+articlesave.get(0).getId());


                    //articlesave.get(position).setFavoris("1");
                    //articlesave.get(position).save();
                    if(articlesave.size() > 0 && articlesave.get(0).getId() > 0) {
                        Article article = Article.findById(Article.class, articlesave.get(0).getId());
                        article.favoris = "1"; // modify the values
                        article.save();
                    }


                    holder.ivFavoris.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
                    likeArticle = true;
                }else{
                    //Mettre Favoris a 0
                    List<Article> articlesave = Article.find(Article.class, "articleid = ?", String.valueOf(articleList.get(position).getArticle_id()));

                    if(articlesave.size() > 0 && articlesave.get(0).getId() > 0) {
                        Article article = Article.findById(Article.class, articlesave.get(0).getId());
                        article.favoris = "0"; // modify the values
                        article.save();
                    }

                    holder.ivFavoris.setColorFilter(ContextCompat.getColor(context, R.color.cardview_dark_background));
                    likeArticle = false;
                }
            }
        });

        holder.ivPartager.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Code Foncier - Article "+articleList.get(position).getArticle_id()+ " : "+articleList.get(position).getDescription();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Code Foncier");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Partager via"));

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.articleList.size();
    }

}