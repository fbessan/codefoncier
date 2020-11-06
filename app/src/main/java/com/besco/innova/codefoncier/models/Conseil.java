package com.besco.innova.codefoncier.models;



import com.orm.SugarRecord;

/**
 * Created by root on 4/10/16.
 */



public class Conseil extends SugarRecord{

    private int conseil_id;
    private String nowdate;
    private String label;


    public Conseil(){

    }

    public Conseil(String nowdate, String label){

        this.nowdate = nowdate;
        this.label = label;

    }
    public Conseil(int conseil_id,String nowdate, String label){

            this.nowdate = nowdate;
            this.conseil_id = conseil_id;
            this.label = label;

        }



    //Getter
    public String getLabel() { return label; }
    public int getConseil_id() { return conseil_id; }
    public String getNowdate() { return nowdate; }


    //Setter
    public void setLabel(String conseil) { this.label = label;}
    public void setConseil_id(int conseil_id) { this.conseil_id = conseil_id;}
    public void setNowdate(String now) { this.nowdate = nowdate;}

    @Override
    public String toString() {
        return this.label;
    }

}
