package com.besco.innova.codefoncier;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by fbessan on 14/11/2016.
 */

public class AideActivity extends AppCompatActivity {

    SeekBar volumeSeekbar;
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);
        setTitle(null);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            //toolbar.setTitle("Histoire de notre Hymne National");
            toolbar.setTitle("Aide");
        }
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        WebView webView = (WebView)findViewById(R.id.webView1);

        webView.loadDataWithBaseURL(null, getString(R.string.html_content_aide), "text/html", "utf-8", null);

        if (Build.VERSION.SDK_INT >= 11) {
            webView.setBackgroundColor(0x01000000);
        } else {
            webView.setBackgroundColor(0x00000000);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        menu.findItem(R.id.action_liste).setVisible(false);
        //menu.findItem(R.id.action_home).setVisible(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // action with ID action_refresh was selected
            case R.id.action_conseil:
                Intent intenconseil = new Intent(com.besco.innova.codefoncier.AideActivity.this, com.besco.innova.codefoncier.ConseilActivity.class);
                startActivity(intenconseil);
                break;
            case R.id.action_home:
                Intent intenhome = new Intent(com.besco.innova.codefoncier.AideActivity.this, com.besco.innova.codefoncier.DashboardActivity.class);
                startActivity(intenhome);
                break;
            case android.R.id.home:

                finish();
                return true;
            case R.id.action_help:
                Intent intentaide = new Intent(com.besco.innova.codefoncier.AideActivity.this, com.besco.innova.codefoncier.AideActivity.class);
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
        final Dialog dialogcontact = new Dialog(AideActivity.this);
        dialogcontact.setContentView(R.layout.aproposdialog);
        dialogcontact.setTitle("Contactez-Nous");
        dialogcontact.show();
    }



}
