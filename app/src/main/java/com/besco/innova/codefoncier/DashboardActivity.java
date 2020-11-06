package com.besco.innova.codefoncier;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.besco.innova.codefoncier.models.Article;

import java.util.List;

/**
 * Created by fbessan on 24/11/2016.
 */

public class DashboardActivity extends AppCompatActivity {

    private ImageView imagebenin1,imagebenin2,imagebenin3,imagebenin4,imagebenin5,imagebenin6;
    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //Code
        imagebenin1 = (ImageView) findViewById(R.id.imagebenin1);
        imagebenin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentaide = new Intent(com.besco.innova.codefoncier.DashboardActivity.this, com.besco.innova.codefoncier.MainActivity.class);
                startActivity(intentaide);

            }
        });



        //Recherche
        imagebenin2 = (ImageView) findViewById(R.id.imagebenin2);
        imagebenin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialogsearch = new Dialog(DashboardActivity.this);
                dialogsearch.setContentView(R.layout.searchdialog);
                dialogsearch.setTitle("Recherche par article");
                dialogsearch.show();

                final EditText txtrecherche = (EditText) dialogsearch.findViewById(R.id.txtrecherche);
                ImageButton imageButton = (ImageButton) dialogsearch.findViewById(R.id.imageButton);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        List<Article> articles = Article.findWithQuery(Article.class, "SELECT * FROM Article WHERE articleid = ? ", txtrecherche.getText().toString());

                        if(articles.size() > 0) {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(DashboardActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("PREF_DASHBOARD", "1");
                            Log.v("LogMeeVALUEjhjh", "VALUE " + articles.size());
                            editor.putString("PREF_CHAPITRE_ID", String.valueOf(articles.get(0).getChapitre_id()));
                            editor.putString("PREF_ARTICLE_ID", String.valueOf(articles.get(0).getArticle_id()));
                            editor.apply();

                            Intent intent = new Intent(com.besco.innova.codefoncier.DashboardActivity.this, com.besco.innova.codefoncier.ArticleActivity.class);
                            intent.putExtra("DASHBOARD", "1");
                            intent.putExtra("CHAPITREID", String.valueOf(articles.get(0).getChapitre_id()));
                            intent.putExtra("ARTICLEID", String.valueOf(articles.get(0).getArticle_id()));
                            startActivity(intent);
                        }else{
                            Toast toast = Toast.makeText(DashboardActivity.this, "Aucun article ne correspond", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });

            }
        });


        //Evaluer
        imagebenin3 = (ImageView) findViewById(R.id.imagebenin3);
        imagebenin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialogsearch = new Dialog(DashboardActivity.this);
                dialogsearch.setContentView(R.layout.ratingdialog);
                dialogsearch.setTitle("Notez-nous");
                dialogsearch.show();


                final RatingBar ratingBar = (RatingBar) dialogsearch.findViewById(R.id.ratingBar);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(dialogsearch.getContext());
                String pref_rating = preferences.getString("PREF_RATING", "1.0");
                if(!pref_rating.isEmpty()) {
                    ratingBar.setRating(Float.parseFloat(pref_rating));
                }

                ImageButton imageButton = (ImageButton) dialogsearch.findViewById(R.id.imageButton);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialogsearch.dismiss();

                       Log.e("LOGMERATING",String.valueOf(ratingBar.getRating()));
                        Toast toast = Toast.makeText(DashboardActivity.this, "Merci pour votre participation", Toast.LENGTH_LONG);
                        toast.show();

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(DashboardActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("PREF_RATING",String.valueOf(ratingBar.getRating()));

                        editor.apply();




                    }
                });
            }
        });


        //Favoris
        imagebenin4 = (ImageView) findViewById(R.id.imagebenin4);
        imagebenin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentaide = new Intent(com.besco.innova.codefoncier.DashboardActivity.this, com.besco.innova.codefoncier.FavorisActivity.class);
                startActivity(intentaide);
            }
        });


        //FAQ
        imagebenin5 = (ImageView) findViewById(R.id.imagebenin5);
        imagebenin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentaide = new Intent(com.besco.innova.codefoncier.DashboardActivity.this, com.besco.innova.codefoncier.FaqActivity.class);
                startActivity(intentaide);

            }
        });


        //Aide
        imagebenin6 = (ImageView) findViewById(R.id.imagebenin6);
        imagebenin6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentaide = new Intent(com.besco.innova.codefoncier.DashboardActivity.this, com.besco.innova.codefoncier.AideActivity.class);
                startActivity(intentaide);

            }
        });

    }


}
