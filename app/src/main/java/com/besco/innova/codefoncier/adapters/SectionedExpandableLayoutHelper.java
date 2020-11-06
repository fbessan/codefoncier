package com.besco.innova.codefoncier.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.besco.innova.codefoncier.models.Chapitre;
import com.besco.innova.codefoncier.models.Titre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bpncool on 2/23/2016.
 */
public class SectionedExpandableLayoutHelper implements SectionStateChangeListener {

    //data list
    private LinkedHashMap<Titre, ArrayList<Chapitre>> mSectionDataMap = new LinkedHashMap<Titre, ArrayList<Chapitre>>();
    private ArrayList<Object> mDataArrayList = new ArrayList<Object>();

    //section map
    //TODO : look for a way to avoid this
    private HashMap<String, Titre> mSectionMap = new HashMap<String, Titre>();

    //adapter
    private SectionedExpandableGridAdapter mSectionedExpandableGridAdapter;

    //recycler view
    RecyclerView mRecyclerView;

    public SectionedExpandableLayoutHelper(Context context, RecyclerView recyclerView, ItemClickListener itemClickListener,
                                           int gridSpanCount) {

        //setting the recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, gridSpanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        mSectionedExpandableGridAdapter = new SectionedExpandableGridAdapter(context, mDataArrayList,
                gridLayoutManager, itemClickListener, this);
        recyclerView.setAdapter(mSectionedExpandableGridAdapter);

        mRecyclerView = recyclerView;
    }

    public void notifyDataSetChanged() {
        //TODO : handle this condition such that these functions won't be called if the recycler view is on scroll
        generateDataList();
        mSectionedExpandableGridAdapter.notifyDataSetChanged();
    }

    public void addSection(String titre, ArrayList<Chapitre> chapitres) {
        Titre newTitre;
        mSectionMap.put(titre, (newTitre = new Titre(titre)));
        mSectionDataMap.put(newTitre, chapitres);
    }

    public void addItem(String section, Chapitre chapitre) {
        mSectionDataMap.get(mSectionMap.get(section)).add(chapitre);
    }

    public void removeItem(String section, Chapitre chapitre) {
        mSectionDataMap.get(mSectionMap.get(section)).remove(chapitre);
    }

    public void removeSection(String section) {
        mSectionDataMap.remove(mSectionMap.get(section));
        mSectionMap.remove(section);
    }

    private void generateDataList () {
        mDataArrayList.clear();
        for (Map.Entry<Titre, ArrayList<Chapitre>> entry : mSectionDataMap.entrySet()) {
            Titre key;
            mDataArrayList.add((key = entry.getKey()));
            if (key.isExpanded)
                mDataArrayList.addAll(entry.getValue());
        }
    }

    @Override
    public void onSectionStateChanged(Titre titre, boolean isOpen) {
        if (!mRecyclerView.isComputingLayout()) {
            titre.isExpanded = isOpen;
            notifyDataSetChanged();
        }
    }
}
