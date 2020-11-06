package com.besco.innova.codefoncier;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.besco.innova.codefoncier.Constant.Constant;
import com.besco.innova.codefoncier.HttpRequest.HttpRequest;
import com.besco.innova.codefoncier.adapters.ItemClickListener;
import com.besco.innova.codefoncier.adapters.SectionedExpandableLayoutHelper;
import com.besco.innova.codefoncier.models.Article;
import com.besco.innova.codefoncier.models.Chapitre;
import com.besco.innova.codefoncier.models.Faq;
import com.besco.innova.codefoncier.models.Titre;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements ItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Code Foncier");
        }
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //setting the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(this, mRecyclerView, this, 1);


        //deleteSugarDB(getApplicationContext());
        //DemoTitreChapitre();

        List<Titre> titres = Titre.findWithQuery(Titre.class, "SELECT * FROM Titre ", null);
        ArrayList<Chapitre> chapitre = new ArrayList<>();
        for(int i = 0; i < titres.size(); i++) {
            String TitreAfficher = titres.get(i).getLabel()+ " - "+titres.get(i).getDescription();
            sectionedExpandableLayoutHelper.addSection(TitreAfficher, chapitre);

            List<Chapitre> chapitres = Chapitre.findWithQuery(Chapitre.class, "SELECT * FROM Chapitre WHERE titreid =? ", String.valueOf(titres.get(i).getTitre_id()));

            for(int j = 0; j < chapitres.size(); j++) {

                String ChapitreAfficher = chapitres.get(j).getLabel()+ " - "+chapitres.get(j).getDescription();
                chapitre.add(new Chapitre(ChapitreAfficher, chapitres.get(j).getTitre_id(),chapitres.get(j).getChapitre_id(),chapitres.get(j).getLabel()));
            }
        }


        sectionedExpandableLayoutHelper.notifyDataSetChanged();

        //Refresh
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);

    }

    public void DemoTitreChapitre(){

        //Default titre
        Titre titre = new Titre();
        titre.setTitre_id(1);
        titre.setLabel("TITRE I");
        titre.setDescription("DES DISPOSITIONS GENERALES");
        titre.save();

        //Default Chapiter
        Chapitre chapitre = new Chapitre();
        chapitre.setChapitre_id(1);
        chapitre.setTitre_id(1);
        chapitre.setLabel("Chapitre I");
        chapitre.setDescription("DE L’OBJET, DU CHAMP D’APPLICATION ET DU REGIME FONCIER");
        chapitre.save();

        Chapitre chapitre1 = new Chapitre();
        chapitre1.setChapitre_id(2);
        chapitre1.setTitre_id(1);
        chapitre1.setLabel("Chapitre II");
        chapitre1.setDescription("DES PRINCIPES GENERAUX");
        chapitre1.save();

        //Article
        Article article =  new Article();
        article.setChapitre_id(1);
        article.setArticle_id(1);
        article.setDescription("Le présent code a pour objet de déterminer les règles et les principes fondamentaux applicables en matières foncière et domaniale et de régir l'organisation et le fonctionnement du régime foncier et domanial en République du Bénin.");
        article.setSection("");
        article.save();

        Article article1 =  new Article();
        article1.setChapitre_id(2);
        article1.setArticle_id(2);
        article1.setDescription("Le présent code a pour objet de déterminer les règles et les principes fondamentaux applicables en matières foncière et domaniale et de régir l'organisation et le fonctionnement du régime foncier et domanial en République du Bénin.");
        article1.setSection("SECTION III - DE LA PROPRIETE PAR ACHAT-VENTE");
        article1.save();


    }

    public void deleteSugarDB(Context context)
    {
        SugarContext.terminate();
        SchemaGenerator schemaGenerator = new SchemaGenerator(context);
        schemaGenerator.deleteTables(new SugarDb(context).getDB());
        SugarContext.init(context);
        schemaGenerator.createDatabase(new SugarDb(context).getDB());
    }

    @Override
    public void itemClicked(Chapitre chapitre) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PREF_CHAPITRE_ID",String.valueOf(chapitre.getChapitre_id()));
        editor.putString("PREF_DASHBOARD","0");
        editor.apply();

        Intent intent = new Intent(com.besco.innova.codefoncier.MainActivity.this, com.besco.innova.codefoncier.ArticleActivity.class);
        intent.putExtra("LABEL", chapitre.getLabel());
        intent.putExtra("CHAPITREID", String.valueOf(chapitre.getChapitre_id()));
        startActivity(intent);


    }

    @Override
    public void itemClicked(Titre titre) {
        Toast.makeText(this, titre.getLabel() , Toast.LENGTH_SHORT).show();
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
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("PREF_DASHBOARD", "1");

                    editor.putString("PREF_CHAPITRE_ID", String.valueOf(articles.get(0).getChapitre_id()));
                    editor.putString("PREF_ARTICLE_ID", String.valueOf(articles.get(0).getArticle_id()));
                    editor.apply();

                    Intent intent = new Intent(com.besco.innova.codefoncier.MainActivity.this, com.besco.innova.codefoncier.ArticleActivity.class);
                    intent.putExtra("DASHBOARD", "1");
                    intent.putExtra("CHAPITREID", String.valueOf(articles.get(0).getChapitre_id()));
                    intent.putExtra("ARTICLEID", String.valueOf(articles.get(0).getArticle_id()));
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(MainActivity.this, "Aucun article ne correspond", Toast.LENGTH_LONG);
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
                Intent intenconseil = new Intent(com.besco.innova.codefoncier.MainActivity.this, com.besco.innova.codefoncier.ConseilActivity.class);
                startActivity(intenconseil);
                break;
            case R.id.action_home:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("PREF_DASHBOARD","0");
                editor.apply();

                Intent intenhome = new Intent(com.besco.innova.codefoncier.MainActivity.this, com.besco.innova.codefoncier.DashboardActivity.class);
                startActivity(intenhome);
                break;
            case android.R.id.home:
                SharedPreferences preferenceshome = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editorhome = preferenceshome.edit();
                editorhome.putString("PREF_DASHBOARD","0");
                editorhome.apply();
                finish();
                return true;
            case R.id.action_help:
                Intent intentaide = new Intent(com.besco.innova.codefoncier.MainActivity.this, com.besco.innova.codefoncier.AideActivity.class);
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
        final Dialog dialogcontact = new Dialog(MainActivity.this);
        dialogcontact.setContentView(R.layout.aproposdialog);
        dialogcontact.setTitle("Contactez-Nous");
        dialogcontact.show();
    }





    @Override
    public void onRefresh() {

    }
}
