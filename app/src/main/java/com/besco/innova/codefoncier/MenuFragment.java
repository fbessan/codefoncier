package com.besco.innova.codefoncier;


import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.besco.innova.codefoncier.adapters.MenuRecyclerAdapter;
import com.besco.innova.codefoncier.adapters.SectionedExpandableLayoutHelper;
import com.besco.innova.codefoncier.models.Article;
import com.besco.innova.codefoncier.models.Chapitre;
import com.besco.innova.codefoncier.models.Titre;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private String chapitreid="";

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_menu, container, false);
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.menu_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager set to horizontal
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        if(getActivity()!=null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            chapitreid = preferences.getString("PREF_CHAPITRE_ID", "");
        }

        List<Chapitre> chapitres = Chapitre.findWithQuery(Chapitre.class, "SELECT * FROM Chapitre WHERE chapitreid <> ?", chapitreid);
        List<Chapitre> allChapitre = new ArrayList<Chapitre>();
        for(int i = 0; i < chapitres.size(); i++) {
            String va = "Titre "+chapitres.get(i).getTitre_id()+" - "+chapitres.get(i).getLabel();
            allChapitre.add(new Chapitre(chapitres.get(i).getLabel()));
            //allChapitre.add(new Chapitre(chapitres.get(i).getDescription(),chapitres.get(i).getTitre_id(),chapitres.get(i).getChapitre_id(),chapitres.get(i).getLabel()));
        }


        MenuRecyclerAdapter mAdapter = new MenuRecyclerAdapter(chapitres);

        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);


        return  view;
    }


}
