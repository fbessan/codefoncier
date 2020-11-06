package com.besco.innova.codefoncier;

import android.*;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;



import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.besco.innova.codefoncier.Constant.Constant;
import com.besco.innova.codefoncier.Function.Functions;
import com.besco.innova.codefoncier.HttpRequest.HttpRequest;
import com.besco.innova.codefoncier.R;
import com.besco.innova.codefoncier.models.Article;
import com.besco.innova.codefoncier.models.Chapitre;
import com.besco.innova.codefoncier.models.Faq;
import com.besco.innova.codefoncier.models.Titre;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class SplashActivity extends AppCompatActivity {

    Functions functions;
    Context context;
    private CoordinatorLayout coordinatorLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        TextView textBox = (TextView) findViewById(R.id.textBox);
        TextView textBox1 = (TextView) findViewById(R.id.textBox1);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Shrikhand.ttf");
        textBox.setTypeface(typeFace);
        textBox1.setTypeface(typeFace);

        functions = new Functions();
        //functions.deleteSugarDB(getApplicationContext());
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        if (getBaseContext() != null && functions.isNetworkAvailable(getBaseContext())) {
            (new LoadBackgroudData()).execute();
        } else {
            Toast toast = Toast.makeText(getBaseContext(), "Connexion non disponible", Toast.LENGTH_LONG);
            toast.show();
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Connexion non disponible", Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            showBasicSplash();
        }



    }

    private void showBasicSplash() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(getBaseContext(), DashboardActivity.class));
                        finish();
                    }
                }
            }
        };
        // start thread
        thread.start();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void monprivilege(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 29;
                requestPermissions(new String[]{android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }
    class LoadBackgroudData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            String response = null;

            FirebaseMessaging.getInstance().subscribeToTopic("CodeFoncier");
            FirebaseInstanceId.getInstance().getToken();

            String faqUrl = HttpRequest.doGetRequest(Constant.SERVEUR_URL + Constant.SERVICE_FAQ);
            String titreUrl = HttpRequest.doGetRequest(Constant.SERVEUR_URL + Constant.SERVICE_TITRE);
            String chapitreUrl = HttpRequest.doGetRequest(Constant.SERVEUR_URL + Constant.SERVICE_CHAPITRE);
            String articleUrl = HttpRequest.doGetRequest(Constant.SERVEUR_URL + Constant.SERVICE_ARTICLE);
            if(getApplicationContext()!=null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 29;
                        requestPermissions(new String[]{android.Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                }
                String addtoken = HttpRequest.doGetRequest(Constant.SERVEUR_URL + Constant.SERVICE_TOKEN + "?imei=" + functions.getIMEI(getApplicationContext()) + "&token="+FirebaseInstanceId.getInstance().getToken());
                Log.v("LogMeeVALUE000","Token00000 "+FirebaseInstanceId.getInstance().getToken());
                if(addtoken.equals("SUCCESS")){
                    Log.v("LogMeeVALUE000","Token00000 "+FirebaseInstanceId.getInstance().getToken());
                }
            }


            //FAQ
            try {

                List<Faq> dbFaq = Faq.findWithQuery(Faq.class, "SELECT * FROM Faq ORDER BY faqid DESC", null);

                Log.v("LogMeeVALUE","VALUE "+faqUrl);
                //Faq
                JSONArray faqjson = new JSONArray(faqUrl);
                if(faqjson.length() != dbFaq.size()) {
                    Faq.deleteAll(Faq.class);
                    for (int j = 0; j < faqjson.length(); j++) {
                        JSONObject row = faqjson.getJSONObject(j);

                        Faq faq = new Faq();
                        faq.setFaq_id(row.optInt("id"));
                        faq.setQuestion(row.optString("question"));
                        faq.setReponse(row.optString("reponse"));
                        faq.save();
                    }
                }

                //Titre
                List<Titre> dbTitre = Titre.findWithQuery(Titre.class, "SELECT * FROM Titre ORDER BY titreid DESC", null);

                Log.v("LogMeeVALUE","VALUE "+titreUrl);
                //Faq
                JSONArray titrejson = new JSONArray(titreUrl);
                if(titrejson.length() != dbTitre.size()) {
                    Titre.deleteAll(Titre.class);
                    for (int j = 0; j < titrejson.length(); j++) {
                        JSONObject row = titrejson.getJSONObject(j);

                        Titre titre = new Titre();
                        titre.setTitre_id(row.optInt("id"));
                        titre.setLabel(row.optString("label"));
                        titre.setDescription(row.optString("description"));
                        titre.save();
                    }
                }


                //CHapitre
                List<Chapitre> dbChapitre = Chapitre.findWithQuery(Chapitre.class, "SELECT * FROM Chapitre ORDER BY chapitreid DESC", null);

                Log.v("LogMeeVALUE","VALUE "+chapitreUrl);
                //Faq
                JSONArray chapitrejson = new JSONArray(chapitreUrl);
                if(chapitrejson.length() != dbChapitre.size()) {
                    Chapitre.deleteAll(Chapitre.class);
                    for (int j = 0; j < chapitrejson.length(); j++) {
                        JSONObject row = chapitrejson.getJSONObject(j);

                        Chapitre chapitre = new Chapitre();
                        chapitre.setChapitre_id(row.optInt("id"));
                        chapitre.setTitre_id(row.optInt("titre_id"));
                        chapitre.setLabel(row.optString("label"));
                        chapitre.setDescription(row.optString("description"));
                        chapitre.save();
                    }
                }

                //Articles
                List<Article> dbArticle = Article.findWithQuery(Article.class, "SELECT * FROM Article ORDER BY articleid DESC", null);

                Log.v("LogMeeVALUE","VALUE "+articleUrl);
                //Faq
                JSONArray articlejson = new JSONArray(articleUrl);
                if(articlejson.length() != dbArticle.size()) {
                    Article.deleteAll(Article.class);
                    for (int j = 0; j < articlejson.length(); j++) {
                        JSONObject row = articlejson.getJSONObject(j);

                        Article article = new Article();
                        article.setChapitre_id(row.optInt("chapitre_id"));
                        article.setArticle_id(row.optInt("id"));
                        article.setSection(row.optString("section"));
                        article.setDescription(row.optString("description"));
                        article.setParagraphe(row.optString("paragraphe"));
                        article.setSousparagraphe(row.optString("sousparagraphe"));
                        article.setFavoris("0");
                        article.save();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            startActivity(new Intent(getBaseContext(), DashboardActivity.class));
            finish();

            response = Constant.SUCCESS_CODE;





            return response;

        }

    }


}
