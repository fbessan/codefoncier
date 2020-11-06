package com.besco.innova.codefoncier;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




import com.besco.innova.codefoncier.models.Faq;


import java.util.List;



class FaqAdapter extends RecyclerView.Adapter<FaqViewHolder> {

    private List<Faq> faqList;
    private Context context;
    private boolean likeArticle = false;

    FaqAdapter(Context context, List<Faq> faqList) {
        this.faqList = faqList;
        this.context = context;

    }

    @Override
    public FaqViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_item_view, null);
        FaqViewHolder rcv = new FaqViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final FaqViewHolder holder, final int position) {

        holder.question.setText(String.valueOf(faqList.get(position).getQuestion()));
        holder.reponse.setText(faqList.get(position).getReponse());




    }

    @Override
    public int getItemCount() {
        return this.faqList.size();
    }

}