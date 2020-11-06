package com.besco.innova.codefoncier.models;



import com.orm.SugarRecord;

/**
 * Created by root on 4/10/16.
 */



public class Titre extends SugarRecord{

    private int titre_id;
    private String label;
    private String description;
    public boolean isExpanded;



    public Titre(){

    }

    public Titre(String description){


        this.description = description;
        isExpanded = true;

    }

    public Titre(int titre_id,String description){

        this.titre_id = titre_id;
        this.description = description;
        isExpanded = true;

    }



    //Getter
    public int getTitre_id() { return titre_id; }
    public String getDescription() { return description; }
    public String getLabel() { return label; }


    //Setter
    public void setDescription(String description) { this.description = description;}
    public void setTitre_id(int titre_id) { this.titre_id = titre_id;}
    public void setLabel(String label) { this.label = label;}

    @Override
    public String toString() {
        return this.description;
    }

}
