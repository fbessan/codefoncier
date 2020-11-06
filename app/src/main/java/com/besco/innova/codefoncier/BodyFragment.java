package com.besco.innova.codefoncier;



import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.besco.innova.codefoncier.Function.Functions;
import com.besco.innova.codefoncier.adapters.MenuRecyclerAdapter;
import com.besco.innova.codefoncier.models.Article;
import com.besco.innova.codefoncier.models.Chapitre;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BodyFragment extends Fragment {
    Functions functions;

    private LinearLayoutManager lLayout;
    ArticleAdapter adapter;
    List<Article> rowListItem;


    public BodyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("OOo", "MapActivity onCreate");


        functions = new Functions();

        //functions.deleteSugarDB(getContext());

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_body, container, false);
        String chapitreid = "";
        String articleid = "";
        String dashboard = "";

        if(getActivity()!=null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            chapitreid = preferences.getString("PREF_CHAPITRE_ID", "");
            articleid = preferences.getString("PREF_ARTICLE_ID", "");
            dashboard = preferences.getString("PREF_DASHBOARD", "");

            //

        }


        Log.e("LOGCHAP"," chapitre_id "+chapitreid);

        if(dashboard.equals("1")){
            rowListItem = getArticleFilterList(chapitreid,articleid);
        }else {
            rowListItem = getArticleList(chapitreid);
        }
        //List<Article> rowListItem = getArticleList(chapitreid);
        lLayout = new LinearLayoutManager(getActivity());

        RecyclerView rView = (RecyclerView)view.findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        ArticleAdapter rcAdapter = new ArticleAdapter(getActivity(), rowListItem);
        rView.setAdapter(rcAdapter);

        return  view;
    }


    private List<Article> getArticleList(String chapitreid){


        List<Article> articles = Article.findWithQuery(Article.class, "SELECT * FROM Article WHERE chapitreid = ? ", chapitreid);
        List<Article> allArticles = new ArrayList<Article>();
        for(int i = 0; i < articles.size(); i++) {
            allArticles.add(new Article(articles.get(i).getArticle_id(),articles.get(i).getChapitre_id(),articles.get(i).getDescription(),articles.get(i).getSection(),articles.get(i).getParagraphe(),articles.get(i).getSousparagraphe(),articles.get(i).getFavoris()));
        }

        return allArticles;
    }

    private List<Article> getArticleFilterList(String chapitreid,String articleid){


        List<Article> articles = Article.findWithQuery(Article.class, "SELECT * FROM Article WHERE chapitreid = ? AND articleid = ?", chapitreid,articleid);
        List<Article> allArticles = new ArrayList<Article>();
        for(int i = 0; i < articles.size(); i++) {
            allArticles.add(new Article(articles.get(i).getArticle_id(),articles.get(i).getChapitre_id(),articles.get(i).getDescription(),articles.get(i).getSection(),articles.get(i).getParagraphe(),articles.get(i).getSousparagraphe(),articles.get(i).getFavoris()));
        }

        return allArticles;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case android.R.id.home:

                getActivity().finish();
                return true;

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
        final Dialog dialogcontact = new Dialog(getActivity());
        dialogcontact.setContentView(R.layout.aproposdialog);
        dialogcontact.setTitle("Contactez-Nous");
        dialogcontact.show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);

        //menu.findItem(R.id.ic_next).setVisible(false);
        //menu.findItem(R.id.action_refresh).setVisible(false);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_liste).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        //searchView.setQueryHint("Nom Prénom Email Téléphone Formation");
        searchView.setQueryHint("Ex: 23 = article 23");

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("HeLog","ValHeLog"+newText);


                List<Article> rowListItem = getArticleList("1");
                lLayout = new LinearLayoutManager(getActivity());

                RecyclerView rView = (RecyclerView)getActivity().findViewById(R.id.recycler_view);
                rView.setLayoutManager(lLayout);

                ArticleAdapter rcAdapter = new ArticleAdapter(getActivity(), rowListItem);
                rView.setAdapter(rcAdapter);

                return true;
            }

        };

        searchView.setOnQueryTextListener(textChangeListener);

        /*menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);*/

        return true;
    }

}
