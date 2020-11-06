package com.besco.innova.codefoncier.adapters;


import com.besco.innova.codefoncier.models.Chapitre;
import com.besco.innova.codefoncier.models.Titre;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface ItemClickListener {
    void itemClicked(Chapitre chapitre);
    void itemClicked(Titre titre);
}
