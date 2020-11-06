package com.besco.innova.codefoncier.adapters;

/**
 * Created by Ariel on 3/25/2016.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.besco.innova.codefoncier.R;
import com.besco.innova.codefoncier.models.Article;
import com.besco.innova.codefoncier.models.Chapitre;

import java.util.ArrayList;
import java.util.List;


public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {


    //ArrayList<String> array = new ArrayList<>();
    private List<Chapitre> chapitreList;

    public MenuRecyclerAdapter(List<Chapitre> chapitreList){

        this.chapitreList = chapitreList;

    }

    @Override
    public MenuRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.

        //inflating the row layout we created earlier
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_row, parent, false);

        //making a ViewHolder object with the view we just inflated
        ViewHolder vh = new ViewHolder(v);


        return vh;
    }



    @Override
    public void onBindViewHolder(final MenuRecyclerAdapter.ViewHolder holder, final int position) {

        //setting the imageview with an image from my drawable resource folder
        holder.imageNumberView.setText("TITRE "+chapitreList.get(position).getTitre_id()+" - "+chapitreList.get(position).getLabel());

        holder.view.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("PREF_CHAPITRE_ID",String.valueOf(chapitreList.get(position).getChapitre_id()));
                editor.putString("PREF_DASHBOARD","0");
                editor.apply();

                Intent intent = new Intent(v.getContext(), com.besco.innova.codefoncier.ArticleActivity.class);
                intent.putExtra("LABEL", chapitreList.get(position).getLabel());
                intent.putExtra("CHAPITREID", String.valueOf(chapitreList.get(position).getChapitre_id()));
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount(){



        return chapitreList.size();

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        //reference to the row view
        public View view;
        TextView imageNumberView;

        //constructing the viewHolder
        public ViewHolder(View v) {
            super(v);
            view = v;
            imageNumberView = (TextView) v.findViewById(R.id.textView);

        }
    }

}
