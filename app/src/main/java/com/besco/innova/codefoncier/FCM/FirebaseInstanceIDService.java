package com.besco.innova.codefoncier.FCM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by fbessan on 05/10/2016.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "Token";

    @Override
    public void onTokenRefresh() {
        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        //Get Zem By Token
        /*List<User> userInfo = Select.from(User.class)
                .where(Condition.prop("id").lt(1))
                .list();
        Log.i(TAG, "TOGBO " + refreshedToken);
        if(userInfo.size() == 0){ //Check if the data exist

            //Save token in the database
            User user = new User();
            user.setMatricule("0");
            user.setPassword("0");
            user.setToken(refreshedToken);
            user.save();

        }*/
    }
    private void sendRegistrationToServer(String token) {
        //Implement this method if you want to store the token on your server
    }

    /*@Override
    public void onTokenRefresh()
    {
        String token = FirebaseInstanceId.getInstance().getToken();

        //Get Zem By Token
        List<User> userInfo = Select.from(User.class)
                .where(Condition.prop("id").lt(1))
                .list();
        Log.i("Token ", "TOGBO " + token);
        if(userInfo.size() == 0){ //Check if the data exist

            //Save token in the database
            User user = new User();
            user.setMatricule("0");
            user.setPassword("0");
            user.setToken(token);
            user.save();

        }


    }*/
}
