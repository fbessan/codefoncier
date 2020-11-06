package com.besco.innova.codefoncier.HttpRequest;

import android.util.Log;
import android.util.Pair;


import com.besco.innova.codefoncier.Constant.Constant;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by fbessan on 02/09/2016.
 */
public class HttpRequest {


    static OkHttpClient client = new OkHttpClient();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String postToServer(String url, List<Pair<String, String>> pairs) throws Exception {

        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url);

        if (pairs != null) {

            okhttp3.FormBody.Builder postData = new okhttp3.FormBody.Builder();
            for (Pair<String, String> pair : pairs) {

                postData.add(pair.first, pair.second);
            }
            builder.post(postData.build());
        }

        okhttp3.Request request = builder.build();
        okhttp3.Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            Log.e("HTTP"," Error"+response.toString());
            throw new IOException(response.message() + " " + response.toString());
        }else{
            Log.e("HTTP"," Success"+response.toString());
        }
        return response.body().string();
    }


    public static String doGetRequest(String url) {

        String reponse = Constant.FAILURE_CODE_500;

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            reponse =  response.body().string();
            response.body().close();
        } catch (IOException e) {

            return reponse ;
        }

        return reponse;
    }


     void doGetRequestAsync(String url) throws Exception {

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                /*Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                System.out.println(response.body().string());*/
                Log.e("Dialog"," reponse "+ response.body().string());

                response.body().string();
            }
        });
    }




}
