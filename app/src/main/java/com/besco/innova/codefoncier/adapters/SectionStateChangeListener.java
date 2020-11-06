package com.besco.innova.codefoncier.adapters;

/**
 * Created by bpncool on 2/24/2016.
 */

import com.besco.innova.codefoncier.models.Titre;

/**
 * interface to listen changes in state of sections
 */
public interface SectionStateChangeListener {
    void onSectionStateChanged(Titre titre, boolean isOpen);
}