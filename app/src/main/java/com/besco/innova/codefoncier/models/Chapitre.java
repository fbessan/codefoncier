package com.besco.innova.codefoncier.models;



import com.orm.SugarRecord;

/**
 * Created by root on 4/10/16.
 */



public class Chapitre extends SugarRecord{

    private int chapitre_id;
    private int titre_id;
    private String label;
    private String description;


    public Chapitre(){

    }
    public Chapitre(String label){
        this.label = label;
    }

    public Chapitre(String description,int titre_id,int chapitre_id){

        this.description = description;
        this.titre_id = titre_id;
        this.chapitre_id = chapitre_id;


    }

    public Chapitre(String description,int titre_id,int chapitre_id,String label){

        this.chapitre_id = chapitre_id;
        this.titre_id = titre_id;
        this.description = description;
        this.label = label;

    }



    //Getter
    public int getTitre_id() { return titre_id; }
    public int getChapitre_id() { return chapitre_id; }
    public String getDescription() { return description; }
    public String getLabel() { return label; }


    //Setter
    public void setTitre_id(int titre_id) { this.titre_id = titre_id;}
    public void setChapitre_id(int chapitre_id) { this.chapitre_id = chapitre_id;}
    public void setDescription(String description) { this.description = description;}
    public void setLabel(String label) { this.label = label;}

    @Override
    public String toString() {
        return this.description;
    }

}
