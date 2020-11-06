package com.besco.innova.codefoncier;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.besco.innova.codefoncier.models.Article;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by fbessan on 13/11/2016.
 */

public class ArticleActivity extends AppCompatActivity {
    private LinearLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);


        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        String LABEL = intent.getStringExtra("LABEL");
        String chapitreid = intent.getStringExtra("CHAPITREID");






        if (toolbar != null) {
            toolbar.setTitle(LABEL);
        }
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



    }



    private List<Article> getArticleList(String chapitreid){


        List<Article> articles = Article.findWithQuery(Article.class, "SELECT * FROM Article WHERE chapitreid = ? ", chapitreid);
        List<Article> allArticles = new ArrayList<Article>();
        for(int i = 0; i < articles.size(); i++) {
            allArticles.add(new Article(articles.get(i).getArticle_id(),articles.get(i).getChapitre_id(),articles.get(i).getDescription(),articles.get(i).getSection(),articles.get(i).getParagraphe(),articles.get(i).getSousparagraphe(),articles.get(i).getFavoris()));
        }

        return allArticles;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_liste).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Ex: 23 = article 23");
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String txtrecherche) {

                Log.e("LOGSEARCH","  BABA "+txtrecherche);
                List<Article> articles = Article.findWithQuery(Article.class, "SELECT * FROM Article WHERE articleid = ? ", txtrecherche);

                if(articles.size() > 0) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ArticleActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("PREF_DASHBOARD", "1");

                    editor.putString("PREF_CHAPITRE_ID", String.valueOf(articles.get(0).getChapitre_id()));
                    editor.putString("PREF_ARTICLE_ID", String.valueOf(articles.get(0).getArticle_id()));
                    editor.apply();

                    Intent intent = new Intent(com.besco.innova.codefoncier.ArticleActivity.this, com.besco.innova.codefoncier.ArticleActivity.class);
                    intent.putExtra("DASHBOARD", "1");
                    intent.putExtra("CHAPITREID", String.valueOf(articles.get(0).getChapitre_id()));
                    intent.putExtra("ARTICLEID", String.valueOf(articles.get(0).getArticle_id()));
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(ArticleActivity.this, "Aucun article ne correspond", Toast.LENGTH_LONG);
                    toast.show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {return true;}

        };

        searchView.setOnQueryTextListener(textChangeListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_conseil:
                Intent intenconseil = new Intent(com.besco.innova.codefoncier.ArticleActivity.this, com.besco.innova.codefoncier.ConseilActivity.class);
                startActivity(intenconseil);
                break;
            case R.id.action_home:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ArticleActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("PREF_DASHBOARD","0");
                editor.apply();

                Intent intenhome = new Intent(com.besco.innova.codefoncier.ArticleActivity.this, com.besco.innova.codefoncier.DashboardActivity.class);
                startActivity(intenhome);
                break;
            case android.R.id.home:
                SharedPreferences preferenceshome = PreferenceManager.getDefaultSharedPreferences(ArticleActivity.this);
                SharedPreferences.Editor editorhome = preferenceshome.edit();
                editorhome.putString("PREF_DASHBOARD","0");
                editorhome.apply();

                finish();
                return true;
            case R.id.action_help:
                Intent intentaide = new Intent(com.besco.innova.codefoncier.ArticleActivity.this, com.besco.innova.codefoncier.AideActivity.class);
                startActivity(intentaide);
                break;
            case R.id.menu_item_shared:
                //Partager
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Maitriser le code foncier du Bénin. Application disponible gratuitement sur PlayStore (Lien vers play store)";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Code Foncier");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Partager via"));
                break;
            case R.id.action_contact:
                initapropos();
                break;
            default:
                break;
        }

        return true;
    }


    private void initapropos(){
        //Contacter
        final Dialog dialogcontact = new Dialog(ArticleActivity.this);
        dialogcontact.setContentView(R.layout.aproposdialog);
        dialogcontact.setTitle("Contactez-Nous");
        dialogcontact.show();
    }


}