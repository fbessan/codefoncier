package com.besco.innova.codefoncier;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.besco.innova.codefoncier.adapters.SectionedExpandableLayoutHelper;
import com.besco.innova.codefoncier.models.Article;
import com.besco.innova.codefoncier.models.Faq;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by fbessan on 13/11/2016.
 */

public class FavorisActivity extends AppCompatActivity {
    private LinearLayoutManager lLayout;
    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);


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
            toolbar.setTitle("Mes favoris");
        }
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //getFaqList();

        context = getApplicationContext();

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new FavorisAdapter(context, getArticleList());

        recyclerView.setAdapter(recyclerViewAdapter);


    }



    private List<Article> getArticleList(){


        List<Article> articles = Article.findWithQuery(Article.class, "SELECT * FROM Article WHERE favoris = 1 ", null);
        List<Article> allArticles = new ArrayList<Article>();
        for(int i = 0; i < articles.size(); i++) {
            allArticles.add(new Article(articles.get(i).getArticle_id(),articles.get(i).getChapitre_id(),articles.get(i).getDescription(),articles.get(i).getSection(),articles.get(i).getParagraphe(),articles.get(i).getSousparagraphe()));
        }

        return allArticles;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        menu.findItem(R.id.action_liste).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_conseil:
                Intent intenconseil = new Intent(com.besco.innova.codefoncier.FavorisActivity.this, com.besco.innova.codefoncier.ConseilActivity.class);
                startActivity(intenconseil);
                break;
            case R.id.action_home:
                Intent intenhome = new Intent(com.besco.innova.codefoncier.FavorisActivity.this, com.besco.innova.codefoncier.DashboardActivity.class);
                startActivity(intenhome);
                break;
            case android.R.id.home:

                finish();
                return true;
            case R.id.action_help:
                Intent intentaide = new Intent(com.besco.innova.codefoncier.FavorisActivity.this, com.besco.innova.codefoncier.AideActivity.class);
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
        final Dialog dialogcontact = new Dialog(FavorisActivity.this);
        dialogcontact.setContentView(R.layout.aproposdialog);
        dialogcontact.setTitle("Contactez-Nous");
        dialogcontact.show();
    }


}