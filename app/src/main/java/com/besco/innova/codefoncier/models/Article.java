package com.besco.innova.codefoncier.models;



import com.orm.SugarRecord;

/**
 * Created by root on 4/10/16.
 */



public class Article extends SugarRecord{

    private int article_id;
    private int chapitre_id;
    private String description;
    private String section;
    private String paragraphe;
    private String sousparagraphe;
    public String favoris;


    public Article(){

    }

    public Article(int article_id,int chapitre_id, String description, String section, String paragraphe, String sousparagraphe){

        this.chapitre_id = chapitre_id;
        this.article_id = article_id;
        this.description = description;
        this.section = section;
        this.paragraphe = paragraphe;
        this.sousparagraphe = sousparagraphe;

    }

    public Article(int article_id,int chapitre_id, String description, String section, String paragraphe, String sousparagraphe,String favoris){

        this.chapitre_id = chapitre_id;
        this.article_id = article_id;
        this.description = description;
        this.section = section;
        this.paragraphe = paragraphe;
        this.sousparagraphe = sousparagraphe;
        this.favoris = favoris;

    }


    //Getter
    public int getChapitre_id() { return chapitre_id; }
    public int getArticle_id() { return article_id; }
    public String getDescription() { return description; }
    public String getSection() { return section; }
    public String getParagraphe() { return paragraphe; }
    public String getSousparagraphe() { return sousparagraphe; }
    public String getFavoris() { return favoris; }


    //Setter
    public void setDescription(String description) { this.description = description;}
    public void setArticle_id(int article_id) { this.article_id = article_id;}
    public void setChapitre_id(int chapitre_id) { this.chapitre_id = chapitre_id;}
    public void setSection(String section) { this.section = section;}
    public void setParagraphe(String paragraphe) { this.paragraphe = paragraphe;}
    public void setSousparagraphe(String sousparagraphe) { this.sousparagraphe = sousparagraphe;}
    public void setFavoris(String favoris) { this.favoris = favoris;}

    @Override
    public String toString() {
        return this.description;
    }

}
