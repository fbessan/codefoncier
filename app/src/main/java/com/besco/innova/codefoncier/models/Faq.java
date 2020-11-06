package com.besco.innova.codefoncier.models;



import com.orm.SugarRecord;

/**
 * Created by root on 4/10/16.
 */



public class Faq extends SugarRecord{

    private int faq_id;
    private String question;
    private String reponse;


    public Faq(){

    }

    public Faq(int faq_id,String question, String reponse){

        this.question = question;
        this.faq_id = faq_id;
        this.reponse = reponse;

    }



    //Getter
    public String getQuestion() { return question; }
    public int getFaq_id() { return faq_id; }
    public String getReponse() { return reponse; }


    //Setter
    public void setQuestion(String question) { this.question = question;}
    public void setFaq_id(int Faq_id) { this.faq_id = faq_id;}
    public void setReponse(String reponse) { this.reponse = reponse;}

    @Override
    public String toString() {
        return this.question;
    }

}
